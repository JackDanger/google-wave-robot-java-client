// Copyright 2009 Google Inc. All Rights Reserved.
package com.google.wave.api.impl;

import com.google.wave.api.Element;
import com.google.wave.api.Gadget;
import com.google.wave.api.GadgetView;
import com.google.wave.api.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * GadgetView implementation.
 * 
 * @author scovitz@google.com (Seth Covitz)
 */
public class GadgetViewImpl implements GadgetView {

  private final TextView textView;
  
  public GadgetViewImpl(TextView textView) {
    this.textView = textView;
  }

  @Override
  public void append(Gadget gadget) {
    textView.appendElement(gadget);
  }

  @Override
  public void delete(Gadget gadget) {
    textView.deleteElement(
        textView.getPosition(gadget));
  }

  @Override
  public void delete(String url) {
    Gadget gadget = getGadget(url);
    if (gadget != null) {
      delete(gadget);
    }
  }

  @Override
  public Gadget getGadget(String url) {
    for (Element element : textView.getElements()) {
      if (element.isGadget() &&
          element.getProperty("url") != null &&
          element.getProperty("url").equals(url)) {
        return (Gadget) element;
      }
    }
    return null;
  }

  @Override
  public List<Gadget> getGadgets() {
    List<Gadget> gadgets = new ArrayList<Gadget>();
    for (Element element : textView.getElements()) {
      if (element.isGadget()) {
        gadgets.add((Gadget) element);
      }
    }
    return gadgets;
  }

  @Override
  public void insertAfter(Gadget after, Gadget gadget) {
    textView.insertElement(textView.getPosition(after) + 2, gadget);
  }

  @Override
  public void insertAfter(String url, Gadget gadget) {
    textView.insertElement(textView.getPosition(getGadget(url)) + 2, gadget);
  }

  @Override
  public void insertBefore(Gadget before, Gadget gadget) {
    textView.insertElement(textView.getPosition(before), gadget);
  }

  @Override
  public void insertBefore(String url, Gadget gadget) {
    textView.insertElement(textView.getPosition(getGadget(url)), gadget);
  }

  @Override
  public void replace(Gadget gadget) {
    textView.replaceElement(textView.getPosition(getGadget(gadget.getUrl())), gadget);
  }

  @Override
  public void replace(Gadget toReplace, Gadget replaceWith) {
    textView.replaceElement(textView.getPosition(toReplace), replaceWith);
  }

  @Override
  public void replace(String url, Gadget gadget) {
    textView.replaceElement(textView.getPosition(getGadget(url)), gadget);
  }
}
