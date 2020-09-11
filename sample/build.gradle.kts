plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
}

android {
    setCompileSdkVersion(Versions.compileSdk)

    defaultConfig {
        applicationId = "io.github.raggedycoder.coroutine.preference.app"
        setMinSdkVersion(Versions.minSdk)
        setTargetSdkVersion(Versions.targetSdk)
        versionCode = 1
    }
    sourceSets {
        getByName("main") {
            java.srcDir("src/main/kotlin")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(project(":coroutine-preference"))
    implementation(Libraries.appCompat)
    implementation(Libraries.materialDesign)
    implementation(Libraries.androidAnnotations)
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.kotlinCoroutines)
    implementation(Libraries.kotlinCoroutinesAndroid)
}
