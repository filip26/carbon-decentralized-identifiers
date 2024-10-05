package com.apicatalog.multikey;

import java.net.URI;
import java.time.Instant;

import com.apicatalog.controller.method.KeyPair;
import com.apicatalog.linkedtree.orm.Id;
import com.apicatalog.linkedtree.orm.Literal;
import com.apicatalog.linkedtree.orm.Term;
import com.apicatalog.linkedtree.orm.Vocab;
import com.apicatalog.linkedtree.xsd.XsdDateTimeAdapter;
import com.apicatalog.multicodec.MulticodecKey;

@Vocab("https://w3id.org/security#")
public interface MultiKeyT extends KeyPair {

    @Id
    @Override
    URI id();

    @Override
    default String type() {
        return "https://w3id.org/security#Multikey";
    }

    @Override
    URI controller();

    @Literal(XsdDateTimeAdapter.class)
    @Override
    Instant revoked();

    @Term("publicKeyMultibase")
    @Literal(MultiKeyAdapter.class)
    @Override
    MulticodecKey publicKey();

    @Term("secretKeyMultibase")
    @Literal(MultiKeyAdapter.class)
    @Override
    MulticodecKey privateKey();

}
