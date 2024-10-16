package com.apicatalog.multikey;

import com.apicatalog.controller.key.KeyPair;
import com.apicatalog.linkedtree.orm.Context;
import com.apicatalog.linkedtree.orm.Fragment;
import com.apicatalog.linkedtree.orm.Literal;
import com.apicatalog.linkedtree.orm.Mapper;
import com.apicatalog.linkedtree.orm.Term;
import com.apicatalog.linkedtree.orm.Vocab;
import com.apicatalog.multibase.MultibaseLiteralAdapter;
import com.apicatalog.multicodec.key.MulticodecKey;
import com.apicatalog.multicodec.key.MulticodecKeyMapper;

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
    @Literal(value = MultibaseLiteralAdapter.class, params = { "base58btc", "base64url" })
    @Mapper(MulticodecKeyMapper.class)
    @Override
    MulticodecKey publicKey();

    @Term("secretKeyMultibase")
    @Literal(value = MultibaseLiteralAdapter.class, params = { "base58btc", "base64url" })
    @Mapper(MulticodecKeyMapper.class)
    @Override
    MulticodecKey privateKey();
}
