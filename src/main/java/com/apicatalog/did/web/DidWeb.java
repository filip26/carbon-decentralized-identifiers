package com.apicatalog.did.web;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

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
    protected final Collection<String> path;

    protected DidWeb(String specificId, URI url, String domain, Collection<String> path) {
        super(METHOD_NAME, specificId);
        this.url = url;
        this.domain = domain;
        this.path = path;
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
        Collection<String> path = null;

        if (parts.length > 1) {
            path = new ArrayList<>(parts.length - 1);
            for (int i = 1; i < parts.length; i++) {
                path.add(parts[i]);
            }
        }
        
        URI url = null; //FIXME

        return new DidWeb(did.getMethodSpecificId(), url, domain, path);
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

    public Collection<String> getPath() {
        return path;
    }
    
    public URI getUrl() {
        return url;
    }
}
