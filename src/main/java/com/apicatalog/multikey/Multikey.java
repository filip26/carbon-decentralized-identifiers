package com.apicatalog.multikey;

import com.apicatalog.controller.method.KeyPair;
import com.apicatalog.linkedtree.orm.Fragment;
import com.apicatalog.linkedtree.orm.Literal;
import com.apicatalog.linkedtree.orm.Term;
import com.apicatalog.linkedtree.orm.Vocab;
import com.apicatalog.multicodec.key.MulticodecKey;
import com.apicatalog.multicodec.key.MulticodecKeyMapper;

@Fragment
@Vocab("https://w3id.org/security#")
public interface Multikey extends KeyPair {

    @Override
    default String type() {
        return "https://w3id.org/security#Multikey";
    }
    
    @Term("publicKeyMultibase")
    @Literal(MulticodecKeyMapper.class)
    @Override
    MulticodecKey publicKey();

    @Term("secretKeyMultibase")
    @Literal(MulticodecKeyMapper.class)
    @Override
    MulticodecKey privateKey();

}
