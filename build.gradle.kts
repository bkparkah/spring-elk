plugins {
    id("org.springframework.boot") version "2.7.18" // 사용하려는 2.x 버전 입력
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("java")
}

group = "com.kbds.devops"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("software.amazon.awssdk:s3:2.20.0")

    implementation("jakarta.json:jakarta.json-api:2.0.1")

    // Kafka library
    implementation("org.apache.kafka:kafka-clients:3.9.2")

    // ElasticSearch Java API
    implementation("co.elastic.clients:elasticsearch-java:8.12.2")
    implementation("org.elasticsearch.client:elasticsearch-rest-client:8.12.2")

    // apache common
    implementation("org.apache.commons:commons-lang3:3.18.0")

    // Http Appender for Logback
    implementation("net.logstash.logback:logstash-logback-encoder:7.2")

    // 개발 편의를 위한 Lombok (선택 사항)
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    // 테스트 환경
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.test {
    useJUnitPlatform()
}