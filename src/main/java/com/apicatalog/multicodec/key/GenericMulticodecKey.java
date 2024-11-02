package com.apicatalog.multicodec.key;

import java.util.Objects;

import com.apicatalog.multicodec.Multicodec;

public record GenericMulticodecKey(
        Multicodec codec,
        byte[] rawBytes
        ) implements MulticodecKey {
    
    public GenericMulticodecKey {
        Objects.requireNonNull(codec);
        Objects.requireNonNull(rawBytes);
    }
}
