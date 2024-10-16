package com.apicatalog.controller.method;

import java.net.URI;

import com.apicatalog.linkedtree.orm.Fragment;
import com.apicatalog.linkedtree.orm.Id;

/**
 * Represents a signature method declaration.
 */
@Fragment(generic = true)
public interface SignatureMethod {

    @Id
    URI id();

}