package com.apicatalog.controller.method;

import com.apicatalog.controller.key.RawKey;

public interface VerificationKey extends VerificationMethod {
    
    RawKey publicKey();

}
