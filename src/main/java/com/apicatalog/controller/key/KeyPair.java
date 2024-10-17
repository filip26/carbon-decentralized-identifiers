package com.apicatalog.controller.key;

import com.apicatalog.controller.method.SignatureMethod;

public interface KeyPair extends VerificationKey, SignatureMethod {

    RawByteKey privateKey();
}
