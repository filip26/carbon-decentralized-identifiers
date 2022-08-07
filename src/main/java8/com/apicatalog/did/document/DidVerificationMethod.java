package com.apicatalog.did.document;

import java.util.Objects;

import com.apicatalog.did.Did;
import com.apicatalog.did.DidUrl;

public class DidVerificationMethod {
    
    final DidUrl id;
    final Did controller; 
    final String type;
    //TODO publicKeyJwk
    final byte[] publicKey;

    public DidVerificationMethod(
            DidUrl id, 
            Did controller, 
            String type,
            byte[] publicKey
        ) {
        this.id = id;
        this.controller = controller;
        this.type = type;
        this.publicKey = publicKey;
    }
    
    public DidUrl id() {
        return id;
    }
    
    public Did controller() {
        return controller;
    }
    
    public String type() {
        return type;
    }
    
    public byte[] publicKey() {
        return publicKey;
    }
}
