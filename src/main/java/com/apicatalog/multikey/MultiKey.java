package com.apicatalog.multikey;

import java.net.URI;
import java.time.Instant;

import com.apicatalog.controller.key.MultihashKey;
import com.apicatalog.controller.method.KeyPair;
import com.apicatalog.multicodec.Multicodec;

public class MultiKey implements KeyPair {

    protected static final String TYPE_NAME = "https://w3id.org/security#Multikey";

    protected final URI id;
    protected final URI controller;

    protected final MultihashKey publicKey;
    protected final MultihashKey privateKey;

    protected Instant revoked;

    protected MultiKey(
            URI id,
            URI controller,
            MultihashKey publicKey,
            MultihashKey privateKey) {
        this.id = id;
        this.controller = controller;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public static MultiKey of(URI id, URI controller, MultihashKey publicKey) {
        return of(id, controller, publicKey, null);
    }

    public static MultiKey of(URI id, URI controller, MultihashKey publicKey, MultihashKey privateKey) {
        return new MultiKey(id, controller, publicKey, privateKey);
    }

//    protected LinkedFragment ld;

//    public static MultiKey of(
//            MulticodecDecoder decoder
////            LinkedFragment source
//            ) throws NodeAdapterError {
//
//        final MultiKey multikey = new MultiKey();
//
//        multikey.id = source.uri();
//        multikey.controller = source.uri(MultiKeyAdapter.CONTROLLER.uri());
//
//        var x = source.literal(MultiKeyAdapter.PUBLIC_KEY.uri(), ByteArrayValue.class);
//        multikey.publicKey = getKey(MultiKeyAdapter.PUBLIC_KEY, x.byteArrayValue(), multikey, decoder);
//
////            multikey.privateKey = getKey(node, PRIVATE_KEY, multikey);
////
////            multikey.expiration = node.scalar(EXPIRATION).xsdDateTime();
////            multikey.revoked = node.scalar(REVOKED).xsdDateTime();
////
////        } else if (node.type().exists()) {
////            throw new DocumentError(ErrorType.Invalid, "VerificationMethodType");
////        }
////
////        validate(multikey);
//
////        return new LinkableObject(id, types, properties, multikey);
//        
//        multikey.ld = source;
//        
//        return multikey;
//    }

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
    public MultihashKey publicKey() {
        return publicKey;
    }

    @Override
    public MultihashKey privateKey() {
        return privateKey;
    }

    public void revoked(Instant revoked) {
        this.revoked = revoked;
    }

    @Override
    public Instant revoked() {
        return revoked;
    }

//    protected static final byte[] getKey(Term term, final byte[] decodedKey, MultiKey multikey, MulticodecDecoder decoder) throws NodeAdapterError {
//
//        if (decodedKey == null || decodedKey.length == 0) {
//            return null;
//        }
//
//        final Multicodec codec = decoder.getCodec(decodedKey)
//                .orElseThrow(() -> new NodeAdapterError("Invalid " + term.name() + " codec"));
//
//        if (multikey.algorithm == null) {
//            multikey.algorithm = getAlgorithmName(codec);
//
//        } else if (!multikey.algorithm.equals(getAlgorithmName(codec))) {
//            throw new IllegalArgumentException("Invalid key pair codec [" + codec + "]");
//        }
//
//        return codec.decode(decodedKey);
//    }

    public static final String getAlgorithmName(Multicodec codec) {
        if (codec.name().endsWith("-priv")) {
            return codec.name().substring(0, codec.name().length() - "-priv".length()).toUpperCase();
        }
        if (codec.name().endsWith("-pub")) {
            return codec.name().substring(0, codec.name().length() - "-pub".length()).toUpperCase();
        }
        return codec.name().toUpperCase();
    }

    public static String typeName() {
        return TYPE_NAME;
    }
//
//    @Override
//    public LinkedNode ld() {
//        return ld;
//    }

}
