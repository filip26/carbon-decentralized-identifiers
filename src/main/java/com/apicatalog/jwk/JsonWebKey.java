package com.apicatalog.jwk;

import com.apicatalog.controller.method.VerificationMethod;
import com.apicatalog.linkedtree.orm.Fragment;
import com.apicatalog.linkedtree.orm.Term;
import com.apicatalog.linkedtree.orm.Vocab;

@Fragment
@Vocab("https://w3id.org/security#")
public interface JsonWebKey extends VerificationMethod {

    static final String TYPE = "https://w3id.org/security#JsonWebKey";

    @Override
    default String type() {
        return TYPE;
    }

    @Term("publicKeyJwk")
    JwkLiteral publicKey();

    @Term("secretKeyJwk")
    JwkLiteral privateKey();
}
