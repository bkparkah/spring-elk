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

    // Kafka library
    implementation("org.springframework.kafka:spring-kafka")

    // ElasticSearch Java API
    implementation("co.elastic.clients:elasticsearch-java:8.12.2")

    // apache common
    implementation("org.apache.commons:commons-lang3:3.18.0")

    // 개발 편의를 위한 Lombok (선택 사항)
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    // 테스트 환경
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.test {
    useJUnitPlatform()
}