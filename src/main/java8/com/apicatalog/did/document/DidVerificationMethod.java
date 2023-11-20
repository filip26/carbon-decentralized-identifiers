package com.apicatalog.did.document;

import com.apicatalog.did.Did;
import com.apicatalog.did.DidUrl;

public class DidVerificationMethod {
    
    final DidUrl id;
    final Did controller; 
    final String curve;
    final byte[] publicKey;

    public DidVerificationMethod(
            DidUrl id, 
            Did controller, 
            String curve,
            byte[] publicKey
        ) {
        this.id = id;
        this.controller = controller;
        this.curve = curve;
        this.publicKey = publicKey;
    }
    
    public DidUrl id() {
        return id;
    }
    
    public Did controller() {
        return controller;
    }
    
    public String curve() {
        return curve;
    }
    
    public byte[] publicKey() {
        return publicKey;
    }
}
