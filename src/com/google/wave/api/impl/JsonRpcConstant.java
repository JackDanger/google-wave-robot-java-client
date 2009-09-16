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

package com.google.wave.api.impl;

import com.google.wave.api.Annotation;
import com.google.wave.api.Element;
import com.google.wave.api.Range;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Enumeration for Google Wave JSON-RPC request properties.
 *
 * @author mprasetya@google.com (Marcel Prasetya)
 */
public class JsonRpcConstant {

  /**
   * Enumeration for JSON-RPC request properties.
   *
   * @author mprasetya@google.com (Marcel Prasetya)
   */
  public enum RequestProperty {
    METHOD("method"),
    ID("id"),
    PARAMS("params");

    private final String key;

    private RequestProperty(String key) {
      this.key = key;
    }

    /**
     * Returns the string key to access the property..
     *
     * @return A string key to access the property.
     */
    public String key() {
      return key;
    }
  }

  /**
   * Enumeration for Google Wave specific JSON-RPC request parameters.
   *
   * @author mprasetya@google.com (Marcel Prasetya)
   */
  public enum ParamsProperty {
    // TODO(mprasetya): Consider combining this with OperationType, or at least
    // each OperationType should have a list of ParamsProperty.

    // Required parameters.
    WAVE_ID("waveId", String.class),
    WAVELET_ID("waveletId", String.class),

    // Commonly used parameters.
    BLIP_ID("blipId", String.class),

    // Operation specific parameters.
    ANNOTATION("annotation", Annotation.class),
    BLIP_DATA("blipData", BlipData.class),
    BLIP_AUTHOR("blipAuthor", String.class),
    BLIP_CREATION_TIME("blipCreationTime", Long.class),
    CAPABILITIES_HASH("capabilitiesHash", String.class),
    CHILD_BLIP_ID("childBlipId", String.class),
    CONTENT("content", String.class),
    DATADOC_NAME("datadocName", String.class),
    DATADOC_VALUE("datadocValue", String.class),
    DATADOC_WRITEBACK("datadocWriteback", String.class),
    ELEMENT("element", Element.class),
    INDEX("index", Integer.class),
    NAME("name", String.class),
    PARTICIPANT_ID("participantId", String.class),
    RANGE("range", Range.class),
    STYLE_TYPE("styleType", String.class),
    WAVELET_DATA("waveletData", WaveletData.class),
    WAVELET_TITLE("waveletTitle", String.class);

    private static final Logger LOG = Logger.getLogger(ParamsProperty.class.getName());

    private static final Map<String, ParamsProperty> reverseLookupMap =
        new HashMap<String, ParamsProperty>();

    static {
      for (ParamsProperty property : ParamsProperty.values()) {
        if (reverseLookupMap.containsKey(property.key)) {
          LOG.warning("Parameter with key " + property.key + " already exist.");
        }
        reverseLookupMap.put(property.key, property);
      }
    }

    private final String key;
    private final Class<? extends Object> clazz;

    private ParamsProperty(String key, Class<? extends Object> clazz) {
      this.key = key;
      this.clazz = clazz;
    }

    /**
     * Returns the string key to access the property.
     *
     * @return A string key to access the property.
     */
    public String key() {
      return key;
    }

    /**
     * Returns the {@link Class} object that represents the type of this
     * property.
     *
     * @return A {@link Class} object that represents the type of this property.
     */
    public Class<? extends Object> clazz() {
      return clazz;
    }

    /**
     * Returns a {@link ParamsProperty} enumeration that has the given key.
     *
     * @param key The method name of a property.
     * @return An {@link ParamsProperty} that has the given key.
     */
    public static ParamsProperty fromKey(String key) {
      return reverseLookupMap.get(key);
    }
  }
}
