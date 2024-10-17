package com.apicatalog.multicodec.key;

import com.apicatalog.linkedtree.LinkedLiteral;
import com.apicatalog.multicodec.Multicodec;

public record MulticodecKeyLiteral(
        String lexicalValue,
        String datatype,
        Multicodec codec,
        byte[] rawBytes) implements LinkedLiteral, MulticodecKey {

}
