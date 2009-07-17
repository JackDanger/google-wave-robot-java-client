// Copyright 2009 Google Inc. All Rights Reserved.
package com.google.wave.api;

/**
 * Element Types.
 * 
 * @author scovitz@google.com (Seth Covitz)
 * @author mprasetya@google.com (Marcel Prasetya)
 */
public enum ElementType {
  // Form Elements
  INPUT("INPUT"),
  PASSWORD("PASSWORD"),
  CHECK("CHECK"),
  LABEL("LABEL"),
  BUTTON("BUTTON"),
  RADIO_BUTTON("RADIO_BUTTON"),
  RADIO_BUTTON_GROUP("RADIO_BUTTON_GROUP"),
  TEXTAREA("TEXTAREA"),
  // Inline Blips
  INLINE_BLIP("INLINE_BLIP"),
  // Gadgets
  GADGET("GADGET"),
  // Images
  IMAGE("IMAGE");
  
  private final String text;
  
  private ElementType(String text) {
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
   * Converts a string into an ElementType. This is used primarily during
   * deserialization from JSON. 
   * 
   * @param name the name of the ElementType.
   * @return the ElementType representing that name.
   */
  public static ElementType valueOfIgnoreCase(String name) {
    return valueOf(name.toUpperCase());
  }
}
