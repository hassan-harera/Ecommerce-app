plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("kotlin-android")
    id("org.jetbrains.kotlin.kapt")
}
apply(plugin = "androidx.navigation.safeargs")
apply(plugin = "dagger.hilt.android.plugin")
android {
    compileSdkVersion(DefaultConfig.compileSdk)
    buildToolsVersion(DefaultConfig.buildToolsVersion)

    defaultConfig {
        applicationId = (DefaultConfig.appId)
        versionCode = (Releases.versionCode)
        versionName = (Releases.versionName)
        targetSdkVersion(DefaultConfig.targetSdk)
        minSdkVersion(DefaultConfig.minSdk)

        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = ("com.harera.hyperpanda.HiltTestRunner")
    }
    buildFeatures {
        viewBinding = (true)
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
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    namespace = ("com.harera.hyperpanda")
}

dependencies {
    implementation(project(Navigation.user))
    implementation(project(Navigation.LOGIN))
    implementation(project(UiComponents.common))

    //hilt
    implementation(Libs.hiltAndroid)
    kapt(Libs.hiltAndroidCompiler)

    implementation(Libs.playServicesTasks)

    implementation(project(Data.repository))

    implementation(Libs.androidxCore)
    implementation(Libs.appcompat)
    implementation(Libs.activity)
    implementation(Libs.fragments)

    implementation(Libs.navigationUi)
    implementation(Libs.navigationFragment)

    implementation(Libs.materialDesign)
    implementation(Libs.circleImage)

    implementation(Libs.liveData)
    implementation(Libs.viewModels)
    kapt(Libs.lifeCycleCompiler)
    implementation(UI.SPLASH_API)
}
kapt {
    generateStubs = true
}