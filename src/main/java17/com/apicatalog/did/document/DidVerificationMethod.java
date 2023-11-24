package com.apicatalog.did.document;

import java.util.Objects;

import com.apicatalog.did.Did;
import com.apicatalog.did.DidUrl;
import com.apicatalog.multicodec.Multicodec;

public record DidVerificationMethod(
                    DidUrl id, 
                    Did controller, 
                    Multicodec codec,
                    byte[] publicKey
                ) {

    public DidVerificationMethod {
        Objects.requireNonNull(id);
        Objects.requireNonNull(controller);
    }
}
