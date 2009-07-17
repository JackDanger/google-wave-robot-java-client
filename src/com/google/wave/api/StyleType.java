// Copyright 2009 Google Inc. All Rights Reserved.
package com.google.wave.api;

/**
 * Text styles supported by Robots. Styles are a specialization of Annotations.
 * 
 * @author scovitz@google.com (Seth Covitz)
 * @author mprasetya@google.com (Marcel Prasetya)
 */
public enum StyleType {
  BOLD("BOLD"),
  ITALIC("ITALIC"),
  UNDERLINE("UNDERLINE"),
  INDENT1("INDENT1"),
  INDENT2("INDENT2"),
  INDENT3("INDENT3"),
  BULLETED("BULLETED"),
  HEADING1("HEADING1"),
  HEADING2("HEADING2"),
  HEADING3("HEADING3"),
  HEADING4("HEADING4");
  
  /**
   * The name of the annotation.
   */
  private final String name;
  
  /**
   * Constructs a StyleType annotation with a given name.
   * 
   * @param name
   */
  private StyleType(String name) {
    this.name = name;
  }
  
  /* (non-Javadoc)
   * @see java.lang.Enum#toString()
   */
  @Override
  public String toString() {
    return name;
  }

  /**
   * Converts the name of a style into the StyleType enumerated type.
   */
  public static StyleType valueOfIgnoreCase(String name) {
    return valueOf(name.toUpperCase());
  }  
}
