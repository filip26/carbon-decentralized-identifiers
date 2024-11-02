package com.apicatalog.multikey;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.apicatalog.controller.key.KeyPair;
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
@Context("https://w3id.org/security/multikey/v1")
public interface Multikey extends KeyPair {

    static final String TYPE = "https://w3id.org/security#Multikey";

    @Type
    default Collection<String> types() {
        return List.of(TYPE);
    }

    @Term("publicKeyMultibase")
    @Literal(value = MulticodecKeyAdapter.class, params = { "base58btc", "base64url" })
    @Override
    MulticodecKey publicKey();

    @Term("secretKeyMultibase")
    @Literal(value = MulticodecKeyAdapter.class, params = { "base58btc", "base64url" })
    @Override
    MulticodecKey privateKey();

    static boolean equals(Multikey k1, Multikey k2) {
        if (k1 == null || k2 == null) {
            return k1 == k2;

        }
        return Objects.equals(k1.id(), k2.id())
                && Objects.equals(k1.type(), k2.type())
                && Objects.equals(k1.controller(), k2.controller())
                && Objects.equals(k1.expires(), k2.expires())
                && Objects.equals(k1.revoked(), k2.revoked())
                && MulticodecKey.equals(k1.publicKey(), k2.publicKey())
                && MulticodecKey.equals(k1.privateKey(), k2.privateKey());
    }
}
