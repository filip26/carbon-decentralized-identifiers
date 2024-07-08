package com.apicatalog.did.document;

import com.apicatalog.did.Did;
import com.apicatalog.did.DidUrl;

public class DidVerificationMethod {
    
    final DidUrl id;
    final Did controller; 
    final byte[] publicKey;

    public DidVerificationMethod(
            DidUrl id, 
            Did controller, 
            byte[] publicKey
        ) {
        this.id = id;
        this.controller = controller;
        this.publicKey = publicKey;
    }
    
    public DidUrl id() {
        return id;
    }
    
    public Did controller() {
        return controller;
    }
        
    public byte[] publicKey() {
        return publicKey;
    }
}
