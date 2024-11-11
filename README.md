<p align="center">
  <a href="https://spring.io/" target="blank"><img src="https://github.com/nmthuann/GroceryFinder/blob/master/docs/images/icons/36fda1085cffacaebc7613ab8f227351.png" width="200" alt="Spring Boot Logo" /></a>
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

<br>
<p align="center">
  <a href="https://github.com/nmthuann/GroceryFinder/blob/master/docs/images/icons/36fda1085cffacaebc7613ab8f227351.png" target="_blank">
    <img src="https://github.com/nmthuann/GroceryFinder/blob/master/docs/images/icons/36fda1085cffacaebc7613ab8f227351.png" alt="springboot" width="70" />
  </a>
  <a href="https://github.com/nmthuann/GroceryFinder/blob/master/docs/images/icons/postgresql-icon-1987x2048-v2fkmdaw.png" target="_blank">
    <img src="https://github.com/nmthuann/GroceryFinder/blob/master/docs/images/icons/postgresql-icon-1987x2048-v2fkmdaw.png" alt="postgresql" width="50" />
  </a>
  <a href="https://github.com/nmthuann/GroceryFinder/blob/master/docs/images/icons/docker-icon.png" target="_blank">
    <img src="https://github.com/nmthuann/GroceryFinder/blob/master/docs/images/icons/docker-icon.png" alt="docker" width="100" />
  </a>
  <a href="https://github.com/nmthuann/GroceryFinder/blob/master/docs/images/icons/1100px_Redis_Logo_01.png" target="_blank">
    <img src="https://github.com/nmthuann/GroceryFinder/blob/master/docs/images/icons/1100px_Redis_Logo_01.png" alt="redis" width="100" />
  </a>
</p>
<br>

---

## Mục Lục

- [**Description**](#description)
- [**Features**](#features)
- [**Knowledge**](#knowledge)
    - [**Nested Set Model**](#nested-set-modelnested-set-model)
    - [**API Phân quyền với Spring Security**](#api-phn-quyn-vi-spring-securityapi-authen-author)
    - [**Áp dựng Strategy Pattern cho tính năng Passport**](#)
    - [**API VERSION**](#)
    - [**SPU & SKU**](#)
- [**Getting Started**](#getting-started)
    - [**Git Clone**](#git-clone)
    - [**Required**](#required)
    - [**Structure Folder**](#structure-folder)
- [**Installation**](#installation)
  - [**Quick Start**](#quick-start)
  - [**Using docker-compose.yml**](#using-docker-composeyml)
- [**Contact**](#contact)
- [**Reference**](#reference)

## [**1. Description**](#description)

Đây là dự án nhỏ về "quản lý sản phẩm theo danh mục có nhiều biến thể" được xây dựng bằng Java Spring Boot 
nhằm cung cấp một hệ thống tìm kiếm sản phẩm hiệu quả và nhanh chóng. 
Hệ thống cho phép người dùng nhập từ khóa và tìm kiếm thông tin chi tiết của sản phẩm 
như tên, giá, mô tả và danh mục trong cơ sở dữ liệu.

## [**2. Features**](#features)

- **Tìm kiếm sản phẩm theo từ khóa**: Cho phép người dùng nhập từ khóa để tìm kiếm sản phẩm theo tên hoặc mô tả.
- **Quản lý sản phẩm theo mô hình SPU - SKU**: Hệ thống quản lý sản phẩm dựa trên SPU (Standard Product Unit) và SKU (Stock Keeping Unit), cho phép quản lý chi tiết các phiên bản sản phẩm như màu sắc, kích thước và thuộc tính khác, giúp tối ưu hóa việc phân loại và tìm kiếm sản phẩm.
- **Phân trang kết quả tìm kiếm**: Hỗ trợ phân trang để tối ưu trải nghiệm người dùng khi có nhiều kết quả trả về.
- **Quản lý Danh mục có nhiều danh mục con**: Hệ thống quản lý sản phẩm dựa trên SPU (Standard Product Unit) và SKU (Stock Keeping Unit), cho phép quản lý chi tiết các phiên bản sản phẩm như màu sắc, kích thước và thuộc tính khác, giúp tối ưu hóa việc phân loại và tìm kiếm sản phẩm.
- **Sử dụng Redis cho OTP**: Tối ưu hóa tốc độ xử lý và phản hồi của hệ thống bằng cách lưu trữ tạm thời kết quả tìm kiếm phổ biến.

## [**3. Knowledge**](#knowledge)
### [**3.1 Nested Set Model**](#nested-set-model)
Nested Set Model là một kỹ thuật để lưu trữ dữ liệu dạng cây trong cơ sở dữ liệu quan hệ bằng cách sử dụng hai giá trị `left` và `right` cho mỗi node. Giúp truy vấn các nhánh cây con nhanh chóng, nhưng cập nhật cây có thể phức tạp.

```sql
  CREATE TABLE categories (
        id INT PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        left_value INT NOT NULL,
        right_value INT NOT NULL
  );
```
![Mô tả ảnh](https://github.com/nmthuann/GroceryFinder/blob/master/docs/images/web/nested-set-model-categories-table.png)
### [**3.2 API phân quyền với Spring Security**](#api-authen-author)
Spring Security cung cấp các tính năng xác thực và phân quyền cho API. Bạn có thể phân quyền các endpoint theo vai trò.
![Mô tả ảnh](https://github.com/nmthuann/GroceryFinder/blob/master/docs/images/web/api-authen-author.png)
### [**3.5 Áp dựng Strategy Pattern cho tính năng Passport**](#spu-sku)
![Mô tả ảnh](https://example.com/path/to/image.jpg)
### [**3.3 API VERSION**](#api-version)
API Versioning giúp hỗ trợ nhiều phiên bản API mà không ảnh hưởng đến client cũ, có thể thực hiện qua URL, header, hoặc request parameter.
![Mô tả ảnh](https://example.com/path/to/image.jpg)
### [**3.4 SPU & SKU**](#spu-sku)
- SPU (Standard Product Unit): Sản phẩm chung dựa trên các thuộc tính giống nhau.
- SKU (Stock Keeping Unit): Phiên bản cụ thể của SPU, khác nhau bởi các thuộc tính như màu sắc, kích thước.
  ![Mô tả ảnh](https://example.com/path/to/image.jpg)
## [**4. Getting Started**](#getting-started)
### [**4.1 Git Clone**](#git-clone)
```shell
   git clone https://github.com/nmthuann/GroceryFinder.git
   cd GroceryFinder
  ```
### [**4.2 Required**](#required)
- **IntelliJ IDEA**: 2022.2.5
- **Docker**: 27.2.0
- **Apache Maven**: 3.9.9
- **Spring Framework**: 3.3.4
- **Spring Boot**: 3.3.4
- **Java**: OpenJDK 24-ea-14
- **Redis**: 7.4.0-alpine
- **Postgres**: 16-alpine

### [**4.3 Structure Folder**](#structure-folder)

```
📦 
├─ pom.xml
└─ src
   ├─ main
   │  ├─ java
   │  │  └─ com
   │  │     └─ nmt
   │  │        └─ groceryfinder
   │  │           ├─ GroceryFinderApplication.java
   │  │           ├─ common
   │  │           │  ├─ bases
   │  │           │  │  ├─ AbstractBaseDto.java
   │  │           │  │  ├─ AbstractBaseEntity.java
   │  │           │  │  ├─ AbstractBaseService.java
   │  │           │  │  ├─ AbstractModelMapper.java
   │  │           │  │  ├─ AuditableEntity.java
   │  │           │  │  └─ IBaseService.java
   │  │           │  ├─ dtos
   │  │           │  │  ├─ Payload.java
   │  │           │  │  └─ Tokens.java
   │  │           │  ├─ enums
   │  │           │  │  ├─ ApiVersionEnum.java
   │  │           │  │  ├─ AuthMethodEnum.java
   │  │           │  │  ├─ BrandBusinessType.java
   │  │           │  │  ├─ CategoryParentEnum.java
   │  │           │  │  ├─ ProductStatusEnum.java
   │  │           │  │  ├─ RoleEnum.java
   │  │           │  │  └─ SubjectMailEnum.java
   │  │           │  └─ messages
   │  │           │     ├─ AccountMessages.java
   │  │           │     ├─ AuthMessages.java
   │  │           │     └─ UserMessages.java
   │  │           ├─ configs
   │  │           │  ├─ AuditConfig.java
   │  │           │  ├─ AuthenticationConfig.java
   │  │           │  ├─ CorsConfig.java
   │  │           │  ├─ ElasticsearchConfig.java
   │  │           │  ├─ MailConfig.java
   │  │           │  ├─ ModelMapperConfig.java
   │  │           │  ├─ OpenAPIConfig.java
   │  │           │  ├─ RedisConfig.java
   │  │           │  └─ SpringSecurityConfig.java
   │  │           ├─ exceptions
   │  │           │  ├─ ModuleException.java
   │  │           │  ├─ RestErrorResponse.java
   │  │           │  ├─ handler
   │  │           │  │  ├─ DelegatedAuthenticationEntryPoint.java
   │  │           │  │  └─ GlobalExceptionHandler.java
   │  │           │  └─ messages
   │  │           │     ├─ AuthExceptionMessages.java
   │  │           │     ├─ InventoriesModuleExceptionMessages.java
   │  │           │     ├─ JwtExceptionMessages.java
   │  │           │     ├─ OrdersModuleExceptionMessages.java
   │  │           │     ├─ ProductsModuleExceptionMessages.java
   │  │           │     └─ UsersModuleExceptionMessages.java
   │  │           ├─ filters
   │  │           │  ├─ ApiVersionFilter.java
   │  │           │  └─ AuthMiddlewareFilter.java
   │  │           ├─ loader
   │  │           │  └─ CategoryDataLoader.java
   │  │           ├─ modules
   │  │           │  ├─ MainController.java
   │  │           │  ├─ auth
   │  │           │  │  ├─ AuthController.java
   │  │           │  │  ├─ AuthService.java
   │  │           │  │  ├─ IAuthService.java
   │  │           │  │  ├─ UserDetailServiceImp.java
   │  │           │  │  └─ dtos
   │  │           │  │     ├─ requests
   │  │           │  │     │  ├─ CheckOtpRequestDto.java
   │  │           │  │     │  ├─ CreateEmployeeDto.java
   │  │           │  │     │  ├─ CreateUserDto.java
   │  │           │  │     │  ├─ GetUserInformationDto.java
   │  │           │  │     │  ├─ LoginAdminRequestDto.java
   │  │           │  │     │  ├─ LoginRequestDto.java
   │  │           │  │     │  ├─ PassportDto.java
   │  │           │  │     │  ├─ RegisterAdminRequestDto.java
   │  │           │  │     │  ├─ RegisterRequestDto.java
   │  │           │  │     │  ├─ ResendOtpRequestDto.java
   │  │           │  │     │  └─ VerifyEmailRequestDto.java
   │  │           │  │     └─ responses
   │  │           │  │        ├─ AuthenticationResponseDto.java
   │  │           │  │        ├─ LoginResponseDto.java
   │  │           │  │        ├─ RegisterAdminResponseDto.java
   │  │           │  │        └─ RegisterResponseDto.java
   │  │           │  ├─ inventories
   │  │           │  │  ├─ IInventoryRepository.java
   │  │           │  │  ├─ IInventoryService.java
   │  │           │  │  ├─ InventoryController.java
   │  │           │  │  ├─ InventoryMapper.java
   │  │           │  │  ├─ InventoryService.java
   │  │           │  │  └─ domain
   │  │           │  │     ├─ InventoryEntity.java
   │  │           │  │     └─ dtos
   │  │           │  │        ├─ CreateInventoryDto.java
   │  │           │  │        ├─ InventoryDto.java
   │  │           │  │        ├─ UpdateInventoryDto.java
   │  │           │  │        └─ UpdateQuantityInventoryDto.java
   │  │           │  ├─ products
   │  │           │  │  ├─ controllers
   │  │           │  │  │  ├─ BrandController.java
   │  │           │  │  │  ├─ CategoryController.java
   │  │           │  │  │  ├─ ProductController.java
   │  │           │  │  │  ├─ ProductSkuController.java
   │  │           │  │  │  └─ SupplierController.java
   │  │           │  │  ├─ domain
   │  │           │  │  │  ├─ mappers
   │  │           │  │  │  │  ├─ BrandMapper.java
   │  │           │  │  │  │  ├─ CategoryMapper.java
   │  │           │  │  │  │  ├─ ImageMapper.java
   │  │           │  │  │  │  ├─ PriceMapper.java
   │  │           │  │  │  │  ├─ ProductMapper.java
   │  │           │  │  │  │  ├─ ProductSkuMapper.java
   │  │           │  │  │  │  ├─ SpuSkuMappingMapper.java
   │  │           │  │  │  │  └─ SupplierMapper.java
   │  │           │  │  │  └─ model
   │  │           │  │  │     ├─ dtos
   │  │           │  │  │     │  ├─ BrandDto.java
   │  │           │  │  │     │  ├─ CategoryDto.java
   │  │           │  │  │     │  ├─ ImageDto.java
   │  │           │  │  │     │  ├─ PriceDto.java
   │  │           │  │  │     │  ├─ ProductDto.java
   │  │           │  │  │     │  ├─ ProductSkuDto.java
   │  │           │  │  │     │  ├─ SpuSkuMappingDto.java
   │  │           │  │  │     │  ├─ SupplierDto.java
   │  │           │  │  │     │  ├─ requests
   │  │           │  │  │     │  │  ├─ CreateCategoryDto.java
   │  │           │  │  │     │  │  ├─ CreateImageDto.java
   │  │           │  │  │     │  │  ├─ CreatePriceDto.java
   │  │           │  │  │     │  │  ├─ CreateProductDto.java
   │  │           │  │  │     │  │  ├─ CreateProductSkuDto.java
   │  │           │  │  │     │  │  ├─ CreateSpuSkuMappingDto.java
   │  │           │  │  │     │  │  ├─ UpdateCategoryDto.java
   │  │           │  │  │     │  │  ├─ UpdateProductDto.java
   │  │           │  │  │     │  │  ├─ UpdateProductSkuDto.java
   │  │           │  │  │     │  │  └─ UpdateSupplierDto.java
   │  │           │  │  │     │  └─ responses
   │  │           │  │  │     │     ├─ GetSkuDetailResponse.java
   │  │           │  │  │     │     ├─ ProductInfoToSearch.java
   │  │           │  │  │     │     └─ SpuSkuMappingResponse.java
   │  │           │  │  │     └─ entities
   │  │           │  │  │        ├─ BrandEntity.java
   │  │           │  │  │        ├─ CategoryEntity.java
   │  │           │  │  │        ├─ ImageEntity.java
   │  │           │  │  │        ├─ PriceEntity.java
   │  │           │  │  │        ├─ PriceIdEntity.java
   │  │           │  │  │        ├─ ProductEntity.java
   │  │           │  │  │        ├─ ProductSkuEntity.java
   │  │           │  │  │        ├─ SpuSkuMappingEntity.java
   │  │           │  │  │        └─ SupplierEntity.java
   │  │           │  │  ├─ repositories
   │  │           │  │  │  ├─ IBrandRepository.java
   │  │           │  │  │  ├─ ICategoryRepository.java
   │  │           │  │  │  ├─ IImageRepository.java
   │  │           │  │  │  ├─ IPriceRepository.java
   │  │           │  │  │  ├─ IProductRepository.java
   │  │           │  │  │  ├─ IProductSkuRepository.java
   │  │           │  │  │  ├─ ISpuSkuMappingRepository.java
   │  │           │  │  │  └─ ISupplierRepository.java
   │  │           │  │  └─ services
   │  │           │  │     ├─ IBrandService.java
   │  │           │  │     ├─ ICategoryService.java
   │  │           │  │     ├─ IImageService.java
   │  │           │  │     ├─ IPriceService.java
   │  │           │  │     ├─ IProductService.java
   │  │           │  │     ├─ IProductSkuService.java
   │  │           │  │     ├─ ISpuSkuMappingService.java
   │  │           │  │     ├─ ISupplierService.java
   │  │           │  │     └─ impl
   │  │           │  │        ├─ BrandService.java
   │  │           │  │        ├─ CategoryService.java
   │  │           │  │        ├─ ImageService.java
   │  │           │  │        ├─ PriceService.java
   │  │           │  │        ├─ ProductService.java
   │  │           │  │        ├─ ProductSkuService.java
   │  │           │  │        ├─ SpuSkuMappingService.java
   │  │           │  │        └─ SupplierService.java
   │  │           │  └─ users
   │  │           │     ├─ controllers
   │  │           │     │  ├─ EmployeeController.java
   │  │           │     │  └─ UserController.java
   │  │           │     ├─ domain
   │  │           │     │  ├─ mappers
   │  │           │     │  │  ├─ CustomerMapper.java
   │  │           │     │  │  ├─ EmployeeMapper.java
   │  │           │     │  │  └─ UserMapper.java
   │  │           │     │  └─ model
   │  │           │     │     ├─ dtos
   │  │           │     │     │  ├─ AccountDto.java
   │  │           │     │     │  ├─ CustomerDto.java
   │  │           │     │     │  ├─ EmployeeDto.java
   │  │           │     │     │  ├─ ProfileDto.java
   │  │           │     │     │  └─ UserDto.java
   │  │           │     │     └─ entities
   │  │           │     │        ├─ CustomerEntity.java
   │  │           │     │        ├─ EmployeeEntity.java
   │  │           │     │        └─ UserEntity.java
   │  │           │     ├─ repositories
   │  │           │     │  ├─ ICustomerRepository.java
   │  │           │     │  ├─ IEmployeeRepository.java
   │  │           │     │  └─ IUserRepository.java
   │  │           │     └─ services
   │  │           │        ├─ ICustomerService.java
   │  │           │        ├─ IEmployeeService.java
   │  │           │        ├─ IUserService.java
   │  │           │        └─ impl
   │  │           │           ├─ CustomerService.java
   │  │           │           ├─ EmployeeService.java
   │  │           │           └─ UserService.java
   │  │           ├─ shared
   │  │           │  ├─ audit
   │  │           │  │  └─ AuditorAwareImpl.java
   │  │           │  ├─ cqrs
   │  │           │  │  ├─ commands
   │  │           │  │  │  ├─ CreateProductCommand.java
   │  │           │  │  │  └─ UpdateProductCommand.java
   │  │           │  │  └─ handlers
   │  │           │  │     ├─ ICommandHandler.java
   │  │           │  │     ├─ IQueryHandler.java
   │  │           │  │     └─ impl
   │  │           │  │        └─ ProductCommandHandler.java
   │  │           │  ├─ elasticsearch
   │  │           │  │  ├─ ElasticsearchService.java
   │  │           │  │  ├─ IElasticsearchService.java
   │  │           │  │  ├─ documents
   │  │           │  │  │  └─ ProductDocument.java
   │  │           │  │  └─ sync
   │  │           │  │     ├─ SyncData.java
   │  │           │  │     └─ SyncDataAspect.java
   │  │           │  ├─ health
   │  │           │  │  ├─ ElasticsearchHealthCheck.java
   │  │           │  │  ├─ HealthCheckAspect.java
   │  │           │  │  ├─ HealthCheckInterceptor.java
   │  │           │  │  ├─ HealthCheckService.java
   │  │           │  │  ├─ HealthCheckType.java
   │  │           │  │  └─ ServerHealthIndicator.java
   │  │           │  ├─ logging
   │  │           │  │  ├─ LoggingAspect.java
   │  │           │  │  └─ LoggingInterceptor.java
   │  │           │  ├─ mail
   │  │           │  │  └─ MailContentLoader.java
   │  │           │  ├─ passport
   │  │           │  │  ├─ PassportStrategy.java
   │  │           │  │  ├─ common
   │  │           │  │  │  ├─ OauthGoogleLoginDto.java
   │  │           │  │  │  ├─ OtpPhoneLoginDto.java
   │  │           │  │  │  ├─ PassportStrategyEnum.java
   │  │           │  │  │  └─ UsernamePasswordLoginDto.java
   │  │           │  │  ├─ impl
   │  │           │  │  │  ├─ PassportContext.java
   │  │           │  │  │  └─ PassportStrategyFactory.java
   │  │           │  │  └─ strategies
   │  │           │  │     ├─ LocalStrategy.java
   │  │           │  │     ├─ OauthGoogleStrategy.java
   │  │           │  │     └─ OtpPhoneStrategy.java
   │  │           │  └─ redis
   │  │           │     ├─ IRedisService.java
   │  │           │     └─ RedisService.java
   │  │           └─ utils
   │  │              ├─ DefaultValueUtil.java
   │  │              ├─ FileUtil.java
   │  │              ├─ JsonUtil.java
   │  │              ├─ JwtServiceUtil.java
   │  │              ├─ MailServiceUtil.java
   │  │              ├─ PasswordUtil.java
   │  │              ├─ SlugUtil.java
   │  │              ├─ StringUtil.java
   │  │              └─ UrlUtil.java
   │  └─ resources
   │     ├─ application-example.yaml
   │     ├─ data
   │     │  ├─ categories.json
   │     │  └─ productDto.json
   │     ├─ logback.xml
   │     └─ static
   │        └─ index.html
   └─ test
      └─ java
         └─ com
            └─ nmt
               └─ groceryfinder
                  ├─ GroceryFinderApplicationTests.java
                  ├─ aop
                  │  └─ SyncDataAspectTest.java
                  ├─ dockers
                  │  ├─ ElasticsearchDockerTest.java
                  │  ├─ PostgresDockerTest.java
                  │  └─ RedisDockerTest.java
                  ├─ shared
                  │  └─ redis
                  │     └─ RedisServiceTest.java
                  └─ utils
                     └─ PasswordUtilTest.java
```
> ©generated by [Project Tree Generator](https://woochanleee.github.io/project-tree-generator)
## [**5. Lược đồ ERD**](#erd)
![Mô tả ảnh](https://github.com/nmthuann/GroceryFinder/blob/master/docs/images/web/ERD-grocery-db.png)
## [**6. Installation**](#installation)
### [**6.1 Quick Start**](#quick-start)
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

## [**7. Contact**](#contact)
Nếu có câu hỏi hoặc phản hồi, vui lòng liên hệ qua email của mình nhé: [thuanminh.2001286@gmail.com](mailto:thuanminh.2001286@gmail.com).
## [**8. Reference**](#reference)
- **Debezium Tutorial**: [Debezium Documentation](https://debezium.io/documentation/reference/2.7/tutorial.html)
- **Kafka UI GitHub**: [Kafka UI Repository](https://github.com/provectus/kafka-ui)
- **Java Spring Boot Documentation**: [Tips JavaScript (YouTube)](https://www.youtube.com/c/TipsJavaScript)
- **Docker Hub**: (link unspecified)

## License <a name="license"></a>
GroceryFinder is [Apache License 2.0 licensed](https://www.apache.org/licenses/).
