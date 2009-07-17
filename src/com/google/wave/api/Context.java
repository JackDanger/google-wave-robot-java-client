// Copyright 2009 Google Inc. All Rights Reserved.
package com.google.wave.api;

/**
 * Enumeration that represents the context that the robot needs to provide when
 * calling the Robot's event handler. This is specified in the Robot's
 * capabilities.xml.
 * 
 * @author mprasetya@google.com (Marcel Prasetya)
 */
public enum Context {
  PARENT,
  SIBLINGS,
  CHILDREN;
  
  public static Context valueOfIgnoreCase(String name) {
    return valueOf(name.toUpperCase());
  }
}
