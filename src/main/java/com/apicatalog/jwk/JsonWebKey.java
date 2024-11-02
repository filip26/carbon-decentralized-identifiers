package com.apicatalog.jwk;

import java.util.Objects;

import com.apicatalog.controller.method.VerificationMethod;
import com.apicatalog.linkedtree.orm.Context;
import com.apicatalog.linkedtree.orm.Fragment;
import com.apicatalog.linkedtree.orm.Term;
import com.apicatalog.linkedtree.orm.Type;
import com.apicatalog.linkedtree.orm.Vocab;

import jakarta.json.JsonValue;

@Fragment
@Vocab("https://w3id.org/security#")
@Context(value = "https://w3id.org/security/jwk/v1", override = true)
public interface JsonWebKey extends VerificationMethod {

    static final String TYPE = "https://w3id.org/security#JsonWebKey";

    @Type
    @Override
    default String type() {
        return TYPE;
    }

    @Term("publicKeyJwk")
    JsonValue publicKey();

    @Term("secretKeyJwk")
    JsonValue privateKey();

    static boolean equals(JsonWebKey k1, JsonWebKey k2) {
        return VerificationMethod.equals(k1, k2)
                && Objects.equals(k1.publicKey(), k2.publicKey())
                && Objects.equals(k1.privateKey(), k2.privateKey());
    }
}
