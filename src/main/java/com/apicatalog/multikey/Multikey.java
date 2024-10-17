package com.apicatalog.multikey;

import com.apicatalog.controller.key.KeyPair;
import com.apicatalog.linkedtree.orm.Context;
import com.apicatalog.linkedtree.orm.Fragment;
import com.apicatalog.linkedtree.orm.Literal;
import com.apicatalog.linkedtree.orm.Term;
import com.apicatalog.linkedtree.orm.Vocab;
import com.apicatalog.multicodec.key.MulticodecKey;
import com.apicatalog.multicodec.key.MulticodecKeyAdapter;

@Fragment
@Vocab("https://w3id.org/security#")
@Context("https://w3id.org/security/multikey/v1")
public interface Multikey extends KeyPair {

    static final String TYPE = "https://w3id.org/security#Multikey";

    @Override
    default String type() {
        return TYPE;
    }

    @Term("publicKeyMultibase")
    @Literal(value = MulticodecKeyAdapter.class, params = { "base58btc", "base64url" })
    @Override
    MulticodecKey publicKey();

    @Term("secretKeyMultibase")
    @Literal(value = MulticodecKeyAdapter.class, params = { "base58btc", "base64url" })
    @Override
    MulticodecKey privateKey();
}
