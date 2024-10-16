package com.apicatalog.controller.key;

import com.apicatalog.controller.method.VerificationMethod;

public interface VerificationKey extends VerificationMethod {
    
    RawKeyBytes publicKey();

}
