// Copyright 2009 Google Inc. All Rights Reserved.
package com.google.wave.api.impl;

import com.metaparadigm.jsonrpc.AbstractSerializer;
import com.metaparadigm.jsonrpc.MarshallException;
import com.metaparadigm.jsonrpc.ObjectMatch;
import com.metaparadigm.jsonrpc.SerializerState;
import com.metaparadigm.jsonrpc.UnmarshallException;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Operation serializer/deserializer.
 * 
 * @author scovitz@google.com (Seth Covitz)
 */
@SuppressWarnings("unchecked")
public class OperationSerializer extends AbstractSerializer {

  private static final String FIELD_JAVA_CLASS = "javaClass";
  private static final String FIELD_PROPERTY = "property";
  private static final String FIELD_INDEX = "index";
  private static final String FIELD_TYPE = "type";
  private static final String FIELD_BLIP_ID = "blipId";
  private static final String FIELD_WAVELET_ID = "waveletId";
  private static final String FIELD_WAVE_ID = "waveId";
  private static final Class[] SERIALIZABLE_CLASSES = new Class[] { OperationImpl.class };
  private static final Class[] JSON_CLASSES = new Class[] { JSONObject.class };

  @Override
  public Class[] getJSONClasses() {
    return JSON_CLASSES;
  }

  @Override
  public Class[] getSerializableClasses() {
    return SERIALIZABLE_CLASSES;
  }

  @Override
  public Object marshall(SerializerState state, Object o) throws MarshallException {
    if (!(o instanceof Operation)) {
      throw new MarshallException("Object is not of type Operation.");
    }
    
    JSONObject json = new JSONObject();
    Operation operation = (Operation) o;
    try {
      json.put(FIELD_JAVA_CLASS, OperationImpl.class.getName());
      json.put(FIELD_WAVE_ID, ser.marshall(state, operation.getWaveId()));
      json.put(FIELD_WAVELET_ID, ser.marshall(state, operation.getWaveletId()));
      json.put(FIELD_BLIP_ID, ser.marshall(state, operation.getBlipId()));
      json.put(FIELD_TYPE, operation.getType().toString());
      json.put(FIELD_INDEX, operation.getIndex());
      json.put(FIELD_PROPERTY, ser.marshall(state, operation.getProperty()));
    } catch (JSONException jsonx) {
      throw new MarshallException("Cannot marshall Operation.");
    }
    
    return json;
  }

  @SuppressWarnings("unused")
  @Override
  public ObjectMatch tryUnmarshall(SerializerState state, Class clazz, Object json)
      throws UnmarshallException {
    throw new UnsupportedOperationException();
  }

  @Override
  public Object unmarshall(SerializerState state, Class clazz, Object json)
      throws UnmarshallException {
    if (!Operation.class.isAssignableFrom(clazz)) {
      throw new UnmarshallException(clazz.getName() + " is not assignable from OperationType");
    }

    JSONObject jsonObject = (JSONObject) json;
    Operation operation = null;
    try {
      String waveId = (String) ser.unmarshall(state, String.class, jsonObject.get(FIELD_WAVE_ID));
      String waveletId = (String) ser.unmarshall(state,
                                                 String.class,
                                                 jsonObject.get(FIELD_WAVELET_ID));
      String blipId = (String) ser.unmarshall(state, String.class, jsonObject.get(FIELD_BLIP_ID));
      OperationType type = OperationType.valueOf(jsonObject.getString(FIELD_TYPE));
      int index = jsonObject.isNull(FIELD_INDEX)?-1:jsonObject.getInt(FIELD_INDEX);
      Object property = jsonObject.isNull(FIELD_PROPERTY)?null:jsonObject.get(FIELD_PROPERTY);
      operation = new OperationImpl(type, waveId, waveletId, blipId, index, property);
    } catch (JSONException jsonx) {
      throw new UnmarshallException(clazz.getName() + " could not be constructed.");
    }

    return operation;
  }
}
