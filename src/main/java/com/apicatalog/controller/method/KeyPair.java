package com.apicatalog.controller.method;

import com.apicatalog.controller.key.RawKey;

public interface KeyPair extends VerificationKey {

    RawKey privateKey();
}
