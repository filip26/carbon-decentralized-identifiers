package com.apicatalog.did;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

public class DidUrl extends Did {

    private static final long serialVersionUID = 5752880077497569763L;

    protected final String path;
    protected final String query;
    protected final String fragment;

    protected DidUrl(Did did, String path, String query, String fragment) {
        super(did.method, did.specificId);
        this.path = path;
        this.query = query;
        this.fragment = fragment;
    }

    public static DidUrl from(Did did, String path, String query, String fragment) {
        return new DidUrl(did, path, query, fragment);
    }

    public static DidUrl from(final URI uri) {

        if (uri == null) {
            throw new IllegalArgumentException("The DID URL must not be null.");
        }

        if (!Did.SCHEME.equalsIgnoreCase(uri.getScheme())) {
            throw new IllegalArgumentException("The URI [" + uri + "] is not valid DID URL, must start with 'did:' prefix.");
        }

        final String[] didParts = uri.getSchemeSpecificPart().split(":", 2);

        if (didParts.length != 2) {
            throw new IllegalArgumentException("The URI [" + uri + "] is not valid DID, must be in form 'did:method:method-specific-id'.");
        }
        
        return from(uri, didParts[0], didParts[1], uri.getFragment());
    }
    
    protected static DidUrl from(Object uri, final String method, final String rest, final String fragment) {
        String specificId = rest;

        String path = null;
        String query = null;

        int urlPartIndex = specificId.indexOf('?');
        if (urlPartIndex != -1) {
            query = specificId.substring(urlPartIndex + 1);
            specificId = specificId.substring(0, urlPartIndex);
        }

        urlPartIndex = specificId.indexOf('/');
        if (urlPartIndex != -1) {
            path = specificId.substring(urlPartIndex);
            specificId = specificId.substring(0, urlPartIndex);
        }

        Did did = from(uri, method, specificId);

        return new DidUrl(did, path, query, fragment);
    }

    public static DidUrl from(final String uri) {

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

        String rest = parts[2];
        String fragment = null;
        
        int fragmentIndex = rest.indexOf('#');
        if (fragmentIndex != -1) {
            fragment = rest.substring(fragmentIndex + 1);
            rest = rest.substring(0, fragmentIndex);
        }

        return from(uri, parts[1], rest, fragment);
    }

    public static boolean isDidUrl(final URI uri) {
        return Did.SCHEME.equals(uri.getScheme());
    }

    public static boolean isDidUrl(final String uri) {
        if (Did.isBlank(uri)) {
            return false;
        }

        final String[] parts = uri.split(":");

        return (parts.length == 3 || parts.length == 4)
                && Did.SCHEME.equalsIgnoreCase(parts[0]);
    }

    @Override
    public URI toUri() {
        try {
            return new URI(SCHEME, method + ":" + specificId, path, query, fragment);
        } catch (URISyntaxException e) {
            throw new IllegalStateException(e);
        }
    }

    public URL toUrl() {
        try {
            return toUri().toURL();
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean isDidUrl() {
        return true;
    }

    @Override
    public DidUrl asDidUrl() {
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(super.toString());

        if (Did.isNotBlank(path)) {
            if (path.charAt(0) != '/') {
                builder.append('/');
            }
            builder.append(path);
        }

        if (Did.isNotBlank(query)) {
            if (query.charAt(0) != '?') {
                builder.append('?');
            }
            builder.append(query);
        }

        if (Did.isNotBlank(fragment)) {
            if (fragment.charAt(0) != '#') {
                builder.append('#');
            }
            builder.append(fragment);
        }

        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(fragment, path, query);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        DidUrl other = (DidUrl) obj;
        return Objects.equals(fragment, other.fragment) && Objects.equals(path, other.path)
                && Objects.equals(query, other.query);
    }

    public String getFragment() {
        return fragment;
    }

    public String getPath() {
        return path;
    }

    public String getQuery() {
        return query;
    }
}
