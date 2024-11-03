package com.apicatalog.controller.method;

import java.net.URI;
import java.time.Instant;
import java.util.Objects;

import com.apicatalog.linkedtree.orm.Compaction;
import com.apicatalog.linkedtree.orm.Context;
import com.apicatalog.linkedtree.orm.Fragment;
import com.apicatalog.linkedtree.orm.Id;
import com.apicatalog.linkedtree.orm.Literal;
import com.apicatalog.linkedtree.orm.Term;
import com.apicatalog.linkedtree.orm.Type;
import com.apicatalog.linkedtree.orm.Vocab;
import com.apicatalog.linkedtree.xsd.XsdDateTimeAdapter;

/**
 * Represents a verification method declaration.
 * 
 * https://www.w3.org/TR/controller-document/#verification-methods
 */
@Fragment(generic = true)
@Vocab("https://w3id.org/security#")
@Context("https://www.w3.org/ns/controller/v1")
public interface VerificationMethod {

    @Id
    URI id();

    @Type
    String type();

    @Compaction(order = 10)
    URI controller();

    @Literal(XsdDateTimeAdapter.class)
    @Compaction(order = 20)
    Instant revoked();

    @Literal(XsdDateTimeAdapter.class)
    @Term(value = "expiration", compact = false)
    @Compaction(order = 30)
    Instant expires();

    static boolean equals(VerificationMethod method1, VerificationMethod method2) {
        if (method1 == null || method2 == null) {
            return method1 == method2;
        }
        return Objects.equals(method1.id(), method2.id())
                && Objects.equals(method1.type(), method2.type())
                && Objects.equals(method1.controller(), method2.controller())
                && Objects.equals(method1.expires(), method2.expires())
                && Objects.equals(method1.revoked(), method2.revoked());
    }
}