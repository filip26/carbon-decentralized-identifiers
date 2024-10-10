package com.apicatalog.jwk;

import com.apicatalog.controller.method.KeyPair;
import com.apicatalog.linkedtree.orm.Fragment;
import com.apicatalog.linkedtree.orm.Term;
import com.apicatalog.linkedtree.orm.Vocab;

@Fragment
@Vocab("https://w3id.org/security#")
public interface JsonWebKey extends KeyPair {

    @Override
    default String type() {
        return "https://w3id.org/security#JsonWebKey";
    }

    @Term("publicKeyJwk")
    EmbeddedJwk publicKey();

    @Term("secretKeyJwk")
    EmbeddedJwk secretKeyJwk();
}
