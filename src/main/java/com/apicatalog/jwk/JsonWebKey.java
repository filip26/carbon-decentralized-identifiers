package com.apicatalog.jwk;

import com.apicatalog.controller.method.VerificationMethod;
import com.apicatalog.linkedtree.orm.Context;
import com.apicatalog.linkedtree.orm.Fragment;
import com.apicatalog.linkedtree.orm.Term;
import com.apicatalog.linkedtree.orm.Vocab;

import jakarta.json.JsonValue;

@Fragment
@Vocab("https://w3id.org/security#")
@Context("https://w3id.org/security/jwk/v1")
public interface JsonWebKey extends VerificationMethod {

    static final String TYPE = "https://w3id.org/security#JsonWebKey";

    @Override
    default String type() {
        return TYPE;
    }

    @Term("publicKeyJwk")
    JsonValue publicKey();

    @Term("secretKeyJwk")
    JsonValue privateKey();
}
