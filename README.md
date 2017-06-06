# Using Application Caches in Oracle Application Container Cloud Service

![](docs/images/accs-cache_01.png)

Oracle Application Container Cloud Service features Application Cache with in-memory which is clusterd, scalable and resilient.
This sample show how to use it with Java API.

## Description

Application Cache in Oracle Application Container cloud Serivice is easy to start. All you have to do is that you specify the data capacity.
The cache service has the following rule:

- One cache service can have multiple cache within it
- Multipe applications can use the same one cache service and multiple caches within it.

You can use the cache with Java API or REST API.

## Demo

## Features

- Data in cahce service is replicated among cluster members
- Cache service is scalable

## Requirement

Application Cache Client needs the following dendency in Maven:

- Artifact ID: **cache-client-api**
  - Group ID: **com.oracle.cloud.caching**

```xml
<dependency>
    <groupId>com.oracle.cloud.caching</groupId>
    <artifactId>cache-client-api</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Usage

## Installation

## Licence

Released under the [MIT license](https://gist.githubusercontent.com/shinyay/56e54ee4c0e22db8211e05e70a63247e/raw/44f0f4de510b4f2b918fad3c91e0845104092bff/LICENSE)

## Author

[shinyay](https://github.com/shinyay)
