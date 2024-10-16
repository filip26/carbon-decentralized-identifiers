package com.apicatalog.controller.key;

public interface RawKeyBytes {

    String type();

    /**
     * Get raw key value.
     * 
     * @return raw key value as byte array
     */
    byte[] rawBytes();

}
