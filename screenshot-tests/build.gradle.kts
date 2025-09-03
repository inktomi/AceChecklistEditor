plugins {
    alias(libs.plugins.androidLibrary)
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.screenshot)
}

android {
    namespace = "com.inktomi.ace.screenshot.tests"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    experimentalProperties["android.experimental.enableScreenshotTest"] = true
}

dependencies {
    implementation(project(":composeApp"))
    implementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    screenshotTestImplementation(libs.screenshot.validation.api)
    screenshotTestImplementation(libs.junit)
    screenshotTestImplementation(libs.androidx.ui.test.junit4)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(23))
    }
}
