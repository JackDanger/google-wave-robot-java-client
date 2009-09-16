// Copyright 2009 Google Inc. All Rights Reserved.
package com.google.wave.api;

import java.util.ArrayList;
import java.util.List;

/**
 * A Capability represents a Robot's interest in handling a given event. The
 * Robot can request that additional context and document content be sent with
 * the event.
 *
 * @author mprasetya@google.com (Marcel Prasetya)
 */
public class Capability {

  /**
   * The context provided by default if non is declared.
   */
  public static final List<Context> DEFAULT_CONTEXT = getDefaultContext();

  /**
   * The list of contexts (parent, children, siblings, all) to send to the
   * Robot for this capability.
   */
  private final List<Context> contexts;

  /**
   * The associated eventType.
   */
  private EventType eventType;

  /**
   * Configures a Robot capability with the content and contexts specified.
   * @param eventType
   */
  public Capability(EventType eventType, List<Context> contexts) {
    this.eventType = eventType;
    this.contexts = contexts;
  }

  private static List<Context> getDefaultContext() {
    List<Context> res = new ArrayList<Context>();
    res.add(Context.CHILDREN);
    res.add(Context.PARENT);
    return res;
  }

  /**
   * Returns the list of contexts requested to be sent for this capability.
   * 
   * @return the list of contexts.
   */
  public List<Context> getContexts() {
    return contexts;
  }

  public EventType getEventType() {
    return eventType;
  }
}
