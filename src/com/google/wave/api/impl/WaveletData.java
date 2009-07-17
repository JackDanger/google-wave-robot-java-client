// Copyright 2009 Google Inc. All Rights Reserved.
package com.google.wave.api.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The data representation of Wavelet metadata used to serialize and send to
 * the Robot.
 * 
 * @author scovitz@google.com (Seth Covitz)
 */
public class WaveletData {

  private long creationTime;
  private String creator;
  private long lastModifiedTime;
  private List<String> participants;
  private String rootBlipId;
  private String title;
  private long version;
  private String waveId;
  private String waveletId;
  private Map<String, String> dataDocuments;

  public WaveletData() {
    creationTime = -1L;
    creator = null;
    lastModifiedTime = -1L;
    participants = new ArrayList<String>();
    rootBlipId = null;
    title = null;
    version = -1L;
    waveId = null;
    waveletId = null;
    dataDocuments = new HashMap<String, String>();
  }
  
  public WaveletData(WaveletData wavelet) {
    this.creationTime = wavelet.getCreationTime();
    this.creator = wavelet.getCreator();
    this.lastModifiedTime = wavelet.getLastModifiedTime();
    this.participants = wavelet.getParticipants();
    this.rootBlipId = wavelet.getRootBlipId();
    this.title = wavelet.getTitle();
    this.version = wavelet.getVersion();
    this.waveId = wavelet.getWaveId();
    this.waveletId = wavelet.getWaveletId();
    this.dataDocuments = wavelet.getDataDocuments();
  }

  public long getCreationTime() {
    return creationTime;
  }

  public String getCreator() {
    return creator;
  }

  public long getLastModifiedTime() {
    return lastModifiedTime;
  }

  public List<String> getParticipants() {
    return participants;
  }

  public String getRootBlipId() {
    return rootBlipId;
  }
  
  public String getTitle() {
    return title;
  }

  public long getVersion() {
    return version;
  }

  public String getWaveId() {
    return waveId;
  }
  
  public String getWaveletId() {
    return waveletId;
  }

  public void setCreationTime(long creationTime) {
    this.creationTime = creationTime;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  public void setLastModifiedTime(long lastModifiedTime) {
    this.lastModifiedTime = lastModifiedTime;
  }

  public void setParticipants(List<String> participants) {
    this.participants = participants;
  }

  public void setRootBlipId(String rootBlipId) {
    this.rootBlipId = rootBlipId;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }

  public void setVersion(long version) {
    this.version = version;
  }
  
  public void setWaveId(String waveId) {
    this.waveId = waveId;
  }

  public void setWaveletId(String waveletId) {
    this.waveletId = waveletId;
  }

  public Map<String, String> getDataDocuments() {
    return dataDocuments;
  }

  public void setDataDocuments(Map<String, String> dataDocuments) {
    this.dataDocuments = dataDocuments;
  }

  public void setDataDocument(String name, String data) {
    dataDocuments.put(name, data);
  }
  
  public String getDataDocument(String name) {
    if (dataDocuments == null) {
      return null;
    } else {
      return dataDocuments.get(name);
    }
  }
}
