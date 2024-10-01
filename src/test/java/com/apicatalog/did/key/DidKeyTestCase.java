package com.apicatalog.did.key;

import java.net.URI;

public class DidKeyTestCase {

    URI uri;
    boolean negative;
    int keyLength;
    String version;

    static DidKeyTestCase create(String uri, int length) {
        return create(uri, length, "1");
    }

    static DidKeyTestCase create(String uri, int length, String version) {
        DidKeyTestCase testCase = new DidKeyTestCase();
        testCase.uri = URI.create(uri);
        testCase.negative = false;
        testCase.keyLength = length;
        testCase.version = version;
        return testCase;
    }

    static DidKeyTestCase create(String uri) {
        DidKeyTestCase testCase = new DidKeyTestCase();
        testCase.uri = uri != null ? URI.create(uri) : null ;
        testCase.negative = true;
        return testCase;
    }
    
}
