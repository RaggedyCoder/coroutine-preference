/*
 * Copyright 2020 RaggedyCoder.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import org.jetbrains.dokka.gradle.DokkaTask

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

    testBuildType = "debug"

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
            isTestCoverageEnabled = true
        }
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

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<DokkaTask>().configureEach {
    dokkaSourceSets {
        named("main") {
            noAndroidSdkLink.set(false)
        }
    }
}

afterEvaluate {
    tasks.withType<Test> {
        testLogging {
            events("skipped", "failed")
        }
    }
    val testKey = "coroutinePreferenceTests"
    val task = rootProject.tasks.create(testKey, TestReport::class)
    task.configure(closureOf<TestReport> {
        group = "Verification"
        description = "All Unit Tests"

        val tasks = project.tasks
        val testTask = tasks.find { it.name == "testDebugUnitTest" }

        testTask?.let {
            reportOn(testTask)
        }

        destinationDir = file("${rootProject.buildDir}/reports/tests")
    })
}

dependencies {
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.kotlinCoroutines)
    implementation(Libraries.gson)

    testImplementation(TestLibraries.testCore)
    testImplementation(TestLibraries.junit4)
    testImplementation(TestLibraries.junit5)
    testRuntimeOnly(TestLibraries.junit5Engine)
    testRuntimeOnly(TestLibraries.junit5VintageEngine)
    testImplementation(TestLibraries.junit5Params)
    testImplementation(TestLibraries.mockitoCore)
    testImplementation(TestLibraries.mockitoKotlin)
    testImplementation(TestLibraries.coroutinesTest)
    testImplementation(TestLibraries.roboelectric)
    testImplementation(TestLibraries.roboelectricJUnit)
}

tasks {
    withType(Javadoc::class.java) {
        enabled = false
        source = android.sourceSets.getByName("main").java.sourceFiles
        classpath += project.files(android.bootClasspath.joinToString(File.pathSeparator))
    }
}

afterEvaluate {
    apply(plugin = "publishable-library")
}
