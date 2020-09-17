val kotlinVersion = "1.4.10"

plugins {
    `java-gradle-plugin`
    id("org.gradle.kotlin.kotlin-dsl") version "1.3.6"
}


buildscript {
    val kotlinVersion = "1.4.10"

    repositories {
        jcenter()
        mavenCentral()
        google()
        maven { url = uri("https://jitpack.io") }
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
}

gradlePlugin {
    repositories {
        google()
        jcenter()
        gradlePluginPortal()
        maven { url = uri("https://jitpack.io") }
    }
    dependencies {
        implementation("com.android.tools.build:gradle:4.0.1")
        implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.4.0")
        implementation("com.gradle.publish:plugin-publish-plugin:0.12.0")
        implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}
