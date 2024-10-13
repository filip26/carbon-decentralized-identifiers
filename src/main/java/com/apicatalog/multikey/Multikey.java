package com.apicatalog.multikey;

import com.apicatalog.controller.method.KeyPair;
import com.apicatalog.linkedtree.orm.Fragment;
import com.apicatalog.linkedtree.orm.Literal;
import com.apicatalog.linkedtree.orm.Map;
import com.apicatalog.linkedtree.orm.Term;
import com.apicatalog.linkedtree.orm.Vocab;
import com.apicatalog.multibase.MultibaseLiteral;
import com.apicatalog.multicodec.key.MultiformatKey;
import com.apicatalog.multicodec.key.MulticodecKey;
import com.apicatalog.multicodec.key.MulticodecKeyMapper;

@Fragment
@Vocab("https://w3id.org/security#")
public interface Multikey extends KeyPair {

    static final String TYPE = "https://w3id.org/security#Multikey";

    @Override
    default String type() {
        return TYPE;
    }

    @Term("publicKeyMultibase")
    @MultiformatKey
    @Override
    MulticodecKey publicKey();

    @Term("secretKeyMultibase")
    @MultiformatKey
    @Override
    MulticodecKey privateKey();
}
