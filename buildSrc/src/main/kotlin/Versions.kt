object Versions {
    val kotlin = "1.4.0"
    val coroutines = "1.3.9"
    val minSdk = 21
    val targetSdk = 29
    val compileSdk = 29

    val gson = "2.8.0"

    // Test library versions
    val junit5 = "5.6.2"
    val mockito = "3.5.7"
    val mockitoKotlin = "2.2.0"
}


object Libraries {
    val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    val kotlinCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"

    val gson = "com.google.code.gson:gson:${Versions.gson}"

}

object TestLibraries {
    val junit5 = "org.junit.jupiter:junit-jupiter-api:${Versions.junit5}"

    val mockitoCore = "org.mockito:mockito-core:${Versions.mockito}"
    val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlin}"

    val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
}
