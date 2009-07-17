// Copyright 2009 Google Inc. All Rights Reserved.
package com.google.wave.api.impl;

/**
 * An Operation is a robot initiated change to a Wavelet, Blip or Document. 
 * 
 * @author scovitz@google.com (Seth Covitz)
 * @author mprasetya@google.com (Marcel Prasetya)
 */
public interface Operation {
  
  /**
   * Returns the Wave Id associated with this operation.
   * 
   * @return the Wave Id associated with this operation.
   */
  public String getWaveId();
  
  /**
   * Sets the Wave Id associated with this operation.
   */
  public void setWaveId(String waveId);
  
  /**
   * Returns the Wavelet Id associated with this operation.
   * 
   * @return the Wavelet Id associated with this operation.
   */
  public String getWaveletId();
  
  /**
   * Sets the Wavelet Id associated with this operation.
   */
  public void setWaveletId(String waveletId);
  
  /**
   * Returns the Blip Id associated with this operation.
   * 
   * @return the Blip Id associated with this operation.
   */
  public String getBlipId();

  /**
   * Sets the Blip Id associated with this operation.
   */
  public void setBlipId(String blipId);
  
  /**
   * Returns the type of operation.
   * 
   * @return the type of operation.
   */
  public OperationType getType();
  
  /**
   * Sets the type of operation.
   */
  public void setType(OperationType type);
  
  /**
   * Returns the index into the text document associated with this operation.
   * This field is only applicable for operations that modify the document's
   * content.
   * 
   * @return the index associated with this operation.
   */
  public int getIndex();

  /**
   * Sets the index into the text document associated with this operation. This
   * field is only applicable for operations that modify the document's content.
   * 
   * @param index the index into the text document.
   */
  public void setIndex(int index);

  /**
   * Sets a property on the event.
   */
  public void setProperty(Object property);

  /**
   * Returns a property of the event.
   * 
   * @return a property of the event.
   */
  public Object getProperty();
}
