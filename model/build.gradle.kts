plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    jvm()
    sourceSets {
        commonMain.dependencies {
            api(libs.kotlinx.collections.immutable)
        }
    }
}
