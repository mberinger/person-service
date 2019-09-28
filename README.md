# person-service

This service demo's how you can provide new functionality to existing REST API's without breaking 'contracts' for consuming services. Ideally all changes should either be backwards compatible or you would version the REST API itself, but this service is considering a more pragmatic approach.

## person-service structure

The `person-service` exposes a single REST API:

GET /person

The `person-service` provides a 'client' for consuming services to use. In version `0.0.1-SNAPSHOT`, the `person-service` provides basic details about a person, in version `0.0.2-SNAPSHOT`, the `person-service` additionally provides details about a person's dependencies i.e. children/kids. 

There are two consumer services, `address-service` and `contact-service` that are detailed below.

## address-service

This service uses version `0.0.2-SNAPSHOT` of the `person-service` and thus invokes the latest version of the `/person` REST API. See the [README](https://github.com/mikebelringer/address-service) for more details.

## contact-service

This service uses version `0.0.1-SNAPSHOT` of the `person-service` and demonstrates how it continues to work despite the new changes introduced in version `0.0.2-SNAPSHOT`. See the [README](https://github.com/mikebelringer/contact-service) for more details.

## How to run

Run the main class in `RestletServerApplication`