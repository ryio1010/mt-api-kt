import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
	repositories {
		mavenCentral()
	}
	dependencies {
		classpath("org.springframework.boot:spring-boot-gradle-plugin:2.2.0.RELEASE")
		classpath("com.google.cloud.tools:appengine-gradle-plugin:2.2.0'")
	}
}

plugins {
	id("org.springframework.boot") version "2.7.0"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	kotlin("kapt") version "1.5.30"
	id("org.seasar.doma.compile") version "1.1.0"
	id("com.google.cloud.tools.appengine") version "2.2.0"
}

group = "com.muscletracking"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	maven("https://oss.sonatype.org/content/repositories/snapshots/")
	maven(url = "https://repo.spring.io/milestone")
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation ("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("com.google.appengine:appengine-api-1.0-sdk:+")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test"){
		exclude("junit")
	}

	// junit5
	testImplementation("org.junit.jupiter:junit-jupiter-api")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

	// doma2
	runtimeOnly("com.h2database:h2:1.4.191")
	kapt("org.seasar.doma:doma-processor:2.48.0")
	implementation("org.seasar.doma:doma-core:2.48.0")
	implementation("org.seasar.doma.boot:doma-spring-boot-starter:1.5.0")
}
// GCP GAEデプロイ用設定
appengine {
	deploy {
		// デプロイ先の Google Cloud Project ID
		projectId = "individual-development"
		// デプロイによって反映される Web アプリのバージョン
		version = "GCLOUD_CONFIG"
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
