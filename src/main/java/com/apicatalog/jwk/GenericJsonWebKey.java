package com.apicatalog.jwk;

import java.net.URI;
import java.time.Instant;

import jakarta.json.JsonValue;

public class GenericJsonWebKey implements JsonWebKey {

    protected final URI id;
    protected final URI controller;

    protected final JsonValue publicKey;
    protected final JsonValue privateKey;

    protected Instant revoked;
    protected Instant expires;

    protected GenericJsonWebKey(
            URI id,
            URI controller,
            JsonValue publicKey,
            JsonValue privateKey) {
        this.id = id;
        this.controller = controller;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public static GenericJsonWebKey of(URI id, URI controller, JsonValue publicKey) {
        return of(id, controller, publicKey, null);
    }

    public static GenericJsonWebKey of(URI id, URI controller, JsonValue publicKey, JsonValue privateKey) {
        return new GenericJsonWebKey(id, controller, publicKey, privateKey);
    }

    @Override
    public URI id() {
        return id;
    }

    @Override
    public URI controller() {
        return controller;
    }

    @Override
    public Instant revoked() {
        return revoked;
    }

    @Override
    public Instant expires() {
        return expires;
    }

    @Override
    public JsonValue publicKey() {
        return publicKey;
    }

    @Override
    public JsonValue privateKey() {
        return privateKey;
    }

    public GenericJsonWebKey expires(Instant expires) {
        this.expires = expires;
        return this;
    }

    public GenericJsonWebKey revoked(Instant revoked) {
        this.revoked = revoked;
        return this;
    }
}
