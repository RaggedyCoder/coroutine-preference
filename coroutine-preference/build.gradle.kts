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

    testImplementation(TestLibraries.junit5)
    testRuntimeOnly(TestLibraries.junit5Engine)
    testRuntimeOnly(TestLibraries.junit5VintageEngine)
    testImplementation(TestLibraries.junit5Params)
    testImplementation(TestLibraries.mockitoCore)
    testImplementation(TestLibraries.mockitoKotlin)
    testImplementation(TestLibraries.coroutinesTest)
}

tasks {
    withType(Javadoc::class.java) {
        enabled = false
        source = android.sourceSets.getByName("main").java.sourceFiles
        classpath += project.files(android.bootClasspath.joinToString(File.pathSeparator))
    }
}

apply(rootProject.file("gradle/gradle-mvn-push.gradle"))
