package com.apicatalog.multibase;

import java.util.Objects;

import com.apicatalog.linkedtree.LinkedLiteral;
import com.apicatalog.linkedtree.LinkedTree;
import com.apicatalog.linkedtree.adapter.NodeAdapterError;
import com.apicatalog.linkedtree.literal.ByteArrayValue;
import com.apicatalog.linkedtree.literal.adapter.GenericLiteralAdapter;
import com.apicatalog.linkedtree.literal.adapter.DataTypeAdapter;

public record MultibaseLiteral(
        String datatype,
        String lexicalValue,
        byte[] byteArrayValue,
        LinkedTree root) implements LinkedLiteral, ByteArrayValue {

    static final String TYPE = "https://w3id.org/security#multibase";

    public static MultibaseLiteral of(Multibase base, String value, LinkedTree root) {
        return new MultibaseLiteral(TYPE, value, base.decode(value), root);
    }

    public static String typeName() {
        return TYPE;
    }

//    public static DataTypeAdapter typeAdapter(final Multibase base) {
//        return new GenericLiteralAdapter(
//                TYPE,
//                MultibaseLiteral.class,
//                (value, root) -> MultibaseLiteral.of(base, value, root));
//    }

    @Override
    public int hashCode() {
        return Objects.hash(datatype, lexicalValue);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MultibaseLiteral other = (MultibaseLiteral) obj;
        return Objects.equals(datatype, other.datatype) && Objects.equals(lexicalValue, other.lexicalValue);
    }

    @Override
    public String toString() {
        return "MultibaseLiteral [datatype=" + datatype + ", lexicalValue=" + lexicalValue + "]";
    }
}
