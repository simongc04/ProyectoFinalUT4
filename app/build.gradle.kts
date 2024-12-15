plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")

}

android {
    namespace = "com.simon.proyectofinalut4"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.simon.proyectofinalut4"
        minSdk = 35
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation (libs.material)

    implementation(libs.ui) // Para la UI de Compose
    implementation(libs.material3) // Para Material3
    implementation(libs.ui.tooling.preview) // Para la vista previa de Compose
    implementation(libs.androidx.foundation) // Para los componentes base de Compose
    implementation(libs.androidx.material) // Material Design de Compose
    implementation(libs.androidx.runtime.livedata) // Para usar LiveData con Compose
    implementation(libs.androidx.lifecycle.runtime.ktx) // Para soporte con Lifecycle
    implementation(libs.androidx.lifecycle.viewmodel.compose) // Para usar ViewModel con Compose
    implementation(libs.androidx.activity.compose.v172) // Para usar Composables en Activities

    // Room Database
    ksp (libs.androidx.room.compiler.v250) // Para el compilador de Room (KAPT)
    implementation (libs.androidx.room.runtime.v260) // o la versión más reciente
    implementation (libs.kotlinx.coroutines.android.v171)


    // Coroutines
    implementation(libs.kotlinx.coroutines.android) // Para corutinas en Android

    // Para las vistas previas en el Android Studio
    debugImplementation(libs.ui.tooling)


    implementation (libs.material3)
    implementation (libs.androidx.navigation.compose.v253)
    implementation (libs.kotlin.stdlib)

    implementation (libs.androidx.navigation.compose.v260)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.room.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}