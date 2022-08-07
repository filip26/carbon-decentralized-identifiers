# Carbon DIDs
An implementation of the [Decentralized Identifiers (DIDs) v1.0](https://www.w3.org/TR/did-core/) in Java.

[![Java 17 CI](https://github.com/filip26/carbon-decentralized-identifiers/actions/workflows/java17-build.yml/badge.svg)](https://github.com/filip26/carbon-decentralized-identifiers/actions/workflows/java17-build.yml)
[![Android (Java 8) CI](https://github.com/filip26/carbon-decentralized-identifiers/actions/workflows/java8-build.yml/badge.svg)](https://github.com/filip26/carbon-decentralized-identifiers/actions/workflows/java8-build.yml)
[![Maven Central](https://img.shields.io/maven-central/v/com.apicatalog/carbon-did.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.apicatalog%22%20AND%20a:%22carbon-did%22)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)


## Features

* [did:key method v0.7](https://w3c-ccg.github.io/did-method-key/)

## Installation

### Maven

```xml
<!-- Java 17 -->
<dependency>
    <groupId>com.apicatalog</groupId>
    <artifactId>carbon-did</artifactId>
    <version>0.0.1</version>
</dependency>

```

or

```xml
<!-- Android (Java 8) -->
<dependency>
    <groupId>com.apicatalog</groupId>
    <artifactId>carbon-did-jre8</artifactId>
    <version>0.0.1</version>
</dependency>
```

## Documentation

[![javadoc](https://javadoc.io/badge2/com.apicatalog/carbon-decentralized-identifiers/javadoc.svg)](https://javadoc.io/doc/com.apicatalog/carbon-decentralized-identifiers)


## Contributing

All PR's welcome!


### Building

Fork and clone the project repository.

#### Java 11
```bash
> cd carbon-decentralized-identifiers
> mvn clean package
```

#### Java 8
```bash
> cd carbon-decentralized-identifiers
> mvn -f pom_jre8.xml clean package
```


## Resources

* [Decentralized Identifiers (DIDs) v1.0](https://www.w3.org/TR/did-core/)
* [The did:key Method v0.7](https://w3c-ccg.github.io/did-method-key/)

