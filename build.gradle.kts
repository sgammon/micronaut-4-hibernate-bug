import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.targets

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.20"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.8.20"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.8.20"
    id("com.google.devtools.ksp") version "1.8.20-1.0.10"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.micronaut.application") version "4.0.0-M3"
    id("io.micronaut.test-resources") version "4.0.0-M3"
}

version = "0.1"
group = "com.example"

val micronautLatest: String by properties

val micronautPrefixes = sortedSetOf(
    "io.micronaut",
    "io.micronaut.data",
)
val micronautOverrides = sortedSetOf(
    "micronaut-aop",
    "micronaut-graal",
    "micronaut-http-client",
    "micronaut-http-server-netty",
    "micronaut-http-validation",
    "micronaut-inject",
    "micronaut-inject-java",
    "micronaut-inject-kotlin",
    "micronaut-jackson-databind",
    "micronaut-management",
    "micronaut-runtime",
    "micronaut-runtime-osx",
    "micronaut-data-hibernate-jpa",
    "micronaut-data-jdbc",
    "micronaut-data-model",
    "micronaut-data-processor",
    "micronaut-data-runtime",
)

val kotlinVersion: String by properties

repositories {
    maven("https://maven.pkg.st")
}

dependencies {
    ksp("io.micronaut.data:micronaut-data-processor")
    ksp("io.micronaut.serde:micronaut-serde-processor")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("io.micronaut.data:micronaut-data-hibernate-jpa")
    implementation("io.micronaut.kotlin:micronaut-kotlin-extension-functions")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.micronaut.sql:micronaut-hibernate-jpa")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    runtimeOnly("mysql:mysql-connector-java")
    runtimeOnly("org.yaml:snakeyaml")
    testImplementation("io.micronaut:micronaut-http-client")
}


application {
    mainClass.set("com.example.ApplicationKt")
}

java {
    sourceCompatibility = JavaVersion.toVersion("17")
}

kotlin {
    jvmToolchain(17)

    targets.forEach {
        it.compilations.all {
            kotlinOptions.apiVersion = "1.8"
            kotlinOptions.languageVersion = "1.8"
            (kotlinOptions as? KotlinJvmOptions)?.let { jvmOptions ->
                jvmOptions.javaParameters = true
                jvmOptions.jvmTarget = "17"
            }
        }
    }
}

tasks {
    compileKotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }
    compileTestKotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }
}

graalvmNative.toolchainDetection.set(false)
val micronautVersion: String by properties

micronaut {
    version(micronautVersion)
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.example.*")
    }
}

configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.group == "io.micronaut" && micronautOverrides.contains(requested.name)) {
            useVersion(micronautLatest)
            because("force latest micronaut")
        } else if (micronautPrefixes.contains(requested.group)) {
            useVersion(micronautLatest)
            because("force latest micronaut")
        }
    }
}
