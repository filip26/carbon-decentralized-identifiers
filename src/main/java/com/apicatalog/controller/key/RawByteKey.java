package com.apicatalog.controller.key;

public interface RawByteKey {

    String type();

    /**
     * Get raw key value.
     * 
     * @return raw key value as byte array
     */
    byte[] rawBytes();

}
