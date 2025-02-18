
> [!IMPORTANT]
> Your feedback is essential to the improvement of this library. Please share any concerns, primary use cases, areas for enhancement, or challenges you have encountered. Your insights help refine and optimize the library to better meet user needs. Thank you for your time and contributions.

# Carbon DID
An implementation of the [Decentralized Identifiers (DIDs) v1.0](https://www.w3.org/TR/did-core/) in Java.


[![Java 11 CI](https://github.com/filip26/carbon-decentralized-identifiers/actions/workflows/java11-push.yml/badge.svg)](https://github.com/filip26/carbon-decentralized-identifiers/actions/workflows/java11-push.yml)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/dd79aafc6eb14ed18f2217de62585ba7)](https://app.codacy.com/gh/filip26/carbon-decentralized-identifiers/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)
[![Codacy Badge](https://app.codacy.com/project/badge/Coverage/dd79aafc6eb14ed18f2217de62585ba7)](https://app.codacy.com/gh/filip26/carbon-decentralized-identifiers/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_coverage)
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
    <version>0.6.0</version>
</dependency>
<dependency>
    <groupId>com.apicatalog</groupId>
    <artifactId>copper-multibase</artifactId>
    <version>0.5.0</version>
</dependency>
```

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

## Documentation

[![javadoc](https://javadoc.io/badge2/com.apicatalog/carbon-did/javadoc.svg)](https://javadoc.io/doc/com.apicatalog/carbon-did)


## Contributing

All PR's welcome!


### Building

Fork and clone the project repository.

#### Java 11+
```bash
> cd carbon-decentralized-identifiers
> mvn clean package
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

