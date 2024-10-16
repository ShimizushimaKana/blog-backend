val kotlinxSerializationJsonVersion = "1.3.0"
val mysqlVersion = "8.0.15"
val jjwtVersion = "0.9.1"
val aliyunOssVersion = "3.13.0"

plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	kotlin("plugin.serialization") version "1.9.25"
	id("org.springframework.boot") version "2.5.14"
	id("io.spring.dependency-management") version "1.1.6"
	kotlin("plugin.jpa") version "1.9.25"
}

group = "com.soyo"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(8)
	}
}

repositories {
	mavenCentral()
	maven {
		url = uri("https://maven.aliyun.com/repository/public")
		url = uri("https://maven.aliyun.com/repository/google")
		url = uri("https://maven.aliyun.com/repository/central")
	}
	google()
}

dependencies {
	implementation(kotlin("stdlib"))
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${kotlinxSerializationJsonVersion}")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("mysql:mysql-connector-java:${mysqlVersion}")
	implementation("io.jsonwebtoken:jjwt:${jjwtVersion}")
	implementation("com.aliyun.oss:aliyun-sdk-oss:${aliyunOssVersion}")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.processResources {
	from("src/main/resources") {
		include("application.properties")
		include("**/*.yml")
	}
	duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
