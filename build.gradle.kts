// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        maven("https://plugins.gradle.org/m2/")
        google()
        mavenCentral()

    }
    dependencies {
        classpath(Android.gradle)
        classpath(Android.gradle_plugin)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
    configureAndroid()
}

fun Project.configureAndroid() {
    val isAppModule = name == "app"

    if(isAppModule)
        configureAppAndroid()

    apply(plugin = "com.android.application")
    apply(plugin = "kotlin-android")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "kotlin-android-extensions")

    configure<com.android.build.gradle.BaseExtension> {
        compileSdkVersion(Versions.compileSdkVersion)

        defaultConfig {
            minSdkVersion(Versions.minSdkVersion)
            targetSdkVersion(Versions.targetSdkVersion)
            versionCode = Versions.versionCode
            versionName = Versions.versionName
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            vectorDrawables.useSupportLibrary = true
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions.jvmTarget = "1.8"
        }
        testOptions {
            unitTests.isIncludeAndroidResources = true
            unitTests.isReturnDefaultValues = true
        }
        sourceSets {
            getByName("main").java.srcDirs("src/main/kotlin")
            getByName("test").java.srcDirs("src/test/kotlin")
            getByName("androidTest").java.srcDirs("src/androidTest/kotlin")
        }
        dexOptions {
            preDexLibraries = true
            maxProcessCount = 8
        }
    }

    configure<org.jetbrains.kotlin.gradle.internal.AndroidExtensionsExtension> {
        isExperimental = true
        defaultCacheImplementation = org.jetbrains.kotlin.gradle.internal.CacheImplementation.SPARSE_ARRAY
    }
}

fun Project.configureAppAndroid() {
    apply(plugin = "com.android.application")

    configure<com.android.build.gradle.BaseExtension> {

        defaultConfig {
            applicationId = Config.Application.applicationId
        }

        buildTypes {
            // build types
            getByName("release") {
                isDebuggable = false
                isMinifyEnabled = false
                multiDexEnabled = true
                isShrinkResources = false
            }

            getByName("debug") {
                multiDexEnabled = true
                isDebuggable = true
            }
        }
    }
}

//tasks.register("clean").configure {
//    delete("build")
//}