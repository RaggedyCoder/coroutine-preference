plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {

    setCompileSdkVersion(Versions.compileSdk)

    defaultConfig {
        setMinSdkVersion(Versions.minSdk)
        setTargetSdkVersion(Versions.targetSdk)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    sourceSets {
        getByName("main") {
            java.srcDir("src/main/kotlin")
        }

        getByName("test") {
            java.srcDir("src/test/kotlin")
        }
    }
}

dependencies {
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.kotlinCoroutines)
    implementation(Libraries.gson)

    testImplementation(TestLibraries.junit5)
    testImplementation(TestLibraries.mockitoCore)
    testImplementation(TestLibraries.mockitoKotlin)
    testImplementation(TestLibraries.coroutinesTest)
}

apply(rootProject.file("gradle/gradle-mvn-push.gradle"))
