// Copyright 2009 Google Inc. All Rights Reserved.
package com.google.wave.api.impl;

import com.google.wave.api.Element;
import com.google.wave.api.FormElement;
import com.google.wave.api.FormView;
import com.google.wave.api.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * FormView implementation.
 * 
 * @author scovitz@google.com (Seth Covitz)
 */
public class FormViewImpl implements FormView {

  final TextView textView;
  
  public FormViewImpl(TextView textView) {
    this.textView = textView;
  }

  @Override
  public void append(FormElement formElement) {
    textView.appendElement(formElement);
  }

  @Override
  public void delete(String name) {
    FormElement formElement = getFormElement(name);
    if (formElement != null) {
      textView.deleteElement(textView.getPosition(formElement));
    }
  }

  @Override
  public FormElement getFormElement(String name) {
    if (textView.getElements() != null) {
      for (Element element : textView.getElements()) {
        if (element.getProperties().containsKey("name") &&
            name.equals(element.getProperty("name"))) {
          return (FormElement) element;
        }
      }
    }
    return null;
  }

  @Override
  public List<FormElement> getFormElements() {
    List<FormElement> formElements = new ArrayList<FormElement>();
    for (Element element : textView.getElements()) {
      if (element.isFormElement()) {
        formElements.add((FormElement) element);
      }
    }
    return formElements;
  }

  @Override
  public void insertAfter(String name, FormElement formElement) {
    textView.insertElement(textView.getPosition(getFormElement(name)) + 1, formElement);
  }

  @Override
  public void insertBefore(String name, FormElement formElement) {
    textView.insertElement(textView.getPosition(getFormElement(name)), formElement);
  }

  @Override
  public void replace(String name, FormElement formElement) {
    textView.replaceElement(textView.getPosition(getFormElement(name)), formElement);
  }

  @Override
  public void delete(FormElement formElement) {
    textView.deleteElement(textView.getPosition(formElement));
  }

  @Override
  public void insertAfter(FormElement after, FormElement formElement) {
    textView.insertElement(textView.getPosition(after) + 1, formElement);
  }

  @Override
  public void insertBefore(FormElement before, FormElement formElement) {
    textView.insertElement(textView.getPosition(before), formElement);
  }

  @Override
  public void replace(FormElement formElement) {
    textView.replaceElement(-1, formElement);
  }

  @Override
  public void replace(FormElement toReplace, FormElement formElement) {
    textView.replaceElement(textView.getPosition(toReplace), formElement);
  }
}
