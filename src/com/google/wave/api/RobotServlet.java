// Copyright 2009 Google Inc. All Rights Reserved.
package com.google.wave.api;

/**
 * An interface for processing Robot events.
 * 
 * @author scovitz@google.com (Seth Covitz)
 * @author mprasetya@google.com (Marcel Prasetya)
 */
public interface RobotServlet {

  /**
   * The main event loop for a Robot. The robot will process the set of events
   * as a whole or one by one. Each event contains the Wavelet, Blips and
   * Documents that are affected. More context and content are provided for an
   * event depending on the settings in the Capabilities XML configuration.
   * 
   * Modifications to the Wavelet, Blips and Documents attached to an event
   * will generate operations that are transmitted back to the Wave Robot Proxy
   * and applied to the original Wave as intended.
   * 
   * @param events The set of events to be processed.
   */
  public void processEvents(RobotMessageBundle events);
}
