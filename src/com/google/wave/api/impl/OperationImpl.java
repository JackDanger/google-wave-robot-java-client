// Copyright 2009 Google Inc. All Rights Reserved.
package com.google.wave.api.impl;

/**
 * Implementation class for an Operation.
 * 
 * @author scovitz@google.com (Seth Covitz)
 */
public class OperationImpl implements Operation {

  private String waveId;
  private String waveletId;
  private String blipId;
  private OperationType type;
  private int index;
  private Object property;
  
  public OperationImpl(OperationType type, String waveId, String waveletId, String blipId,
      int index, Object property) {
    this.type = type;
    this.waveId = waveId;
    this.waveletId = waveletId;
    this.blipId = blipId;
    this.index = index;
    this.property = property;
  }
    
  /* (non-Javadoc)
   * @see com.google.wave.api.impl.Operation#getBlipId()
   */
  public String getBlipId() {
    return blipId;
  }

  /* (non-Javadoc)
   * @see com.google.wave.api.impl.Operation#getType()
   */
  public OperationType getType() {
    return type;
  }

  /* (non-Javadoc)
   * @see com.google.wave.api.impl.Operation#getWaveId()
   */
  public String getWaveId() {
    return waveId;
  }

  /* (non-Javadoc)
   * @see com.google.wave.api.impl.Operation#getWaveletId()
   */
  public String getWaveletId() {
    return waveletId;
  }

  /* (non-Javadoc)
   * @see com.google.wave.api.impl.Operation#setBlipId(java.lang.String)
   */
  public void setBlipId(String blipId) {
    this.blipId = blipId;
  }

  /* (non-Javadoc)
   * @see com.google.wave.api.impl.Operation#setType(com.google.wave.api.OperationType)
   */
  public void setType(OperationType type) {
    this.type = type;
  }

  /* (non-Javadoc)
   * @see com.google.wave.api.impl.Operation#setWaveId(java.lang.String)
   */
  public void setWaveId(String waveId) {
    this.waveId = waveId;
  }

  /* (non-Javadoc)
   * @see com.google.wave.api.impl.Operation#setWaveletId(java.lang.String)
   */
  public void setWaveletId(String waveletId) {
    this.waveletId = waveletId;
  }

  @Override
  public int getIndex() {
    return index;
  }

  @Override
  public Object getProperty() {
    return property;
  }

  @Override
  public void setIndex(int index) {
    this.index = index;
  }

  @Override
  public void setProperty(Object property) {
    this.property = property;
  }
}
