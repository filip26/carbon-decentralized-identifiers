package com.apicatalog.multicodec.key;

import java.util.Objects;

import com.apicatalog.multibase.Multibase;
import com.apicatalog.multicodec.Multicodec;

public record GenericMulticodecKey(
        Multicodec codec,
        Multibase base,
        byte[] rawBytes
        ) implements MulticodecKey {
    
    public GenericMulticodecKey {
        Objects.requireNonNull(codec);
        Objects.requireNonNull(base);
        Objects.requireNonNull(rawBytes);
    }
    
    public static MulticodecKey of(Multicodec codec, Multibase base, String encoded) {
        return new GenericMulticodecKey(codec, base, codec.decode(base.decode(encoded)));
    }
}
