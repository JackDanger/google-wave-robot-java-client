// Copyright 2009 Google Inc. All Rights Reserved.
package com.google.wave.api.impl;


import java.util.ArrayList;
import java.util.List;

/**
 * EventMessage class used to represent a message sent to the Robot.
 * 
 * @author scovitz@google.com (Seth Covitz)
 */
public class EventMessage {

  private EventData event;
  
  private final List<BlipData> blips;
  
  private WaveletData wavelet;

  public EventMessage() {
    event = null;
    blips = new ArrayList<BlipData>();
    wavelet = null;
  }
  
  public void addBlip(BlipData blip) {
    this.blips.add(blip);
  }

  public void addWavelet(WaveletData wavelet) {
    this.wavelet = wavelet;
  }

  public EventData getEvent() {
    return event;
  }

  public boolean hasBlip() {
    return !blips.isEmpty();
  }
  
  public boolean hasEvent() {
    return event != null;
  }

  public boolean hasWavelet() {
    return wavelet != null;
  }

  public void setEvent(EventData event) {
    this.event = event;
  }

  public WaveletData getWavelet() {
    return wavelet;
  }

  public List<BlipData> getBlips() {
    return blips;
  }
}
