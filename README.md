# Carbon DID
An implementation of the [Decentralized Identifiers (DIDs) v1.0](https://www.w3.org/TR/did-core/) in Java.


[![Java 11 CI](https://github.com/filip26/carbon-decentralized-identifiers/actions/workflows/java11-push.yml/badge.svg)](https://github.com/filip26/carbon-decentralized-identifiers/actions/workflows/java11-push.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=filip26_carbon-decentralized-identifiers&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=filip26_carbon-decentralized-identifiers)
[![Maven Central](https://img.shields.io/maven-central/v/com.apicatalog/carbon-did.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:com.apicatalog%20AND%20a:carbon-did)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)


## Features

* DID, DID URL, DID Document
* Methods
  * [did:key method v0.7](https://w3c-ccg.github.io/did-method-key/)

## Installation

### Carbon DID

#### Maven

```xml
<dependency>
    <groupId>com.apicatalog</groupId>
    <artifactId>carbon-did</artifactId>
    <version>0.3.0</version>
</dependency>

```

#### Gradle 

Android 12+ (API Level >=32)


```gradle
implementation("com.apicatalog:carbon-did:0.3.0")
```

Do you need to support an older Android version? [Contact me](mailto:filip26@gmail.com)


### JSON-P Provider

Add JSON-P provider, if it is not on the classpath already.

#### Maven

```xml
<dependency>
    <groupId>org.glassfish</groupId>
    <artifactId>jakarta.json</artifactId>
    <version>2.0.1</version>
</dependency>
```

#### Gradle

```gradle
implementation("org.glassfish:jakarta.json:2.0.1")
```


## Documentation

[![javadoc](https://javadoc.io/badge2/com.apicatalog/carbon-did/javadoc.svg)](https://javadoc.io/doc/com.apicatalog/carbon-did)


## Contributing

All PR's welcome!


### Building

Fork and clone the project repository.

#### Java 11+
```bash
> cd carbon-decentralized-identifiers
> mvn -f pom_jre.xml clean package
```

## Resources- 
- [Decentralized Identifiers (DIDs) v1.0](https://www.w3.org/TR/did-core/)
- [The did:key Method v0.7](https://w3c-ccg.github.io/did-method-key/)
- [Copper Multicodec](https://github.com/filip26/copper-multicodec)
- [Copper Multibase](https://github.com/filip26/copper-multibase)

## Sponsors

<a href="https://github.com/digitalbazaar">
  <img src="https://avatars.githubusercontent.com/u/167436?s=200&v=4" width="40" />
</a> 

## Commercial Support
Commercial support is available at filip26@gmail.com

