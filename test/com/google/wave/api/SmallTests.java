/* Copyright (c) 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.wave.api;

import com.google.wave.api.event.EventSerializerTest;
import com.google.wave.api.impl.JsonRpcResponseGsonAdaptorTest;
import com.google.wave.api.impl.OperationRequestGsonAdaptorTest;
import com.google.wave.api.impl.TupleTest;
import com.google.wave.api.oauth.impl.OAuthServiceImplTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Google Wave Java Robot API small unit test suite.
 *
 * @author mprasetya@google.com (Marcel Prasetya)
 */
public class SmallTests extends TestSuite {

  public static Test suite() {
    TestSuite suite = new TestSuite();

    // Add all small tests from com.google.wave.api package.
    suite.addTestSuite(AnnotationsTest.class);
    suite.addTestSuite(AnnotationTest.class);
    suite.addTestSuite(BlipIteratorTest.class);
    suite.addTestSuite(BlipTest.class);
    suite.addTestSuite(ElementTest.class);
    suite.addTestSuite(FormElementTest.class);
    suite.addTestSuite(GadgetTest.class);
    suite.addTestSuite(ImageTest.class);
    suite.addTestSuite(OperationQueueTest.class);
    suite.addTestSuite(TagsTest.class);
    suite.addTestSuite(WaveletTest.class);

    // Add all small tests from com.google.wave.api.event package.
    suite.addTestSuite(EventSerializerTest.class);

    // Add all small tests from com.google.wave.api.impl package.
    suite.addTestSuite(JsonRpcResponseGsonAdaptorTest.class);
    suite.addTestSuite(OperationRequestGsonAdaptorTest.class);
    suite.addTestSuite(TupleTest.class);

    // Add all small tests from com.google.wave.api.oauth.impl package.
    suite.addTestSuite(OAuthServiceImplTest.class);

    return suite;
  }
}
