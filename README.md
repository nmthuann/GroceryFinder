<p align="center">
  <a href="https://spring.io/" target="blank"><img src="../GroceryFinder/docs/36fda1085cffacaebc7613ab8f227351.png" width="200" alt="Spring Boot Logo" /></a>
</p>

<p align="center">Đây là mini project <a href="https://spring.io/" target="_blank">Java Spring Boot</a> framework cho việc tìm kiếm nhanh sản phẩm tạp hóa có hỗ trợ truy xuất thông tin nguồn hàng và số lượng tồn kho qua SKU (Stock Keeping Unit).</p>

<p align="center">
  <a href="https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter" target="_blank"><img src="https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-starter.svg" alt="Maven Central Version" /></a>
  <a href="https://opensource.org/licenses/Apache License 2.0" target="_blank"><img src="https://img.shields.io/badge/license-Apache License 2.0-green.svg" alt="License" /></a>
  <a href="https://spring.io/projects/spring-boot" target="_blank"><img src="https://img.shields.io/badge/Spring%20Boot-Project-green.svg" alt="Spring Boot" /></a>
  <a href="https://gitter.im/spring-projects/spring-boot" target="_blank"><img src="https://img.shields.io/gitter/room/spring-projects/spring-boot" alt="Gitter Chat" /></a>
  <a href="https://opencollective.com/spring" target="_blank"><img src="https://img.shields.io/opencollective/sponsors/spring.svg" alt="Sponsors on Open Collective" /></a>
  <a href="https://twitter.com/springboot" target="_blank"><img src="https://img.shields.io/twitter/follow/springboot.svg?style=social&label=Follow" alt="Follow on Twitter" /></a>
</p>

---

<div align="center">
  <style>
    .social-icons a {
      display: inline-block;
      margin: 0 10px; /* Khoảng cách giữa các hình ảnh */
    }
    .social-icons img {
      width: 50px; /* Kích thước hình ảnh */
      height: auto; /* Giữ tỉ lệ hình ảnh */
    }
  </style>
  <div class="social-icons">
    <a href="https://www.facebook.com/thuanleo.wall/" target="_blank">
      <img src="../GroceryFinder/docs/36fda1085cffacaebc7613ab8f227351.png" alt="nmthuann-facebook" />
    </a>
    <a href="https://www.linkedin.com/in/nmthuann" target="_blank">
      <img src="../GroceryFinder/docs/debezium.png" alt="debezium" />
    </a>
    <a href="https://www.linkedin.com/in/nmthuann" target="_blank">
      <img src="../GroceryFinder/docs/debezium.png" alt="debezium" />
    </a>
    <a href="https://www.linkedin.com/in/nmthuann" target="_blank">
      <img src="../GroceryFinder/docs/postgresql-icon-1987x2048-v2fkmdaw.png" alt="debezium" />
    </a>
    <a href="https://www.linkedin.com/in/nmthuann" target="_blank">
      <img src="../GroceryFinder/docs/debezium.png" alt="debezium" />
    </a>
    <a href="https://www.linkedin.com/in/nmthuann" target="_blank">
      <img src="../GroceryFinder/docs/elasticsearch-icon-1839x2048-s0i8mk51.png" alt="debezium" />
    </a>
    <a href="https://www.linkedin.com/in/nmthuann" target="_blank">
      <img src="../GroceryFinder/docs/image_processing20210616-28548-1y1p6zh.png" alt="d" />
    </a>
    <a href="https://www.linkedin.com/in/nmthuann" target="_blank">
      <img src="../GroceryFinder/docs/kafka-ui-logo.png" alt="d" />
    </a>
    <a href="https://www.linkedin.com/in/nmthuann" target="_blank">
      <img src="../GroceryFinder/docs/R.png" alt="d" />
    </a>
    <a href="https://www.linkedin.com/in/nmthuann" target="_blank">
      <img src="../GroceryFinder/docs/zookeeper-icon.png" alt="d" />
    </a>
    <a href="https://www.linkedin.com/in/nmthuann" target="_blank">
      <img src="../GroceryFinder/docs/docker-icon.png" alt="d" />
    </a>
    <a href="https://www.linkedin.com/in/nmthuann" target="_blank">
      <img src="../GroceryFinder/docs/1100px_Redis_Logo_01.png" alt="d" />
    </a>
  </div>
</div>

---



## Mục Lục

- [**Description**](#description)
- [**Features**](#features)
- [**Knowledge**](#knowledge)
    - [**Docker**](#docker)
    - [**Kafka**](#kafka)
- [**Getting Started**](#getting-started)
    - [**Git Clone**](#git-clone)
    - [**Required**](#required)
    - [**Structure Folder**](#structure-folder)
- [**Installation**](#installation)
    - [**Quick Start**](#quick-start)
    - [**Using docker-compose.yml**](#using-docker-composeyml)
- [**Contact**](#contact)
- [**Reference**](#reference)

## [**Description**](#description)

Đây là dự án mini "Tìm kiếm sản phẩm nhanh" được xây dựng bằng Java Spring Boot 
nhằm cung cấp một hệ thống tìm kiếm sản phẩm hiệu quả và nhanh chóng. 
Hệ thống cho phép người dùng nhập từ khóa và tìm kiếm thông tin chi tiết của sản phẩm 
như tên, giá, mô tả và danh mục trong cơ sở dữ liệu.

## [**Features**](#features)

- **Tìm kiếm sản phẩm theo từ khóa**: Cho phép người dùng nhập từ khóa để tìm kiếm sản phẩm theo tên hoặc mô tả.

- **Lọc sản phẩm theo tiêu chí**: Kết quả tìm kiếm có thể được lọc theo danh mục, khoảng giá hoặc các thuộc tính sản phẩm khác.

- **Phân trang kết quả tìm kiếm**: Hỗ trợ phân trang để tối ưu trải nghiệm người dùng khi có nhiều kết quả trả về.

- **Bộ nhớ đệm (Caching)**: Tối ưu hóa tốc độ xử lý và phản hồi của hệ thống bằng cách lưu trữ tạm thời kết quả tìm kiếm phổ biến.

- **Tích hợp Elasticsearch (Tùy chọn)**: Tăng tốc độ và hiệu quả tìm kiếm đối với các cơ sở dữ liệu lớn nhờ tích hợp Elasticsearch.

- **Quản lý sản phẩm theo mô hình SPU - SKU**: Hệ thống quản lý sản phẩm dựa trên SPU (Standard Product Unit) và SKU (Stock Keeping Unit), cho phép quản lý chi tiết các phiên bản sản phẩm như màu sắc, kích thước và thuộc tính khác, giúp tối ưu hóa việc phân loại và tìm kiếm sản phẩm.


## [**Knowledge**](#knowledge)

### [**Tìm hiểu về Docker**](#docker)
<details>
  <summary><strong>Xem thêm</strong></summary>

Docker là một nền tảng phần mềm giúp đơn giản hóa quá trình phát triển, triển khai và chạy các ứng dụng trong các môi trường chứa (containers). Containers là các gói phần mềm bao gồm toàn bộ môi trường cần thiết cho một ứng dụng, như mã nguồn, thư viện, và các phụ thuộc, cho phép ứng dụng chạy ổn định trên bất kỳ hệ thống nào mà không cần quan tâm đến sự khác biệt về môi trường hệ điều hành hay cấu hình.

Một số khái niệm cơ bản trong Docker:
- **Image**: Một ảnh Docker chứa mọi thứ cần thiết để chạy ứng dụng, từ hệ điều hành đến các thư viện cần thiết. Images được sử dụng để tạo ra containers.
- **Container**: Một container là một phiên bản đang chạy của một image. Mỗi container hoạt động độc lập và không ảnh hưởng đến các container khác.
- **Dockerfile**: Là file định nghĩa chi tiết các bước để tạo ra một image Docker, từ hệ điều hành cơ sở, các gói phần mềm cần thiết, đến ứng dụng chính.
- **Docker Hub**: Một kho lưu trữ trực tuyến cho phép bạn tải lên và chia sẻ các Docker images.

Lợi ích của Docker:
- **Tính di động**: Docker containers có thể chạy trên mọi môi trường mà Docker hỗ trợ, từ máy cá nhân, máy chủ đến đám mây.
- **Nhẹ và hiệu quả**: Docker sử dụng tài nguyên ít hơn so với các máy ảo (VM), cho phép chạy nhiều containers trên cùng một hệ thống mà không tốn nhiều tài nguyên.
- **Quản lý phiên bản dễ dàng**: Docker cho phép quản lý các phiên bản của ứng dụng thông qua Docker images, dễ dàng chia sẻ và triển khai trên các môi trường khác nhau.

</details>

### [**7 + 1 từ khóa nên nhớ về Kafka**](#kafka)
<details>
  <summary><strong>Xem thêm</strong></summary>

1. **Message Queue**: Hàng đợi tin nhắn dùng để lưu trữ và xử lý các sự kiện.
2. **Producer**: Thành phần gửi dữ liệu (messages) vào các topic trong Kafka.
3. **Consumer**: Thành phần nhận dữ liệu từ các topic.
4. **Topic**: Một kênh chứa các dữ liệu theo danh mục mà producer và consumer tương tác.
5. **Partition**: Một topic có thể được chia nhỏ thành nhiều phần để tăng khả năng mở rộng.
6. **Broker**: Máy chủ Kafka quản lý các topic và xử lý các yêu cầu từ producers và consumers.
7. **Consumer Group**: Nhóm các consumers chia sẻ việc tiêu thụ các messages từ cùng một topic.
8. **Kafka Cluster**: Một tập hợp các brokers hoạt động đồng bộ để quản lý dữ liệu và đảm bảo khả năng mở rộng, độ tin cậy.

</details>

> Note: mọi người nhớ like ủng hộ kênh anh Tips javascript nhé. Xem xong nhớ để cho một like 👍 video nha.
Xem về Kafka [**video**](https://www.youtube.com/watch?v=a7lmP5hdgB0).

## [**Getting Started**](#getting-started)
### [**Git Clone**](#git-clone)
```shell
   git clone https://github.com/nmthuann/GroceryFinder.git
   cd GroceryFinder
  ```
### [**Required**](#required)
- **IntelliJ IDEA**: 2022.2.5
- **Docker**: 27.2.0
- **Apache Maven**: 3.9.9
- **Spring Framework**: 3.3.4
- **Java**: OpenJDK 24-ea-14
- **Redis**: 7.4.0-alpine
- **Postgres**: 16-alpine
- **Zookeeper**: 2.7
- **Kafka**: 2.7
- **Debezium**: 2.7
- **Elasticsearch**: 7.17.24
- **Kibana**: 7.17.24


### [**Structure Folder**](#structure-folder)
<details>
  <summary><strong>Xem thêm</strong></summary>

```
📦 e-commerce-backend-nestjs
├─ .eslintrc.js
├─ .gitignore
├─ .prettierrc
├─ .vscode
│  └─ launch.json
├─ README.md
├─ nest-cli.json
├─ package-lock.json
├─ package.json
├─ src
│  ├─ app.controller.spec.ts
│  ├─ app.controller.ts
│  ├─ app.module.ts
│  ├─ app.service.ts
│  ├─ common
│  │  ├─ decorators
│  │  │  └─ public.decorator.ts
│  │  ├─ errors
│  │  │  ├─ auth.error.ts
│  │  │  └─ errors.ts
│  │  ├─ exception-filter
│  │  │  ├─ exceptions
│  │  │  │  ├─ auth.exception.ts
│  │  │  │  └─ fobiden.exception.ts
│  │  │  └─ filters
│  │  │     ├─ exception.filter.ts
│  │  │     └─ http-exception.filter.ts
│  │  ├─ guards
│  │  │  ├─ admin.role.guard.ts
│  │  │  ├─ manager.role.guard.ts
│  │  │  ├─ role.guard.ts
│  │  │  └─ user.role.guard.ts
│  │  ├─ interceptors
│  │  │  ├─ checkout.interceptor.ts
│  │  │  ├─ interfaces
│  │  │  │  └─ IResponse.ts
│  │  │  └─ transform.interceptor.ts
│  │  ├─ messages
│  │  │  ├─ auth.message.ts
│  │  │  └─ message.ts
│  │  ├─ middlewares
│  │  │  └─ auth.middleware.ts
│  │  └─ pipes
│  │     └─ create-employee.validator.pipe.ts
│  ├─ configs
│  │  └─ mail.config.ts
│  ├─ main.ts
│  └─ modules
│     ├─ authentication
│     │  ├─ auth-dto
│     │  │  ├─ auth.dto.ts
│     │  │  ├─ create-employee.dto.ts
│     │  │  ├─ register-customer.dto.ts
│     │  │  ├─ register.dto.ts
│     │  │  └─ token.dto.ts
│     │  ├─ auth.controller.ts
│     │  ├─ auth.module.ts
│     │  ├─ auth.service.interface.ts
│     │  └─ auth.service.ts
│     ├─ bases
│     │  ├─ base.abstract.ts
│     │  ├─ base.entity.ts
│     │  ├─ base.interface.ts
│     │  ├─ enums
│     │  │  ├─ order-status.enum.ts
│     │  │  ├─ order.enum.ts
│     │  │  └─ role.enum.ts
│     │  └─ types
│     │     ├─ payload.type.ts
│     │     └─ token.type.ts
│     ├─ databases
│     │  ├─ database.module.ts
│     │  └─ database.providers.ts
│     ├─ inventories
│     │  ├─ inventories.controller.ts
│     │  ├─ inventories.module.ts
│     │  └─ inventories.service.ts
│     ├─ orders
│     │  ├─ cart
│     │  │  ├─ cart-detail.entity.ts
│     │  │  ├─ cart-detail
│     │  │  │  ├─ cart-detail.controller.ts
│     │  │  │  ├─ cart-detail.module.ts
│     │  │  │  ├─ cart-detail.service.interface.ts
│     │  │  │  └─ cart-detail.service.ts
│     │  │  ├─ cart-dto
│     │  │  │  ├─ cart-detail.dto.ts
│     │  │  │  └─ cart.dto.ts
│     │  │  ├─ cart.controller.ts
│     │  │  ├─ cart.entity.ts
│     │  │  ├─ cart.module.ts
│     │  │  ├─ cart.service.interface.ts
│     │  │  └─ cart.service.ts
│     │  ├─ order
│     │  │  ├─ order-detail.entity.ts
│     │  │  ├─ order-detail
│     │  │  │  ├─ order-detail.controller.ts
│     │  │  │  ├─ order-detail.module.ts
│     │  │  │  ├─ order-detail.service.interface.ts
│     │  │  │  └─ order-detail.service.ts
│     │  │  ├─ order-dto
│     │  │  │  ├─ create-order.dto.ts
│     │  │  │  ├─ get-task-orders.dto.ts
│     │  │  │  ├─ order-detail.dto.ts
│     │  │  │  ├─ order-offline.dto.ts
│     │  │  │  ├─ order-online.dto.ts
│     │  │  │  └─ order.dto.ts
│     │  │  ├─ order.controller.ts
│     │  │  ├─ order.entity.ts
│     │  │  ├─ order.module.ts
│     │  │  ├─ order.service.interface.ts
│     │  │  └─ order.service.ts
│     │  ├─ payment
│     │  │  ├─ payment.controller.ts
│     │  │  ├─ payment.dto.ts
│     │  │  ├─ payment.entity.ts
│     │  │  ├─ payment.module.ts
│     │  │  ├─ payment.service.interface.ts
│     │  │  └─ payment.service.ts
│     │  ├─ shipping
│     │  │  ├─ shipping.controller.ts
│     │  │  ├─ shipping.dto.ts
│     │  │  ├─ shipping.entity.ts
│     │  │  ├─ shipping.module.ts
│     │  │  ├─ shipping.service.interface.ts
│     │  │  └─ shipping.service.ts
│     │  └─ stripe
│     │     ├─ stripe.controller.ts
│     │     ├─ stripe.module.ts
│     │     └─ stripe.service.ts
│     ├─ products
│     │  ├─ category
│     │  │  ├─ category-dto
│     │  │  │  ├─ category.dto.ts
│     │  │  │  └─ create-category.dto.ts
│     │  │  ├─ category.controller.ts
│     │  │  ├─ category.entity.ts
│     │  │  ├─ category.module.ts
│     │  │  ├─ category.service.interface.ts
│     │  │  └─ category.service.ts
│     │  ├─ discount
│     │  │  ├─ discount-dto
│     │  │  │  └─ discount.dto.ts
│     │  │  ├─ discount.controller.ts
│     │  │  ├─ discount.entity.ts
│     │  │  ├─ discount.module.ts
│     │  │  ├─ discount.service.interface.ts
│     │  │  └─ discount.service.ts
│     │  ├─ image
│     │  │  ├─ create-image.dto.ts
│     │  │  ├─ image.controller.ts
│     │  │  ├─ image.dto.ts
│     │  │  ├─ image.entity.ts
│     │  │  ├─ image.module.ts
│     │  │  ├─ image.service.interface.ts
│     │  │  └─ image.service.ts
│     │  └─ product
│     │     ├─ entities
│     │     │  └─ product.entity.ts
│     │     ├─ product-dto
│     │     │  ├─ create-product.dto.ts
│     │     │  ├─ filter-product.dto.ts
│     │     │  ├─ get-product-detail.ts
│     │     │  ├─ get-product-order.dto.ts
│     │     │  ├─ get-product.dto.ts
│     │     │  ├─ get-products-some-field.dto.ts
│     │     │  ├─ product-duplicate.dto.ts
│     │     │  ├─ product-filter.dto.ts
│     │     │  └─ product.dto.ts
│     │     ├─ product.controller.ts
│     │     ├─ product.module.ts
│     │     ├─ product.service.interface.ts
│     │     └─ product.service.ts
│     └─ users
│        ├─ account
│        │  ├─ account-dto
│        │  │  ├─ account-employee.dto.ts
│        │  │  └─ account.dto.ts
│        │  ├─ account.controller.ts
│        │  ├─ account.entity.ts
│        │  ├─ account.module.ts
│        │  ├─ account.service.interface.ts
│        │  └─ account.service.ts
│        ├─ employee
│        │  ├─ employee-dto
│        │  │  ├─ employee.dto.ts
│        │  │  └─ get-employee-list.dto.ts
│        │  ├─ employee.controller.ts
│        │  ├─ employee.entity.ts
│        │  ├─ employee.module.ts
│        │  ├─ employee.service.interface.ts
│        │  └─ employee.service.ts
│        ├─ mail.service.ts
│        ├─ position
│        │  ├─ position.controller.ts
│        │  ├─ position.dto.ts
│        │  ├─ position.entity.ts
│        │  ├─ position.module.ts
│        │  ├─ position.service.interface.ts
│        │  └─ position.service.ts
│        ├─ user
│        │  ├─ user-dto
│        │  │  ├─ create-user.dto.ts
│        │  │  ├─ get-customer-list.dto.ts
│        │  │  └─ user.dto.ts
│        │  ├─ user.controller.ts
│        │  ├─ user.entity.ts
│        │  ├─ user.module.ts
│        │  ├─ user.service.interface.ts
│        │  └─ user.service.ts
│        ├─ users.controller.spec.ts
│        ├─ users.controller.ts
│        ├─ users.module.ts
│        ├─ users.service.spec.ts
│        └─ users.service.ts
├─ test
│  ├─ app.e2e-spec.ts
│  └─ jest-e2e.json
├─ tsconfig.build.json
├─ tsconfig.json
└─ yarn.lock
```
</details>

> ©generated by [Project Tree Generator](https://woochanleee.github.io/project-tree-generator)

## [**Installation**](#installation)
### [**Quick Start**](#quick-start)
- Open a terminal and use it to start ZooKeeper in a container.
  ```shell 
  docker run -it --rm --name zookeeper -p 2181:2181 -p 2888:2888 -p 3888:3888 quay.io/debezium/zookeeper:2.7
  ```
- Open a new terminal and use it to start Kafka in a container.
  ```shell
  docker run -it --rm --name kafka -p 9092:9092 --link zookeeper:zookeeper quay.io/debezium/kafka:2.7
  ```
- Open a new terminal, and use it to start a new container that runs a MySQL database server preconfigured with an inventory database.
  ```shell
  docker run -it --rm --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=debezium -e MYSQL_USER=mysqluser -e MYSQL_PASSWORD=mysqlpw quay.io/debezium/example-mysql:2.7
  ```
- Open a new terminal, and use it to start the MySQL command line client in a container.
  ```shell
  docker run -it --rm --name mysqlterm --link mysql mysql:8.2 sh -c 'exec mysql -h"$MYSQL_PORT_3306_TCP_ADDR" -P"$MYSQL_PORT_3306_TCP_PORT" -uroot -p"$MYSQL_ENV_MYSQL_ROOT_PASSWORD"'
  ```
- Open a new terminal, and use it to start the Kafka Connect service in a container.
  ```shell
  docker run -it --rm --name connect -p 8083:8083 -e GROUP_ID=1 -e CONFIG_STORAGE_TOPIC=my_connect_configs -e OFFSET_STORAGE_TOPIC=my_connect_offsets -e STATUS_STORAGE_TOPIC=my_connect_statuses --link kafka:kafka --link mysql:mysql quay.io/debezium/connect:2.7
  ```
- Connect
  ```shell
  curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" localhost:8083/connectors/ -d @register-postgres.json
  ```

### [**Using docker-compose.yml**](#using-docker-composeyml)
  ```yaml
version: '3.8'

networks:
  bridge-net:
    driver: bridge

services:
  zookeeper:
    image: debezium/zookeeper:2.7
    container_name: zookeeper
    environment:
      SERVER_ID: 1
      SERVER_COUNT: 1
    ports:
      - '2181:2181'
    networks:
      - bridge-net

  kafka:
    image: debezium/kafka:2.7
    container_name: kafka
    depends_on:
      - zookeeper
    environment:
      BROKER_ID: 1
      ZOOKEEPER_CONNECT: zookeeper:2181
      HOST_NAME: kafka
      ADVERTISED_HOST_NAME: kafka  # Thay đổi từ localhost thành kafka
    ports:
      - '9092:9092'
    networks:
      - bridge-net

  debezium-connect:
    container_name: debezium-connect
    image: debezium/connect:2.7
    ports:
      - '8083:8083'
    environment:
      GROUP_ID: 1
      CONFIG_STORAGE_TOPIC: my_connect_configs
      OFFSET_STORAGE_TOPIC: my_connect_offsets
      BOOTSTRAP_SERVERS: kafka:9092
      HOST_NAME: debezium-connect
      ADVERTISED_HOST_NAME: debezium-connect
      ADVERTISED_PORT: 8083
      KEY_CONVERTER: org.apache.kafka.connect.json.JsonConverter
      VALUE_CONVERTER: org.apache.kafka.connect.json.JsonConverter
      INTERNAL_KEY_CONVERTER: org.apache.kafka.connect.json.JsonConverter
      INTERNAL_VALUE_CONVERTER: org.apache.kafka.connect.json.JsonConverter
    networks:
      - bridge-net

  kafka-ui:
    container_name: kafka-ui
    image: provectuslabs/kafka-ui:latest
    ports:
      - "8080:8080"
    environment:
      DYNAMIC_CONFIG_ENABLED: true
      KAFKA_CLUSTERS_0_NAME: "local"
      KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS: "kafka:9092"
    networks:
      - bridge-net

  postgresql-db:
    container_name: postgresql-db-container
    image: debezium/postgres:16-alpine
    restart: always
    ports:
      - "5432:5432"
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: 123456
      POSTGRES_DB: grocery
    volumes:
      - grocery-finder-postgresql-db:/var/lib/postgresql/data
    networks:
      - bridge-net

  redis:
    container_name: redis-container
    image: redis:7.4.0-alpine
    ports:
      - "6379:6379"
    restart: always
    volumes:
      - redis-data:/data
    networks:
      - bridge-net

  grocery-finder-spring-boot:
    container_name: grocery-finder-spring-boot-container
    image: grocery-finder-spring-boot:1.0
    ports:
      - "3333:3333"
    depends_on:
      - postgresql-db
    build:
      context: .
      dockerfile: Dockerfile
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgresql-db:5432/grocery?autoReconnect=true&allowPublicKeyRetrieval=true&useSSL=false
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: 123456
      SPRING_DATASOURCE_DRIVER-CLASS-NAME: org.postgresql.Driver
    networks:
      - bridge-net

  elasticsearch:
    image: docker.elastic.co/elasticsearch/elasticsearch:7.17.24
    environment:
      discovery.type: single-node
      ES_JAVA_OPTS: "-Xms512m -Xmx512m"
    ports:
      - "9200:9200"
    volumes:
      - es-data:/usr/share/elasticsearch/data
    networks:
      - bridge-net

  kibana:
    image: docker.elastic.co/kibana/kibana:7.17.24
    ports:
      - "5601:5601"
    environment:
      ELASTICSEARCH_HOSTS: "http://elasticsearch:9200"
    depends_on:
      - elasticsearch
    networks:
      - bridge-net

volumes:
  grocery-finder-postgresql-db:
  redis-data:
  es-data: 
```

## [**Contact**](#contact)
Nếu có câu hỏi hoặc phản hồi, vui lòng liên hệ qua email của mình nhé: [thuanminh.2001286@gmail.com](mailto:thuanminh.2001286@gmail.com).
## [**Reference**](#reference)
- **Debezium Tutorial**: [Debezium Documentation](https://debezium.io/documentation/reference/2.7/tutorial.html)
- **Kafka UI GitHub**: [Kafka UI Repository](https://github.com/provectus/kafka-ui)
- **Java Spring Boot Documentation**: [Tips JavaScript (YouTube)](https://www.youtube.com/c/TipsJavaScript)
- **Docker Hub**: (link unspecified)

## License <a name="license"></a>
GroceryFinder is [Apache License 2.0 licensed](https://www.apache.org/licenses/).