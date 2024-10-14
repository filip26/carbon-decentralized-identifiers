package com.apicatalog.multicodec.key;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.apicatalog.linkedtree.orm.Literal;
import com.apicatalog.linkedtree.orm.Mapper;
import com.apicatalog.multibase.MultibaseLiteralAdapter;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Literal(value = MultibaseLiteralAdapter.class, params = { "base58btc", "base64url" })
@Mapper(MulticodecKeyMapper.class)
public @interface MultiformatKey {

}
