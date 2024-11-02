package com.apicatalog.controller.method;

import java.net.URI;
import java.time.Instant;

import com.apicatalog.linkedtree.orm.Fragment;
import com.apicatalog.linkedtree.orm.Id;
import com.apicatalog.linkedtree.orm.Literal;
import com.apicatalog.linkedtree.orm.Type;
import com.apicatalog.linkedtree.xsd.XsdDateTimeAdapter;

/**
 * Represents a signature method declaration.
 */
@Fragment(generic = true)
public interface SignatureMethod {

    @Id
    URI id();

    @Type
    String type();

    URI controller();

    @Literal(XsdDateTimeAdapter.class)
    Instant revoked();
}