/* Copyright (c) 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.wave.api;

import com.google.wave.api.impl.ElementSerializer;
import com.google.wave.api.impl.EventDataSerializer;
import com.google.wave.api.impl.EventMessageBundle;
import com.google.wave.api.impl.EventMessageBundleSerializer;
import com.google.wave.api.impl.OperationMessageBundle;
import com.google.wave.api.impl.OperationSerializer;
import com.google.wave.api.impl.RobotMessageBundleImpl;

import com.metaparadigm.jsonrpc.JSONSerializer;
import com.metaparadigm.jsonrpc.MarshallException;
import com.metaparadigm.jsonrpc.SerializerState;
import com.metaparadigm.jsonrpc.UnmarshallException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * An abstract implementation of a Robot that handles deserialization of
 * events and serialization of operations and implements callbacks for
 * the profile and capabilities.
 * 
 * @author douwe@google.com (Douwe Osinga)
 * @author mprasetya@google.com (Marcel Prasetya)
 */
public abstract class AbstractRobot extends HttpServlet implements RobotServlet {
  /* Some mime types */
  private static final String JSON_MIME_TYPE = "application/json; charset=utf-8";
  private static final String XML_MIME_TYPE = "application/xml";
  private static final String RESPONSE_ENCODING = "utf-8";
  
  /* The query parameter to specify custom profile request. */
  private static final String NAME_QUERY_PARAMETER_KEY = "name";

  private static final Logger LOG = Logger.getLogger(AbstractRobot.class.getName());

  /* Various request path constants that the robot replies to */
  private static final String RPC_PATH = "/_wave/robot/jsonrpc";
  private static final String PROFILE_PATH = "/_wave/robot/profile";
  private static final String CAPABILITIES_PATH = "/_wave/capabilities.xml";
  public static final String DEFAULT_AVATAR =
      "https://wave.google.com/a/wavesandbox.com/static/images/profiles/rusty.png";

  private List<Capability> capabilities = null;
  
  private long version;
  
  private JSONSerializer serializer;
  private HttpServletRequest request;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    this.request = req;
    maybeRegisterEvents();
    if (req.getServletPath().equals(RPC_PATH)) {
      processRpc(req, resp);
    } else {
      resp.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
    }
  }
  
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    this.request = req;
    maybeRegisterEvents();
    String path = req.getServletPath();
    if (path.equals(PROFILE_PATH)) {
      processProfile(req, resp);
    } else if (path.equals(CAPABILITIES_PATH)) {
      processCapabilities(req, resp);
    } else {
      resp.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
    }
  }

  private void maybeRegisterEvents() {
    if (capabilities == null) {
      capabilities = new ArrayList<Capability>();
      version = 0;
      registerForEvents();
    }
  }

  private void processCapabilities(HttpServletRequest req, HttpServletResponse resp) {
    StringBuilder xml = new StringBuilder();
    xml.append("<?xml version=\"1.0\"?>\n");
    xml.append("<w:robot xmlns:w=\"http://wave.google.com/extensions/robots/1.0\">\n");
    xml.append("  <w:version>");
    xml.append(getVersionString());
    xml.append("</w:version>\n");
    xml.append("  <w:capabilities>\n");
    for (Capability cap : capabilities) {
      xml.append("    <w:capability name=\"" + cap.getEventType().toString() + "\"");
      if (!cap.getContexts().isEmpty()) {
        xml.append(" context=\"");
        boolean first = true;
        for (Context context : cap.getContexts()) {
          xml.append(context.name());
          if (first) {
            first = false;
          } else {
            xml.append(',');
          }
        }
        xml.append("\"");
      }
      xml.append("/>\n");
    }
    xml.append("  </w:capabilities>\n");
    xml.append("</w:robot>\n");
    // Write the result into the output stream.
    resp.setContentType(XML_MIME_TYPE);
    resp.setCharacterEncoding(RESPONSE_ENCODING);
    try {
      resp.getWriter().write(xml.toString());
    } catch (IOException e) {
      resp.setStatus(HttpURLConnection.HTTP_INTERNAL_ERROR);
      return;
    }
    resp.setStatus(HttpURLConnection.HTTP_OK);
  }
  
  private void processProfile(HttpServletRequest req, HttpServletResponse resp) {
    ParticipantProfile profile = null;
    
    // Try to get custom profile.
    String gatewayedName = req.getParameter(NAME_QUERY_PARAMETER_KEY);    
    if (gatewayedName != null) {
      profile = getCustomProfile(gatewayedName);
    }
    
    // Set the default profile. 
    if (profile == null) {
      profile = new ParticipantProfile(getRobotName(), getRobotAvatarUrl(),
          getRobotProfilePageUrl());
    }

    String profileAsJson;
    try {
      profileAsJson = getJSONSerializer().toJSON(profile);
    } catch (MarshallException e) {
      resp.setStatus(HttpURLConnection.HTTP_INTERNAL_ERROR);
      return;
    }
    
    // Write the result into the output stream.
    resp.setContentType(JSON_MIME_TYPE);
    resp.setCharacterEncoding(RESPONSE_ENCODING);
    try {
      resp.getWriter().write(profileAsJson);
    } catch (IOException e) {
      resp.setStatus(HttpURLConnection.HTTP_INTERNAL_ERROR);
      return;
    }
    resp.setStatus(HttpURLConnection.HTTP_OK);
  }

  private void processRpc(HttpServletRequest req, HttpServletResponse resp) {
    RobotMessageBundleImpl events = null;
    try {
      events = deserializeEvents(req);
    } catch (IOException e) {
      resp.setStatus(HttpURLConnection.HTTP_INTERNAL_ERROR);
      return;
    }
    processEvents(events);
    events.getOperations().setVersion(getVersionString());
    serializeOperations(events.getOperations(), resp);
  }

  /**
   * @return the base url for this robot.
   */
  protected String getRobotAddress() {
    return request.getRemoteHost();
  }

  /**
   * Returns the version identifier that is specified in the capabilities.xml
   * file.
   *
   * @return A version identifier that is specified in capabilities.xml.
   */
  public String getVersionString() {
    maybeRegisterEvents();
    return String.valueOf(version);
  }
 
  private void serializeOperations(OperationMessageBundle operations, HttpServletResponse resp) {
    try {
      String json = getJSONSerializer().toJSON(operations);
      LOG.info("Outgoing operations: " + json);
      
      resp.setContentType(JSON_MIME_TYPE);
      resp.setCharacterEncoding(RESPONSE_ENCODING);
      resp.getWriter().write(json);
      resp.setStatus(HttpURLConnection.HTTP_OK);
    } catch (IOException iox) {
      resp.setStatus(HttpURLConnection.HTTP_INTERNAL_ERROR);
    } catch (MarshallException mx) {
      resp.setStatus(HttpURLConnection.HTTP_INTERNAL_ERROR);
    }
  }

  private RobotMessageBundleImpl deserializeEvents(HttpServletRequest req) throws IOException {
    String json = getRequestBody(req);
    LOG.info("Incoming events: " + json);

    RobotMessageBundleImpl events = null;

    try {
      JSONObject jsonObject = new JSONObject(json);
      events = new RobotMessageBundleImpl((EventMessageBundle) getJSONSerializer().unmarshall(
          new SerializerState(), EventMessageBundle.class, jsonObject), getRobotAddress());
    } catch (JSONException jsonx) {
      jsonx.printStackTrace();
    } catch (UnmarshallException e) {
      e.printStackTrace();
    }
    
    return events;
  }

  private String getRequestBody(HttpServletRequest req) throws IOException {
    StringBuilder json = new StringBuilder();
    BufferedReader reader = req.getReader();
    String line;
    while ((line = reader.readLine()) != null) {
      json.append(line);
    }
    return json.toString();
  }

  private JSONSerializer getJSONSerializer() {
    if (serializer != null) {
      return serializer;
    }

    serializer = new JSONSerializer();
    try {
      serializer.registerDefaultSerializers();
      serializer.registerSerializer(new EventMessageBundleSerializer());
      serializer.registerSerializer(new EventDataSerializer());
      serializer.registerSerializer(new ElementSerializer());
      serializer.registerSerializer(new OperationSerializer());
    } catch (Exception e) {
      e.printStackTrace();
    }

    return serializer;
  }
  
  /**
   * Register an event for this robot with the specified contexts.
   */
  public void registerEvent(EventType event, Context... contexts) {
    long hash = event.hashCode();
    List<Context> contextList= new ArrayList<Context>(contexts.length);
    for (Context context : contexts) {
      hash = hash * 31 + context.hashCode();
      contextList.add(context);
    }
    version = version * 17 + hash;
    capabilities.add(new Capability(event, contextList));
  }

  /**
   * @return Custom profile based on "name" query parameter, or {@code null} if
   *     this robot doesn't support custom profile.
   */
  public ParticipantProfile getCustomProfile(String name) {
    return null;
  }

  /**
   * @return The URL of the Robot avatar image.
   */
  public String getRobotAvatarUrl() {
    return DEFAULT_AVATAR;
  }
  
  /**
   * @return The URL of the Robot Profile page.
   */
  public String getRobotProfilePageUrl() {
    return "http://" + request.getRemoteHost();
  }

  /**
   * @return The display name of the Robot.
   */
  public abstract String getRobotName();
  
  @Override
  public abstract void processEvents(RobotMessageBundle events);
  
  /**
   * Override to let the system know which events this robot
   * reacts to (by calling {@link #registerEvent(EventType, Context...)})
   */
  public abstract void registerForEvents();

}
