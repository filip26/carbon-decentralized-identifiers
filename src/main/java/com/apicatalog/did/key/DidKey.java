package com.apicatalog.did.key;

import java.net.URI;

import com.apicatalog.did.Did;
import com.apicatalog.multibase.Multibase;
import com.apicatalog.multibase.MultibaseDecoder;
import com.apicatalog.multicodec.Multicodec;
import com.apicatalog.multicodec.Multicodec.Tag;
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

    private static final long serialVersionUID = 582726516478574544L;

    public static final String METHOD_KEY = "key";

    protected static final MulticodecDecoder MULTICODEC = MulticodecDecoder.getInstance(Tag.Key);
    protected static final MultibaseDecoder MULTIBASE = MultibaseDecoder.getInstance();

    private final Multicodec codec;

    private final byte[] rawKey;

    protected DidKey(Did did, Multicodec codec, byte[] rawValue) {
        super(did.getMethod(), did.getVersion(), did.getMethodSpecificId());
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
    public static final DidKey from(final URI uri) {

        final Did did = Did.from(uri);

        if (!METHOD_KEY.equalsIgnoreCase(did.getMethod())) {
            throw new IllegalArgumentException("The given URI [" + uri + "] is not valid DID key, does not start with 'did:key'.");
        }

        return from(did);
    }

    public static final DidKey from(final Did did) {

        if (!METHOD_KEY.equalsIgnoreCase(did.getMethod())) {
            throw new IllegalArgumentException("The given DID method [" + did.getMethod() + "] is not 'key'. DID [" + did.toString() + "].");
        }

        final Multibase base = MULTIBASE.getBase(did.getMethodSpecificId()).orElseThrow(() -> new IllegalArgumentException("Cannot detect did:key base encoding."));
        
        final byte[] decoded = base.decode(did.getMethodSpecificId());

        final Multicodec codec = MULTICODEC.getCodec(decoded).orElseThrow(() -> new IllegalArgumentException("Cannot detect did:key codec."));

        final byte[] rawKey = codec.decode(decoded);

        return new DidKey(did, codec, rawKey);
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

    public byte[] getRawKey() {
        return rawKey;
    }
}
