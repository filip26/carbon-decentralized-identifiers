package com.apicatalog.did.document;

import java.util.Objects;

import com.apicatalog.did.Did;
import com.apicatalog.did.DidUrl;

public record DidVerificationMethod(
                    DidUrl id, 
                    Did controller, 
                    String type,
                    //TODO publicKeyJwk
                    byte[] publicKey
                ) {

    public DidVerificationMethod {
        Objects.requireNonNull(id);
        Objects.requireNonNull(controller);
        Objects.requireNonNull(type);
    }
    
}
