package com.apicatalog.controller.method;

public interface RawKey {

    String type();

    /**
     * Get raw key value.
     * 
     * @return raw key value as byte array
     */
    byte[] rawBytes();

}
