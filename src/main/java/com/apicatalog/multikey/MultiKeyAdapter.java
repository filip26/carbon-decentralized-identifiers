package com.apicatalog.multikey;

import com.apicatalog.ld.Term;
import com.apicatalog.linkedtree.LinkedLiteral;
import com.apicatalog.linkedtree.adapter.NodeAdapterError;
import com.apicatalog.linkedtree.literal.ByteArrayValue;
import com.apicatalog.linkedtree.literal.adapter.TypedLiteralAdapter;
import com.apicatalog.linkedtree.orm.adapter.NativeLiteralAdapter;
import com.apicatalog.multibase.Multibase;
import com.apicatalog.multibase.MultibaseLiteral;
import com.apicatalog.multicodec.GenericMulticodecKey;
import com.apicatalog.multicodec.MulticodecDecoder;
import com.apicatalog.multicodec.MulticodecKey;

public class MultiKeyAdapter implements NativeLiteralAdapter {

//    public static final String SECURITY_VOCAB = "https://w3id.org/security#";
//
//    public static final Term CONTROLLER = Term.create("controller", SECURITY_VOCAB);
//
//    public static final Term PUBLIC_KEY = Term.create("publicKeyMultibase", SECURITY_VOCAB);
//    public static final Term PRIVATE_KEY = Term.create("secretKeyMultibase", SECURITY_VOCAB);
//
//    public static final Term EXPIRATION = Term.create("expiration", SECURITY_VOCAB);
//    public static final Term REVOKED = Term.create("revoked", SECURITY_VOCAB);

    @Override
    public Object materialize(Class<?> type, LinkedLiteral literal) throws NodeAdapterError {
        
        if (literal instanceof ByteArrayValue byteArray) {
            
        }
        
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypedLiteralAdapter literalAdapter() {
        return MultibaseLiteral.typeAdapter(Multibase.BASE_58_BTC);
    }

    protected static final MulticodecKey getKey(Term term, final byte[] encodedKey, MulticodecDecoder decoder) throws NodeAdapterError {

        if (encodedKey == null || encodedKey.length == 0) {
            return null;
        }

        return decoder.getCodec(encodedKey)
                .map(codec -> new GenericMulticodecKey(codec, codec.decode(encodedKey)))
                .orElseThrow(() -> new NodeAdapterError("Unsupported " + term.name() + " codec"));
    }

//
//    protected final MulticodecDecoder decoder;
//
//    public MultiKeyAdapter(MulticodecDecoder decoder) {
//        this.decoder = decoder;
//    }
//
//    protected abstract Multicodec getPublicKeyCodec(String algo, int keyLength);
//
//    protected abstract Multicodec getPrivateKeyCodec(String algo, int keyLength);
//
//    /**
//     * Custom multikey validation.
//     * 
//     * @param method to validate
//     * @throws DocumentError if there is validation error
//     */
//    protected void validate(MultiKey method) throws DocumentError {
//        /* implement a custom validation */
//    }
//
//    public MultiKey of(
//            MulticodecDecoder decoder,
//            LinkedFragment source) throws NodeAdapterError {
//
//        final MultiKey multikey = new MultiKey();
//
//        multikey.id = source.uri();
//        multikey.controller = source.uri(MultiKeyAdapter.CONTROLLER.uri());
//
//        var x = source.literal(MultiKeyAdapter.PUBLIC_KEY.uri(), ByteArrayValue.class);
////        multikey.publicKey = getKey(MultiKeyAdapter.PUBLIC_KEY, x.byteArrayValue(), multikey, decoder);
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
//        return multikey;
//    }
//
//    public LinkedFragmentAdapter resolve(String id, Collection<String> types) {
//        if (types.contains(MultiKey.TYPE_NAME)) {
//            return new LinkedFragmentAdapter() {
//
////                @Override
////                public LinkedFragmentReader reader() {
//////                    return ((id, types, properties, ctx) -> MultiKey.of(id, types, properties, ctx, decoder));
////                }
//
////                @Override
//                public Collection<LiteralAdapter> literalAdapters() {
//                    return List.of(
//                            new GenericDatatypeAdapter(
//                                    MultibaseLiteral.typeName(),
//                                    ((source, root) -> new MultibaseLiteral(
//                                            MultibaseLiteral.typeName(),
//                                            source,
//                                            Multibase.BASE_58_BTC.decode(source),
//                                            root))));
//                }
//
//                @Override
//                public LinkedFragmentReader reader() {
//                    // TODO Auto-generated method stub
//                    return null;
//                }
//
//            };
//        }
//        return null;
//    }
//
////    public VerificationMethod read(JsonObject document) throws DocumentError {
////        Objects.requireNonNull(document);
////
////        final LdNode node = LdNode.of(document);
////
////        final MultiKey multikey = new MultiKey();
////
////        multikey.id = node.id();
////        multikey.controller = node.node(CONTROLLER).id();
////
////        if (node.type().hasType(MultiKey.TYPE.toString())) {
////
////            multikey.publicKey = getKey(node, PUBLIC_KEY, multikey);
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
////        return multikey;
////    }
//
////    protected final byte[] getKey(final LdNode node, final Term term, final MultiKey multikey) throws DocumentError {
////
////        final LdScalar key = node.scalar(term);
////
////        if (key.exists()) {
////
////            if (!"https://w3id.org/security#multibase".equals(key.type())) {
////                throw new DocumentError(ErrorType.Invalid, term.name() + "Type");
////            }
////
////            final String encodedKey = key.string();
////
////            if (!Multibase.BASE_58_BTC.isEncoded(encodedKey)) {
////                throw new DocumentError(ErrorType.Invalid, term.name() + "Type");
////            }
////
////            final byte[] decodedKey = Multibase.BASE_58_BTC.decode(encodedKey);
////
////            final Multicodec codec = decoder.getCodec(decodedKey).orElseThrow(() -> new DocumentError(ErrorType.Invalid, term.name() + "Codec"));
////
////            if (multikey.algorithm == null) {
////                multikey.algorithm = getAlgorithmName(codec);
////
////            } else if (!multikey.algorithm.equals(getAlgorithmName(codec))) {
////                throw new DocumentError(ErrorType.Invalid, "KeyPairCodec");
////            }
////
////            return codec.decode(decodedKey);
////        }
////
////        return null;
////    }
//
//    public LinkedNode write(VerificationMethod value) {
//
////        LdNodeBuilder builder = new LdNodeBuilder();
////
////        if (value.id() != null) {
////            builder.id(value.id());
////        }
////
////        boolean embedded = false;
////
////        if (value.controller() != null) {
////            builder.set(CONTROLLER).id(value.controller());
////            embedded = true;
////        }
////
////        if (value instanceof MultiKey) {
////            MultiKey multikey = (MultiKey) value;
////            if (multikey.expiration() != null) {
////                builder.set(EXPIRATION).xsdDateTime(multikey.expiration());
////                embedded = true;
////            }
////            if (multikey.revoked() != null) {
////                builder.set(REVOKED).xsdDateTime(multikey.revoked());
////                embedded = true;
////            }
////        }
////
////        if (value instanceof VerificationKey) {
////            VerificationKey verificationKey = (VerificationKey) value;
////
////            if (verificationKey.publicKey() != null) {
////                builder.set(PUBLIC_KEY)
////                        .scalar("https://w3id.org/security#multibase",
////                                Multibase.BASE_58_BTC.encode(
////                                        getPublicKeyCodec(verificationKey.algorithm(), verificationKey.publicKey().length)
////                                                .encode(verificationKey.publicKey())));
////                ;
////                embedded = true;
////            }
////        }
////
////        if (value instanceof KeyPair) {
////            KeyPair keyPair = (KeyPair) value;
////            if (keyPair.privateKey() != null) {
////                builder.set(PRIVATE_KEY)
////                        .scalar("https://w3id.org/security#multibase",
////                                Multibase.BASE_58_BTC.encode(
////                                        getPrivateKeyCodec(keyPair.algorithm(), keyPair.privateKey().length)
////                                                .encode(keyPair.privateKey())));
////                ;
////                embedded = true;
////            }
////        }
////
////        if (embedded) {
////            builder.type(value.type());
////        }
//
////        return builder.build();
//        // FIXME
//        return null;
//    }
//
//    public static final String getAlgorithmName(Multicodec codec) {
//        if (codec.name().endsWith("-priv")) {
//            return codec.name().substring(0, codec.name().length() - "-priv".length()).toUpperCase();
//        }
//        if (codec.name().endsWith("-pub")) {
//            return codec.name().substring(0, codec.name().length() - "-pub".length()).toUpperCase();
//        }
//        return codec.name().toUpperCase();
//    }
}