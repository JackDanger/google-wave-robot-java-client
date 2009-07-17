// Copyright 2009 Google Inc. All Rights Reserved.
package com.google.wave.api;


/**
 * The types of events that Robots can process.
 * 
 * @author scovitz@google.com (Seth Covitz)
 * @author mprasetya@google.com (Marcel Prasetya)
 */
public enum EventType {
  WAVELET_BLIP_CREATED("WAVELET_BLIP_CREATED"),
  WAVELET_BLIP_REMOVED("WAVELET_BLIP_REMOVED"),
  WAVELET_PARTICIPANTS_CHANGED("WAVELET_PARTICIPANTS_CHANGED"),
  WAVELET_SELF_ADDED("WAVELET_SELF_ADDED"),
  WAVELET_SELF_REMOVED("WAVELET_SELF_REMOVED"),
  WAVELET_TIMESTAMP_CHANGED("WAVELET_TIMESTAMP_CHANGED"),
  WAVELET_TITLE_CHANGED("WAVELET_TITLE_CHANGED"),
  WAVELET_VERSION_CHANGED("WAVELET_VERSION_CHANGED"),
  BLIP_CONTRIBUTORS_CHANGED("BLIP_CONTRIBUTORS_CHANGED"),
  BLIP_DELETED("BLIP_DELETED"),
  BLIP_SUBMITTED("BLIP_SUBMITTED"),
  BLIP_TIMESTAMP_CHANGED("BLIP_TIMESTAMP_CHANGED"),
  BLIP_VERSION_CHANGED("BLIP_VERSION_CHANGED"),
  DOCUMENT_CHANGED("DOCUMENT_CHANGED"),
  FORM_BUTTON_CLICKED("FORM_BUTTON_CLICKED");
  
  private final String text;

  private EventType(String text) {
    this.text = text;
  }
  
  /* (non-Javadoc)
   * @see java.lang.Enum#toString()
   */
  @Override
  public String toString() {
    return text;
  }
  
  /**
   * Converts a string into an EventType ignoring case in the process. This is
   * used primarily for serialization from JSON.
   * 
   * @param name the name of the event type.
   * @return the converted event type.
   */
  public static EventType valueOfIgnoreCase(String name) {
    return valueOf(name.toUpperCase());
  }
}
