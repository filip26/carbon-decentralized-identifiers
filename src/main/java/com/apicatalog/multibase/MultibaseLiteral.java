package com.apicatalog.multibase;

import java.util.Objects;

import com.apicatalog.linkedtree.LinkedLiteral;
import com.apicatalog.linkedtree.literal.ByteArrayValue;

public class MultibaseLiteral implements LinkedLiteral, ByteArrayValue {

    static final String TYPE = "https://w3id.org/security#multibase";

    protected final String lexicalValue;
    protected final Multibase base;
    protected final byte[] byteArrayValue;

    protected MultibaseLiteral(
            String lexicalValue,
            Multibase base,
            byte[] byteArrayValue) {
        this.lexicalValue = lexicalValue;
        this.base = base;
        this.byteArrayValue = byteArrayValue;
    }

    public static MultibaseLiteral of(MultibaseDecoder bases, String value) {
        return of(
                bases.getBase(value)
                        .orElseThrow(IllegalArgumentException::new),
                value);
    }

    public static MultibaseLiteral of(Multibase base, String value) {
        return new MultibaseLiteral(value, base, base.decode(value));
    }

    public static MultibaseLiteral of(Multibase base, byte[] byteArrayValue) {
        return new MultibaseLiteral(base.encode(byteArrayValue), base, byteArrayValue);
    }

    @Override
    public String datatype() {
        return TYPE;
    }

    public static String typeName() {
        return TYPE;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lexicalValue);
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
        return Objects.equals(lexicalValue, other.lexicalValue);
    }

    @Override
    public String toString() {
        return "MultibaseLiteral [lexicalValue=" + lexicalValue + "]";
    }

    @Override
    public byte[] byteArrayValue() {
        return byteArrayValue;
    }

    @Override
    public String lexicalValue() {
        return lexicalValue;
    }

    public Multibase base() {
        return base;
    }
}
