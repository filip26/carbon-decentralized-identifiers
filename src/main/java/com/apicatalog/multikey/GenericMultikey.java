package com.apicatalog.multikey;

import java.net.URI;
import java.time.Instant;

import com.apicatalog.ld.Term;
import com.apicatalog.linkedtree.adapter.NodeAdapterError;
import com.apicatalog.multicodec.MulticodecDecoder;
import com.apicatalog.multicodec.key.GenericMulticodecKey;
import com.apicatalog.multicodec.key.MulticodecKey;

public class GenericMultikey implements Multikey {

    protected static final String TYPE_NAME = "https://w3id.org/security#Multikey";

    protected final URI id;
    protected final URI controller;

    protected final MulticodecKey publicKey;
    protected final MulticodecKey privateKey;

    protected Instant revoked;
    protected Instant expires;

    protected GenericMultikey(
            URI id,
            URI controller,
            MulticodecKey publicKey,
            MulticodecKey privateKey) {
        this.id = id;
        this.controller = controller;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public static GenericMultikey of(URI id, URI controller, MulticodecKey publicKey) {
        return of(id, controller, publicKey, null);
    }

    public static GenericMultikey of(URI id, URI controller, MulticodecKey publicKey, MulticodecKey privateKey) {
        return new GenericMultikey(id, controller, publicKey, privateKey);
    }

    @Override
    public URI id() {
        return id;
    }

    @Override
    public String type() {
        return TYPE_NAME;
    }

    @Override
    public URI controller() {
        return controller;
    }

    @Override
    public MulticodecKey publicKey() {
        return publicKey;
    }

    @Override
    public MulticodecKey privateKey() {
        return privateKey;
    }

    public void revoked(Instant revoked) {
        this.revoked = revoked;
    }

    @Override
    public Instant revoked() {
        return revoked;
    }

    protected static final MulticodecKey getKey(Term term, final byte[] encodedKey, MulticodecDecoder decoder) throws NodeAdapterError {

        if (encodedKey == null || encodedKey.length == 0) {
            return null;
        }

        return decoder.getCodec(encodedKey)
                .map(codec -> new GenericMulticodecKey(codec, codec.decode(encodedKey)))
                .orElseThrow(() -> new NodeAdapterError("Unsupported " + term.name() + " codec"));
    }

    public static String typeName() {
        return TYPE_NAME;
    }

    @Override
    public Instant expires() {
        return expires;
    }

    public void expires(Instant expires) {
        this.expires = expires;
    }
}
