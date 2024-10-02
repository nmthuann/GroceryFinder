1. Create a Docker Network
   ```
   docker network create <network-name>
    ```
   ```yaml
    networks:
    bridge-net:
    driver: bridge
    ```

    ```shell
    docker network create --driver bridge bridge-net
    docker network ls
    ```
   
2. Create a PostgreSQL container
    ```yaml
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
    ```
   
    ```shell
    docker run -d \
    --name postgresql-db-container \
    --restart always \
    -e POSTGRES_USER=postgres \
    -e POSTGRES_PASSWORD=123456 \
    -e POSTGRES_DB=grocery \
    -p 5432:5432 \
    -v grocery-finder-postgresql-db:/var/lib/postgresql/data \
    --network bridge-net \
    debezium/postgres:16-alpine
    ```
   ```shell
   docker ps -a
   docker logs postgresql-db-container
   docker exec -it postgresql-db-container psql -U postgres -d grocery
   ```
3. Create a Redis container 
   ```yaml
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
   ```
   ```shell
    docker run -d \
   --name redis-container \
   --restart always \
   -p 6379:6379 \
   -v redis-data:/data \
   --network bridge-net \
   redis:7.4.0-alpine
   ```
   ```shell
   docker ps -a
   ```
4. Create an Elasticsearch Container
   ```yaml
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
   ```
   ```shell
   docker run -d --name elasticsearch-container \
   --network bridge-net \
   -p 9200:9200 \
   -e "discovery.type=single-node" \
   -e "ES_JAVA_OPTS=-Xms512m -Xmx512m" \
   -v es-data:/usr/share/elasticsearch/data \
   docker.elastic.co/elasticsearch/elasticsearch:7.17.24
   ```
5. The End