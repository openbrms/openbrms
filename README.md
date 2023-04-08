<a name="readme-top"></a>
# Business Rules Management System

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![CI](https://github.com/alexk-dev/brms/actions/workflows/maven.yml/badge.svg)](https://github.com/alexk-dev/brms/actions/workflows/maven.yml)

## Overview

Business Rules Management System (BRMS) solution for abstracting business logic (rules, policies) from the system.
It provides a simple way to separate your rules and your application core logic,
thus ensuring that any changes to the rules will not affect the main system.

This engine supports dynamic sandboxed expressions powered by https://github.com/twineworks/tweakflow

### Prerequisites:

* Java 11
* Maven

## Getting Started

Clean and build the project, run the command:
```shell
mvn clean install
```

Then run the api service
```shell
cd api
mvn spring-boot:run
```

or you can try with docker-compose
```shell
version: "3.9"

services:

  pgsql:
    image: postgres:15-alpine
    container_name: pgsql
    ports:
      - "5432:5432"
    environment:
      - POSTGRES_USER=sa
      - POSTGRES_PASSWORD=password
      - POSTGRES_DB=brms

  api:
    image: openbrms/api:1.0.0-SNAPSHOT
    environment:
      DB_HOST: 'pgsql'
      DB_PORT: 5432
      DB_NAME: 'brms'
      DB_USER: 'sa'
      DB_PASSWORD: 'password'
      DB_SCHEMA: 'brms'
      SPRING_PROFILES_ACTIVE: 'docker'
    ports:
      - "8085:8085"
```

Finally open examples/ folder and try my python-notebooks


<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- LICENSE -->
## License
Distributed under the business friendly [MIT license](https://opensource.org/licenses/MIT).

<p align="right">(<a href="#readme-top">back to top</a>)</p>

