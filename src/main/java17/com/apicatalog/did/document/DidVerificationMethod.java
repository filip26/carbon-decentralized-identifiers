package com.apicatalog.did.document;

import java.util.Objects;

import com.apicatalog.did.Did;
import com.apicatalog.did.DidUrl;

public record DidVerificationMethod(
                    DidUrl id, 
                    Did controller, 
                    String curve,
                    byte[] publicKey
                ) {

    public DidVerificationMethod {
        Objects.requireNonNull(id);
        Objects.requireNonNull(controller);
    }
}
