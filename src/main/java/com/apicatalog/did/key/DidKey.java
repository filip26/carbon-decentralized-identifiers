package com.apicatalog.did.key;

import java.net.URI;

import com.apicatalog.did.Did;
import com.apicatalog.multibase.Multibase;
import com.apicatalog.multibase.MultibaseDecoder;
import com.apicatalog.multicodec.Multicodec;
import com.apicatalog.multicodec.MulticodecDecoder;

/**
 * Immutable DID Key
 * <p>
 * did-key-format := did:key:MULTIBASE(base58-btc, MULTICODEC(public-key-type,
 * raw-public-key-bytes))
 * </p>
 *
 * @see <a href=
 *      "https://pr-preview.s3.amazonaws.com/w3c-ccg/did-method-key/pull/51.html">DID
 *      method key</a>
 *
 */
public class DidKey extends Did {

    private static final long serialVersionUID = 3710900614215756688L;

    public static final String METHOD_KEY = "key";

    protected final Multibase base;
    protected final Multicodec codec;

    protected final byte[] rawKey;

    protected DidKey(String version, String encoded, Multibase base, Multicodec codec, byte[] rawValue) {
        super(METHOD_KEY, version, encoded);
        this.base = base;
        this.codec = codec;
        this.rawKey = rawValue;
    }

    /**
     * Creates a new DID key instance from the given {@link URI}.
     *
     * @param uri The source URI to be transformed into DID key
     * @return The new DID key
     *
     * @throws NullPointerException     If {@code uri} is {@code null}
     *
     * @throws IllegalArgumentException If the given {@code uri} is not valid DID
     *                                  key
     */
    public static final DidKey from(final URI uri, final MultibaseDecoder bases, final MulticodecDecoder codecs) {

        final Did did = Did.from(uri);

        if (!METHOD_KEY.equalsIgnoreCase(did.getMethod())) {
            throw new IllegalArgumentException("The given URI [" + uri + "] is not valid DID key, does not start with 'did:key'.");
        }

        return from(did, bases, codecs);
    }

    public static final DidKey from(final Did did, final MultibaseDecoder bases, final MulticodecDecoder codecs) {

        if (!METHOD_KEY.equalsIgnoreCase(did.getMethod())) {
            throw new IllegalArgumentException("The given DID method [" + did.getMethod() + "] is not 'key'. DID [" + did.toString() + "].");
        }

        final Multibase base = bases.getBase(did.getMethodSpecificId()).orElseThrow(() -> new IllegalArgumentException("Unsupported did:key base encoding. DID [" + did.toString() + "]."));

        final byte[] decoded = base.decode(did.getMethodSpecificId());

        final Multicodec codec = codecs.getCodec(decoded).orElseThrow(() -> new IllegalArgumentException("Unsupported did:key codec. DID [" + did.toString() + "]."));

        final byte[] rawKey = codec.decode(decoded);

        return new DidKey(did.getVersion(), did.getMethodSpecificId(), base, codec, rawKey);
    }

    public static final DidKey create(Multibase base, Multicodec codec, byte[] rawKey) {
        return new DidKey(null, base.encode(codec.encode(rawKey)), base, codec, rawKey);
    }

    public static boolean isDidKey(final Did did) {
        return METHOD_KEY.equalsIgnoreCase(did.getMethod());
    }

    public static boolean isDidKey(final URI uri) {
        return Did.isDid(uri)
                && uri.getSchemeSpecificPart().toLowerCase().startsWith(METHOD_KEY + ":");
    }

    public static boolean isDidKey(final String uri) {
        return Did.isDid(uri)
                && uri.toLowerCase().startsWith(SCHEME + ":" + METHOD_KEY + ":");
    }

    public Multicodec getCodec() {
        return codec;
    }

    public Multibase getBase() {
        return base;
    }

    public byte[] getRawKey() {
        return rawKey;
    }
}
