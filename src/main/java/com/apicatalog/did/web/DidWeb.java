package com.apicatalog.did.web;

import java.net.URI;
import java.net.URISyntaxException;

import com.apicatalog.did.Did;

/**
 * Immutable DID Web
 * <p>
 * web-did = "did:web:" domain-name * (":" path)
 * </p>
 *
 * @see <a href= "https://w3c-ccg.github.io/did-method-web/">DID Web Method</a>
 *
 */
public class DidWeb extends Did {

    private static final long serialVersionUID = 3656757550197354836L;

    public static final String METHOD_NAME = "web";

    protected final URI url;
    protected final String domain;
    protected final String path;
    protected final int port;

    protected DidWeb(String specificId, URI url, String domain, String path, int port) {
        super(METHOD_NAME, specificId);
        this.url = url;
        this.domain = domain;
        this.path = path;
        this.port = port;
    }

    /**
     * Creates a new DID Web instance from the given {@link URI}.
     *
     * @param uri The source URI to be transformed into {@link DidWeb} instance
     *
     * @return a new instance
     *
     * @throws NullPointerException     If {@code uri} is {@code null}
     *
     * @throws IllegalArgumentException If the given {@code uri} is not valid DID
     *                                  Web method
     * 
     */
    public static final DidWeb of(final URI uri) {

        final Did did = Did.from(uri);

        if (!METHOD_NAME.equalsIgnoreCase(did.getMethod())) {
            throw new IllegalArgumentException("The given URI [" + uri + "] is not valid DID key, does not start with 'did:key'.");
        }

        return of(did);
    }

    public static final DidWeb of(final Did did) {

        if (!METHOD_NAME.equalsIgnoreCase(did.getMethod())) {
            throw new IllegalArgumentException("The given DID method [" + did.getMethod() + "] is not '" + METHOD_NAME + "'. DID [" + did.toString() + "].");
        }

        final String[] parts = did.getMethodSpecificId().split(":");

        String domain = parts[0];
        String path = "/.well-known/did.json";

        if (parts.length > 1) {
            StringBuilder builder = new StringBuilder(did.getMethodSpecificId().length() - domain.length());
            for (int i = 1; i < parts.length; i++) {
                builder.append('/').append(parts[i]);
            }
            path = builder.append("/did.json").toString();
        }

        int port = -1;

        URI url;

        try {
            url = new URI("https", null, domain, port, path, null, null);
            
            return new DidWeb(did.getMethodSpecificId(), url, domain, path, port);

        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static boolean isDidWeb(final Did did) {
        return METHOD_NAME.equalsIgnoreCase(did.getMethod());
    }

    public static boolean isDidWeb(final URI uri) {
        return Did.isDid(uri)
                && uri.getSchemeSpecificPart().toLowerCase().startsWith(METHOD_NAME + ":");
    }

    public static boolean isDidWeb(final String uri) {
        return Did.isDid(uri)
                && uri.toLowerCase().startsWith(SCHEME + ":" + METHOD_NAME + ":");
    }

    public String getDomain() {
        return domain;
    }

    public String getPath() {
        return path;
    }
    
    public int getPort() {
        return port;
    }

    public URI getUrl() {
        return url;
    }
}
