package com.apicatalog.jwk;

import com.apicatalog.controller.method.KeyPair;
import com.apicatalog.linkedtree.json.JsonLiteral;
import com.apicatalog.linkedtree.orm.Fragment;
import com.apicatalog.linkedtree.orm.Literal;
import com.apicatalog.linkedtree.orm.Term;
import com.apicatalog.linkedtree.orm.Vocab;

@Fragment
@Vocab("https://w3id.org/security#")
public interface JsonWebKey extends KeyPair {

    static final String TYPE = "https://w3id.org/security#JsonWebKey";

    @Override
    default String type() {
        return TYPE;
    }

    @Term("publicKeyJwk")
    @Override
    JwkLiteral publicKey();

    @Term("secretKeyJwk")
    @Override
    JwkLiteral privateKey();
}
