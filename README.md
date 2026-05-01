# px_cloud_al

Spring Cloud Alibaba微服务架构

## px_gateway服务更新流程

```shell
cd /Users/wangdante/D/kugou/px/px_cloud_al
mvn clean package -pl px_gateway -am -DskipTests

cp /Users/wangdante/D/kugou/px/px_cloud_al/px_gateway/target/px_gateway-1.0-SNAPSHOT.jar \
   /Users/wangdante/D/mydocker/px_gateway/px_gateway-1.0-SNAPSHOT.jar

cd /Users/wangdante/D/mydocker
docker compose build px_gateway
docker compose up -d px_gateway
```

## px_bff_service服务更新流程

指定范围打包，并构建相关依赖，路过测试

```shell
cd /Users/wangdante/D/kugou/px/px_cloud_al
mvn clean package -pl px_bff_service -am -DskipTests

cp /Users/wangdante/D/kugou/px/px_cloud_al/px_bff_service/target/px_bff_service-1.0-SNAPSHOT.jar \
   /Users/wangdante/D/mydocker/px_bff_service/px_bff_service-1.0-SNAPSHOT.jar

cd /Users/wangdante/D/mydocker
docker compose build px_bff_service
docker compose up -d px_bff_service
```

## px_app_service服务更新流程

指定范围打包，并构建相关依赖，路过测试

```shell
cd /Users/wangdante/D/kugou/px/px_cloud_al
mvn clean package -pl px_app_service -am -DskipTests

cp /Users/wangdante/D/kugou/px/px_cloud_al/px_app_service/target/px_app_service-1.0-SNAPSHOT.jar \
   /Users/wangdante/D/mydocker/px_app_service/px_app_service-1.0-SNAPSHOT.jar

cd /Users/wangdante/D/mydocker
docker compose build px_app_service
docker compose up -d px_app_service
```

