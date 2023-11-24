package com.apicatalog.did.document;

import com.apicatalog.did.Did;
import com.apicatalog.did.DidUrl;
import com.apicatalog.multicodec.Multicodec;

public class DidVerificationMethod {
    
    final DidUrl id;
    final Did controller; 
    final Multicodec codec;
    final byte[] publicKey;

    public DidVerificationMethod(
            DidUrl id, 
            Did controller, 
            Multicodec codec,
            byte[] publicKey
        ) {
        this.id = id;
        this.controller = controller;
        this.codec = codec;
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
    
    public Multicodec codec() {
        return codec;
    }
}
