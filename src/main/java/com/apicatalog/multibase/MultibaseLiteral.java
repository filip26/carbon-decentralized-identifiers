package com.apicatalog.multibase;

import java.util.Objects;

import com.apicatalog.linkedtree.LinkedLiteral;
import com.apicatalog.linkedtree.literal.ByteArrayValue;

public record MultibaseLiteral(
        String datatype,
        String lexicalValue,
        Multibase base,
        byte[] byteArrayValue) implements LinkedLiteral, ByteArrayValue {

    static final String TYPE = "https://w3id.org/security#multibase";

    public static MultibaseLiteral of(MultibaseDecoder bases, String value) {
        
        Multibase base = bases.getBase(value).orElseThrow(IllegalArgumentException::new);
        
        return new MultibaseLiteral(TYPE, value, base, base.decode(value));
    }

    public static String typeName() {
        return TYPE;
    }

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
        return Objects.equals(datatype, other.datatype) && Objects.equals(byteArrayValue, other.byteArrayValue);
    }

    @Override
    public String toString() {
        return "MultibaseLiteral [datatype=" + datatype + ", lexicalValue=" + lexicalValue + "]";
    }
}
