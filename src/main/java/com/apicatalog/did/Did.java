package com.apicatalog.did;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.function.IntPredicate;

public class Did implements Serializable {

    private static final long serialVersionUID = -3127318269811273712L;

    /*
     * method-char = %x61-7A / DIGIT
     */
    static final IntPredicate METHOD_CHAR = ch -> (0x61 <= ch && ch <= 0x7A) || ('0' <= ch && ch <= '9');

    public static final String SCHEME = "did";
    
    protected final String method;
    protected final String specificId;

    protected Did(final String method, final String specificId) {
        this.method = method;
        this.specificId = specificId;
    }

    public static boolean isDid(final URI uri) {
        if (!Did.SCHEME.equalsIgnoreCase(uri.getScheme())
                || isBlank(uri.getSchemeSpecificPart())
                || isNotBlank(uri.getAuthority())
                || isNotBlank(uri.getUserInfo())
                || isNotBlank(uri.getHost())
                || isNotBlank(uri.getPath())
                || isNotBlank(uri.getQuery())
                || isNotBlank(uri.getFragment())) {
            return false;
        }

        final String[] parts = uri.getSchemeSpecificPart().split(":", 2);

        return parts.length == 2
                && parts[0].length() > 0
                && parts[1].length() > 0
                && parts[0].codePoints().allMatch(METHOD_CHAR)
                ;
    }

    public static boolean isDid(final String uri) {

        if (uri == null) {
            return false;
        }

        final String[] parts = uri.split(":", 3);

        return parts.length == 3
                && Did.SCHEME.equalsIgnoreCase(parts[0])
                && parts[1].length() > 0
                && parts[2].length() > 0
                && parts[1].codePoints().allMatch(METHOD_CHAR)
                ;
    }

    /**
     * Creates a new DID instance from the given {@link URI}.
     *
     * @param uri The source URI to be transformed into DID
     * @return The new DID
     *
     * @throws NullPointerException     If {@code uri} is {@code null}
     *
     * @throws IllegalArgumentException If the given {@code uri} is not valid DID
     */
    public static Did from(final URI uri) {

        if (uri == null) {
            throw new IllegalArgumentException("The DID must not be null.");
        }

        if (!Did.SCHEME.equalsIgnoreCase(uri.getScheme())) {
            throw new IllegalArgumentException("The URI [" + uri + "] is not valid DID, must start with 'did:' prefix.");
        }

        return from(uri, uri.getSchemeSpecificPart().split(":", 2));
    }

    /**
     * Creates a new DID instance from the given URI.
     *
     * @param uri The source URI to be transformed into DID
     * @return The new DID
     *
     * @throws NullPointerException     If {@code uri} is {@code null}
     *
     * @throws IllegalArgumentException If the given {@code uri} is not valid DID
     */
    public static Did from(final String uri) {
        if (uri == null || uri.length() == 0) {
            throw new IllegalArgumentException("The DID must not be null or blank string.");
        }

        final String[] parts = uri.split(":", 3);

        if (parts.length != 3) {
            throw new IllegalArgumentException("The URI [" + uri + "] is not valid DID, must be in form 'did:method:method-specific-id'.");
        }

        if (!Did.SCHEME.equalsIgnoreCase(parts[0])) {
            throw new IllegalArgumentException("The URI [" + uri + "] is not valid DID, must start with 'did:' prefix.");
        }

        return from(uri, new String[] { parts[1], parts[2] });
    }

    protected static Did from(final Object uri, final String[] parts) {
        if (parts.length != 2) {
            throw new IllegalArgumentException("The URI [" + uri + "] is not valid DID, must be in form 'did:method:method-specific-id'.");
        }

        // check method
        if (parts[0].length() == 0
                || !parts[0].codePoints().allMatch(METHOD_CHAR)) {
            throw new IllegalArgumentException("The URI [" + uri + "] is not valid DID, method [" + parts[0] + "] syntax is blank or invalid.");
        }
        
        // check method specific id
        if (parts[1].length() == 0) {
            throw new IllegalArgumentException("The URI [" + uri + "] is not valid DID, method specific id [" + parts[1] + "] is blank.");
        }

        return new Did(parts[0], parts[1]);
    }

    public String getMethod() {
        return method;
    }

    public String getMethodSpecificId() {
        return specificId;
    }

    public URI toUri() {
        try {
            return new URI(SCHEME, method + ":" + specificId, null);
        } catch (URISyntaxException e) {
            throw new IllegalStateException(e);
        }
    }

    public boolean isDidUrl() {
        return false;
    }

    public DidUrl asDidUrl() {
        throw new ClassCastException();
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(SCHEME)
                .append(':')
                .append(method)
                .append(':')
                .append(specificId).toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, specificId);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Did other = (Did) obj;
        return Objects.equals(method, other.method) && Objects.equals(specificId, other.specificId);

    }

    static final boolean isNotBlank(String value) {
        return value != null && !value.trim().isEmpty();
    }

    static final boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
