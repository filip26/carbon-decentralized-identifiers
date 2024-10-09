package com.apicatalog.did.document;

import java.net.URI;

import com.apicatalog.linkedtree.orm.Fragment;
import com.apicatalog.linkedtree.orm.Id;
import com.apicatalog.linkedtree.type.Type;

@Fragment
public interface ServiceEndpoint {

    @Id
    URI id();

    Type type();
}
