import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.3.0"
	id("io.spring.dependency-management") version "1.1.5"
	kotlin("jvm") version "1.9.24"
	kotlin("plugin.spring") version "1.9.24"
}

group = "com"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	// Kotlin related
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	// Spring related
	implementation("org.springframework.boot:spring-boot-starter-web")

	// Request/Response serialization
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	// HTTP
	implementation("com.squareup.okhttp3:okhttp:4.12.0")

	// Dev
	implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")

	// Testing
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
