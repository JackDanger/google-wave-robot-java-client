// Copyright 2009 Google Inc. All Rights Reserved.
package com.google.wave.api;

/**
 * Gadgets are external code that can be executed within a protected
 * environment within a Wave. Gadgets are indentified by the url that points to
 * their gadget specification. Gadgets can also maintain state that both they
 * and Robots can modify.  
 * 
 * @author scovitz@google.com (Seth Covitz)
 * @author mprasetya@google.com (Marcel Prasetya)
 */
public class Gadget extends Element {

  /**
   * Constructs an empty gadget.
   */
  public Gadget() {
    super(ElementType.GADGET);
    setUrl("");
  }
  
  /**
   * Constructs a gadget for the specified url.
   *  
   * @param url the url of the gadget specification.
   */
  public Gadget(String url) {
    super(ElementType.GADGET);
    setUrl(url);
  }

  /**
   * Returns the URL for the gadget.
   * 
   * @return the URL for the gadget.
   */
  public String getUrl() {
    return (String) getProperty("url");
  }
  
  /**
   * Changes the URL for the gadget to the given url. This will cause the new
   * gadget to be initialized and loaded.
   * 
   * @param url the new gadget url.
   */
  public void setUrl(String url) {
    setProperty("url", url);
  }
  
  /**
   * Deletes the field represented by the given key from the gadget's state.
   * 
   * @param key The key that identifies the field.
   */
  public void deleteField(String key) {
    getProperties().remove(key);
  }
  
  /**
   * Returns the value of the field with the given key.
   * 
   * @param key The key that identifies the field.
   * @return The value of the field.
   */
  public String getField(String key) {
    return (String) getProperty(key);
  }

  /**
   * Creates or replaces a field matching the key with the given value. Only
   * a single field with a given key is allowed.
   */
  public void setField(String key, String value) {
    setProperty(key, value);
  }
}
