package com.apicatalog.did.key;

import java.net.URI;

import com.apicatalog.did.Did;
import com.apicatalog.multibase.Multibase;
import com.apicatalog.multicodec.Multicodec;
import com.apicatalog.multicodec.MulticodecDecoder;
import com.apicatalog.multicodec.key.MulticodecKey;

/**
 * Immutable DID Key
 * <p>
 * did-key-format := did:key:[version]:MULTIBASE(multiencodedKey)
 * </p>
 *
 * @see <a href= "https://w3c-ccg.github.io/did-method-key/">DID Key Method</a>
 *
 */
public class DidKey extends Did implements MulticodecKey {

    private static final long serialVersionUID = 1343361455801198884L;

    public static final String METHOD_NAME = "key";

    public static final String DEFAULT_VERSION = "1";

    protected final String version;

    protected final Multicodec codec;
    
    protected final byte[] raw;

    protected DidKey(String version, String specificId, Multicodec codec, byte[] raw) {
        super(METHOD_NAME, specificId);
        this.version = version;
        this.codec = codec;
        this.raw = raw;
    }

    /**
     * Creates a new DID Key method instance from the given {@link URI}.
     *
     * @param uri   The source URI to be transformed into {@link DidKey} instance
     * @param codecs
     * @return a new instance
     *
     * @throws NullPointerException     If {@code uri} is {@code null}
     *
     * @throws IllegalArgumentException If the given {@code uri} is not valid DID
     *                                  Key method
     */
    public static final DidKey of(final URI uri, final MulticodecDecoder codecs) {

        final Did did = Did.of(uri);

        if (!METHOD_NAME.equalsIgnoreCase(did.getMethod())) {
            throw new IllegalArgumentException("The given URI [" + uri + "] is not valid DID key, does not start with 'did:key'.");
        }

        return of(did, codecs);
    }

    public static final DidKey of(final Did did, final MulticodecDecoder codecs) {

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
        
        if (!Multibase.BASE_58_BTC.isEncoded(encoded)) {
            throw new IllegalArgumentException("Unsupported did:key base encoding. DID [" + did.toString() + "].");
        }

        final byte[] debased = Multibase.BASE_58_BTC.decode(encoded);

        Multicodec codec = codecs.getCodec(debased).orElseThrow(() -> new IllegalArgumentException("Unsupported did:key codec. DID [" + did.toString() + "]."));

        final byte[] raw = codec.decode(debased);
        
        return new DidKey(version, encoded, codec, raw);
    }
    
    public static final DidKey of(byte[] key, Multicodec codec) {
        return new DidKey(null, Multibase.BASE_58_BTC.encode(key), codec, key);
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

    public String version() {
        return version;
    }

    @Override
    public Multicodec codec() {
        return codec;
    }

    @Override
    public byte[] raw() {
        return raw;
    }
}
