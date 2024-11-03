package com.apicatalog.multikey;

import java.util.Collection;
import java.util.List;

import com.apicatalog.controller.key.KeyPair;
import com.apicatalog.controller.method.VerificationMethod;
import com.apicatalog.linkedtree.orm.Compaction;
import com.apicatalog.linkedtree.orm.Context;
import com.apicatalog.linkedtree.orm.Fragment;
import com.apicatalog.linkedtree.orm.Literal;
import com.apicatalog.linkedtree.orm.Term;
import com.apicatalog.linkedtree.orm.Type;
import com.apicatalog.linkedtree.orm.Vocab;
import com.apicatalog.multicodec.key.MulticodecKey;
import com.apicatalog.multicodec.key.MulticodecKeyAdapter;

@Fragment
@Vocab("https://w3id.org/security#")
@Context(value =  "https://w3id.org/security/multikey/v1", override = true)
public interface Multikey extends KeyPair {

    static final String TYPE = "https://w3id.org/security#Multikey";

    @Type
    default Collection<String> types() {
        return List.of(TYPE);
    }

    @Term("publicKeyMultibase")
    @Literal(value = MulticodecKeyAdapter.class, params = { "base58btc", "base64url" })
    @Compaction(order = 40)
    @Override
    MulticodecKey publicKey();

    @Term("secretKeyMultibase")
    @Literal(value = MulticodecKeyAdapter.class, params = { "base58btc", "base64url" })
    @Compaction(order = 50)
    @Override
    MulticodecKey privateKey();

    static boolean equals(Multikey k1, Multikey k2) {
        return VerificationMethod.equals(k1, k2)
                && MulticodecKey.equals(k1.publicKey(), k2.publicKey())
                && MulticodecKey.equals(k1.privateKey(), k2.privateKey());
    }
}
