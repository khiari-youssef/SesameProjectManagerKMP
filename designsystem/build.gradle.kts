plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "designsystem"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies{
            implementation(libs.compose.ui)
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.compose.material3)
            implementation(libs.compose.foundation)
            implementation(libs.androidx.splashscreen)
            implementation(libs.compose.ui.tooling)
            implementation(libs.compose.navigation)
            implementation(libs.compose.lottie)
            implementation(libs.compose.animation.core)
            implementation(libs.compose.animation.graphics)
            implementation(libs.compose.constraintlayout)
            implementation(libs.compose.coil)
            implementation(libs.compose.material)
        }
    }
}

android {
    namespace = "tn.sesame.designsystem"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}
