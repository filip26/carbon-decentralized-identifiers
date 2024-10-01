package com.apicatalog.controller.method;

import com.apicatalog.controller.key.Key;

public interface KeyPair extends VerificationKey {

    Key privateKey();
}
