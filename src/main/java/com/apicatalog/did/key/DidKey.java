package com.apicatalog.did.key;

import java.net.URI;

import com.apicatalog.did.Did;
import com.apicatalog.multibase.Multibase;
import com.apicatalog.multibase.MultibaseDecoder;

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

    protected final byte[] encodedKey;

    protected DidKey(String version, String encoded, Multibase base, byte[] debased) {
        super(METHOD_KEY, version, encoded);
        this.base = base;
        this.encodedKey = debased;
    }

    /**
     * Creates a new DID key instance from the given {@link URI}.
     *
     * @param uri   The source URI to be transformed into DID key
     * @param bases
     * @return The new DID key
     *
     * @throws NullPointerException     If {@code uri} is {@code null}
     *
     * @throws IllegalArgumentException If the given {@code uri} is not valid DID
     *                                  key
     */
    public static final DidKey from(final URI uri, final MultibaseDecoder bases) {

        final Did did = Did.from(uri);

        if (!METHOD_KEY.equalsIgnoreCase(did.getMethod())) {
            throw new IllegalArgumentException("The given URI [" + uri + "] is not valid DID key, does not start with 'did:key'.");
        }

        return from(did, bases);
    }

    public static final DidKey from(final Did did, final MultibaseDecoder bases) {

        if (!METHOD_KEY.equalsIgnoreCase(did.getMethod())) {
            throw new IllegalArgumentException("The given DID method [" + did.getMethod() + "] is not 'key'. DID [" + did.toString() + "].");
        }

        final Multibase base = bases.getBase(did.getMethodSpecificId()).orElseThrow(() -> new IllegalArgumentException("Unsupported did:key base encoding. DID [" + did.toString() + "]."));

        final byte[] debased = base.decode(did.getMethodSpecificId());

        return new DidKey(did.getVersion(), did.getMethodSpecificId(), base, debased);
    }

    public static final DidKey create(Multibase base, byte[] key) {
        return new DidKey(null, base.encode(key), base, key);
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

    public Multibase getBase() {
        return base;
    }

    public byte[] getKey() {
        return encodedKey;
    }
}
