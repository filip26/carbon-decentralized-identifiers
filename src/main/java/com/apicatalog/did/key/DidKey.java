package com.apicatalog.did.key;

import java.net.URI;

import com.apicatalog.did.Did;
import com.apicatalog.multibase.Multibase;
import com.apicatalog.multibase.MultibaseDecoder;

/**
 * Immutable DID Key
 * <p>
 * did-key-format := did:key:[version]:MULTIBASE(multiencodedKey)
 * </p>
 *
 * @see <a href= "https://w3c-ccg.github.io/did-method-key/">DID Key Method</a>
 *
 */
public class DidKey extends Did {

    private static final long serialVersionUID = 1343361455801198884L;

    public static final String METHOD_NAME = "key";

    public static final String DEFAULT_VERSION = "1";

    protected final String version;

    protected final Multibase base;

    protected final byte[] debased;

    protected DidKey(String version, String specificId, Multibase base, byte[] debased) {
        super(METHOD_NAME, specificId);
        this.base = base;
        this.version = version;
        this.debased = debased;
    }

    /**
     * @deprecated use {@link DidKey#of(URI, MultibaseDecoder)}
     * @param uri
     * @param bases
     * @return
     */
    @Deprecated
    public static final DidKey from(final URI uri, final MultibaseDecoder bases) {
        return of(uri, bases);
    }
    
    /**
     * Creates a new DID Key method instance from the given {@link URI}.
     *
     * @param uri   The source URI to be transformed into {@link DidKey} instance
     * @param bases
     * @return a new instance
     *
     * @throws NullPointerException     If {@code uri} is {@code null}
     *
     * @throws IllegalArgumentException If the given {@code uri} is not valid DID
     *                                  Key method
     */
    public static final DidKey of(final URI uri, final MultibaseDecoder bases) {

        final Did did = Did.of(uri);

        if (!METHOD_NAME.equalsIgnoreCase(did.getMethod())) {
            throw new IllegalArgumentException("The given URI [" + uri + "] is not valid DID key, does not start with 'did:key'.");
        }

        return of(did, bases);
    }

    /**
     * @deprecated use {@link DidKey#of(Did, MultibaseDecoder)}
     * @param did
     * @param bases
     * @return
     */
    @Deprecated
    public static final DidKey from(final Did did, final MultibaseDecoder bases) {
        return of(did, bases);
    }
    
    
    public static final DidKey of(final Did did, final MultibaseDecoder bases) {

        if (!METHOD_NAME.equalsIgnoreCase(did.getMethod())) {
            throw new IllegalArgumentException("The given DID method [" + did.getMethod() + "] is not 'key'. DID [" + did.toString() + "].");
        }

        final String[] parts = did.getMethodSpecificId().split(":", 2);

        String version = DEFAULT_VERSION;
        String encoded = parts[0];

        if (parts.length == 2) {
            version = parts[0];
            encoded = parts[1];
        }

        final Multibase base = bases.getBase(encoded).orElseThrow(() -> new IllegalArgumentException("Unsupported did:key base encoding. DID [" + did.toString() + "]."));

        final byte[] debased = base.decode(encoded);

        return new DidKey(version, encoded, base, debased);
    }

    /**
     * @deprecated use {@link DidKey#of(byte[], Multibase)}
     * @param base
     * @param key
     * @return
     */
    @Deprecated
    public static final DidKey create(Multibase base, byte[] key) {
        return of(key, base);
    }
    
    public static final DidKey of(byte[] key, Multibase base) {
        return new DidKey(null, base.encode(key), base, key);
    }

    public static boolean isDidKey(final Did did) {
        return METHOD_NAME.equalsIgnoreCase(did.getMethod());
    }

    public static boolean isDidKey(final URI uri) {
        return Did.isDid(uri)
                && uri.getSchemeSpecificPart().toLowerCase().startsWith(METHOD_NAME + ":");
    }

    public static boolean isDidKey(final String uri) {
        return Did.isDid(uri)
                && uri.toLowerCase().startsWith(SCHEME + ":" + METHOD_NAME + ":");
    }

    public Multibase getBase() {
        return base;
    }

    public byte[] getKey() {
        return debased;
    }

    public String getVersion() {
        return version;
    }
}
