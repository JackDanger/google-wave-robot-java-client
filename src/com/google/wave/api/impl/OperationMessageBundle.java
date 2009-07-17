// Copyright 2009 Google Inc. All Rights Reserved.
package com.google.wave.api.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a bundle of operations to be executed in order when returned to
 * the robot proxy server.
 * 
 * @author scovitz@google.com (Seth Covitz)
 */
public class OperationMessageBundle {

  private List<Operation> operations = new ArrayList<Operation>();
  private String version;

  public List<Operation> getOperations() {
    return operations;
  }

  public void setOperations(List<Operation> operations) {
    this.operations = operations;
  }

  public void add(Operation operation) {
    operations.add(operation);
  }

  public String getVersion() {
    return version;
  }
  
  public void setVersion(String version) {
    this.version = version;
  }
}
