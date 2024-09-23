# Sử dụng image Debian hoặc Alpine làm base
FROM openjdk:17-jdk-slim

# Cài đặt PostgreSQL và Redis
RUN apt-get update && apt-get install -y \
    postgresql-client \
    redis-server \
    && apt-get clean

# Copy ứng dụng Spring Boot vào container
COPY target/grocery-finder-spring-boot-1.0.jar /app/grocery-finder-spring-boot.jar

# Tạo thư mục dữ liệu cho Redis và PostgreSQL
RUN mkdir -p /var/lib/postgresql/data /data/redis

# Thiết lập môi trường cho PostgreSQL
ENV POSTGRES_USER=postgres \
    POSTGRES_PASSWORD=123456 \
    POSTGRES_DB=grocery

# Expose các cổng
EXPOSE 5432 6379 3333

# Chạy tất cả dịch vụ khi container khởi động
CMD service redis-server start && \
    docker-entrypoint.sh postgres & \
    java -jar /app/grocery-finder-spring-boot.jar
