// Top-level build file where you can add configuration options common to all sub-projects/modules.


buildscript {
    repositories {
        jcenter()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
    }
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
    }
}


allprojects {
    repositories {
        mavenCentral()
        jcenter()
        google()
        maven { url = uri("https://jitpack.io") }
    }
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}
