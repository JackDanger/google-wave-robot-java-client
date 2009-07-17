// Copyright 2009 Google Inc. All Rights Reserved.
package com.google.wave.api.impl;

import com.google.wave.api.Blip;
import com.google.wave.api.Event;
import com.google.wave.api.EventType;
import com.google.wave.api.RobotMessageBundle;
import com.google.wave.api.Wavelet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RobotMessageBundle implementation.
 * 
 * @author scovitz@google.com (Seth Covitz)
 */
public class RobotMessageBundleImpl implements RobotMessageBundle {

  private final OperationMessageBundle operationMessageBundle;
  private final EventMessageBundle eventMessageBundle;
  private final String robotAddress;
  private final Map<Tuple<String>, Blip> blips;
  private final Map<Tuple<String>, Wavelet> wavelets;
  private List<Event> events;
  private Wavelet wavelet;

  public RobotMessageBundleImpl(EventMessageBundle eventMessageBundle,
      String robotAddress) {
    this.eventMessageBundle = eventMessageBundle;
    this.operationMessageBundle = new OperationMessageBundle();
    this.robotAddress = robotAddress;
    this.blips = new HashMap<Tuple<String>, Blip>();
    this.wavelets = new HashMap<Tuple<String>, Wavelet>();
  }

  @Override
  public boolean wasParticipantAddedToNewWave(String participantId) {
    return wasParticipantAddedToWave(participantId) && isNewWave();
  }

  @Override
  public boolean wasSelfAdded() {
    return !filterEventsByType(EventType.WAVELET_SELF_ADDED).isEmpty();
  }

  @Override
  public boolean wasSelfRemoved() {
    return !filterEventsByType(EventType.WAVELET_SELF_REMOVED).isEmpty();
  }
  
  @Override
  public boolean isNewWave() {
    return !getWavelet().getRootBlip().hasChildren();
  }
  
  @Override
  public boolean wasParticipantAddedToWave(String participantId) {
    for (Event event : getParticipantsChangedEvents()) {
      if (event.getAddedParticipants().contains(participantId)) {
        return true;
      }
    }
    return false;
  }
  
  @Override
  public List<Event> getParticipantsChangedEvents() {
    return filterEventsByType(EventType.WAVELET_PARTICIPANTS_CHANGED);
  }
  
  @Override
  public List<Event> getBlipSubmittedEvents() {
    return filterEventsByType(EventType.BLIP_SUBMITTED);
  }

  @Override
  public List<Event> filterEventsByType(EventType eventType) {
    List<Event> filteredEvents = new ArrayList<Event>();
    for (Event event : getEvents()) {
      if (eventType == event.getType()) {
        filteredEvents.add(event);
      }
    }
    return filteredEvents;
  }
  
  public void addOperation(Operation operation) {
    operationMessageBundle.add(operation);
  }
  
  public OperationMessageBundle getOperations() {
    return operationMessageBundle;
  }
  
  @Override
  public Wavelet getWavelet() {
    if (wavelet == null) {
      wavelet = new WaveletImpl(eventMessageBundle.getWaveletData(), this);
    }
    return wavelet;
  }
  
  public Map<String, BlipData> getBlipData() {
   return eventMessageBundle.getBlipData(); 
  }

  @Override
  public boolean blipHasChanged(Blip blip) {
    if (blip != null) {
      for (EventData event : eventMessageBundle.getEvents()) {
        if (event.getType().equals(EventType.DOCUMENT_CHANGED)) {
          if (event.getProperties().get("blipId").equals(blip.getBlipId())) {
            return true;
          }
        }
      }
    }
    return false;
  }

  @Override
  public List<Event> getEvents() {
    if (events == null) {
      events = new ArrayList<Event>();
      for (EventData event : eventMessageBundle.getEvents()) {
        events.add(new EventImpl(event, this));
      }
    }
    return events;
  }

  @Override
  public Wavelet createWavelet(List<String> participants) {
    return createWavelet(participants, null);
  }

  @Override
  public Wavelet createWavelet(List<String> participants, String annotationWriteBack) {
    WaveletData waveletData = new WaveletData();
    waveletData.setWaveId("TBD" + Math.random());
    waveletData.setWaveletId("conv+root");
    waveletData.setParticipants(participants);
    BlipData blipData = new BlipData();
    blipData.setBlipId("TBD" + Math.random());
    blipData.setWaveId(waveletData.getWaveId());
    blipData.setWaveletId(waveletData.getWaveletId());
    eventMessageBundle.getBlipData().put(blipData.getBlipId(), blipData);
    waveletData.setRootBlipId(blipData.getBlipId());
    
    addOperation(new OperationImpl(OperationType.WAVELET_CREATE, getWavelet().getWaveId(),
        getWavelet().getWaveletId(), annotationWriteBack, -1, waveletData));
    
    return new WaveletImpl(waveletData, this);
  }

  @Override
  public Wavelet getWavelet(String waveId, String waveletId) {
    Tuple<String> key = Tuple.of(waveId, waveletId);
    Wavelet result = wavelets.get(key);
    if (result == null) {
      Wavelet wavelet = getWavelet();
      if (waveId.equals(wavelet.getWaveId()) && waveletId.equals(wavelet.getWaveletId())) {
        result = wavelet;
      } else {
        WaveletData waveletData = new WaveletData();
        waveletData.setWaveId(waveId);
        waveletData.setWaveletId(waveletId);
        result = new WaveletImpl(waveletData, this);
      }
      wavelets.put(key, result);
    }
    return result;
  }

  @Override
  public Blip getBlip(String waveId, String waveletId, String blipId) {
    Tuple<String> key = Tuple.of(waveId, waveletId, blipId);
    Blip blip = blips.get(key);
    if (blip == null) {
      BlipData blipData = eventMessageBundle.getBlipData().get(blipId);
      if (blipData == null) {
        blipData = new BlipData();
        blipData.setWaveId(waveId);
        blipData.setWaveletId(waveletId);
        blipData.setBlipId(blipId);
      }
      blip = new BlipImpl(blipData, this);
      blips.put(key, blip);
    }
    return blip;
  }
  
  @Override
  public String getRobotAddress() {
    return robotAddress;
  }
}
