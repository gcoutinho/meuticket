import org.gradle.api.Project
import org.gradle.kotlin.dsl.extra

const val kotlinVersion = "1.3.21"


object Android {
    const val gradle_plugin = "com.android.tools.build:gradle:${Versions.gradle_plugin_version}"
    const val google_services = "com.google.gms:google-services:${Versions.google_services_version}"
    const val gradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin_version}"
}

object BuildPlugins {

    object Versions {
        const val buildToolsVersion = "3.3.1"
    }

    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.buildToolsVersion}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    const val androidApplication = "com.android.application"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinAndroidExtensions = "kotlin-android-extensions"

}

object AndroidSdk {
    const val min = 15
    const val compile = 28
    const val target = compile
}

object Kotlin {
    fun getKotlinStdlibVersion(ext: Project) = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${ext.extra.get(KOTLIN_VERSION)}"
    fun getKotlinReflectVersion(ext: Project) = "org.jetbrains.kotlin:kotlin-reflect:${ext.extra.get(KOTLIN_VERSION)}"

    const val KOTLIN_VERSION = "kotlin_version"
}

object Libraries {
    private object Versions {
        const val jetpack = "1.0.0-beta01"
        const val constraintLayout = "1.1.2"
        const val ktx = "1.1.0-alpha05"
    }

    const val kotlinStdLib     = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
    const val appCompat        = "androidx.appcompat:appcompat:${Versions.jetpack}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val ktxCore          = "androidx.core:core-ktx:${Versions.ktx}"
}

object TestLibraries {
    private object Versions {
        const val junit4 = "4.12"
        const val testRunner = "1.1.0-alpha4"
        const val espresso = "3.1.0-alpha4"
    }
    const val junit4     = "junit:junit:${Versions.junit4}"
    const val testRunner = "androidx.test:runner:${Versions.testRunner}"
    const val espresso   = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}

object Support {
    fun getCoreKtxVersion(ext: Project) = "androidx.core:core-ktx:${ext.extra.get(ANDROIDX_CORE_VERSION)}"
    fun getMultidexVersion(ext: Project) = "androidx.multidex:multidex:${ext.extra.get(MULTIDEX_VERSION)}"
    fun getSupportV7Version(ext: Project) = "androidx.appcompat:appcompat:${ext.extra.get(SUPPORT_VERSION)}"
    fun getSupportConstraintLayoutVersion(ext: Project) = "androidx.constraintlayout:constraintlayout:${ext.extra.get(CONSTRAINT_VERSION)}"
    fun getSupportCardViewVersion(ext: Project) = "androidx.cardview:cardview:${ext.extra.get(CARDVIEW_VERSION)}"
    fun getSupportDesignVersion(ext: Project) = "com.google.android.material:material:${ext.extra.get(MATERIAL_VERSION)}"
    fun getSupportAnimationVersion(ext: Project) = "androidx.dynamicanimation:dynamicanimation:${ext.extra.get(DYNAMICANIMATION_VERSION)}"
    fun getAndroidXFragment(ext: Project) = "androidx.fragment:fragment:${ext.extra.get(FRAGMENT_VERSION)}"


    const val FRAGMENT_VERSION = "fragment_ktx_version"
    const val SUPPORT_VERSION = "support_version"
    const val CONSTRAINT_VERSION = "constraint_motion_version"
    const val CARDVIEW_VERSION = "cardview_version"
    const val MATERIAL_VERSION = "material_version"
    const val DYNAMICANIMATION_VERSION = "dynamicanimation_version"
    const val MULTIDEX_VERSION = "multidex_version"
    const val ANDROIDX_CORE_VERSION = "androidx_core_ktx"
}

object ViewModel {
    fun Project.getLifecycleExtensionsVersion() =
        "androidx.lifecycle:lifecycle-extensions:${extra.get(LIFECYCLE_VERSION) as String}"

    fun Project.getLifecycleConvertRxToLiveDataVersion() =
        "androidx.lifecycle:lifecycle-reactivestreams:${extra.get(LIFECYCLE_VERSION) as String}"

    fun Project.getLifecycleLiveDataKtxVersion() =
        "androidx.lifecycle:lifecycle-livedata-ktx:${extra.get(LIFECYCLE_VERSION) as String}"

    fun Project.getLifecycleViewModelKtxVersion() =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${extra.get(LIFECYCLE_VERSION) as String}"

    private const val LIFECYCLE_VERSION = "lifecycle_extension"

    fun setup(ext: Project): Array<String> {
        return with(ext) {
            arrayOf(
                getLifecycleExtensionsVersion(),
                getLifecycleConvertRxToLiveDataVersion(),
                getLifecycleLiveDataKtxVersion(),
                getLifecycleViewModelKtxVersion()
            )
        }
    }
}

object Dagger {
    fun getDaggerCoreVersion(ext: Project) = "com.google.dagger:dagger:${ext.extra.get(DAGGER_VERSION)}"
    fun getDaggerAndroidVersion(ext: Project) = "com.google.dagger:dagger-android:${ext.extra.get(DAGGER_VERSION)}"
    fun getDaggerSupportVersion(ext: Project) = "com.google.dagger:dagger-android-support:${ext.extra.get(DAGGER_VERSION)}"
    fun getDaggerCompilerVersion(ext: Project) = "com.google.dagger:dagger-compiler:${ext.extra.get(DAGGER_VERSION)}"
    fun getDaggerProcessorVersion(ext: Project) = "com.google.dagger:dagger-android-processor:${ext.extra.get(DAGGER_VERSION)}"

    private const val DAGGER_VERSION = "dagger_version"
}


object Retrofit {
    fun getRetrofitCoreVersion(ext: Project) = "com.squareup.retrofit2:retrofit:${ext.extra.get(RETROFIT_VERSION) as String}"
    fun getRetrofitMoshiVersion(ext: Project) = "com.squareup.moshi:moshi-kotlin:${ext.extra.get(MOSHI_VERSION) as String}"
    fun getRetrofitMoshiConverterVersion(ext: Project) = "com.squareup.retrofit2:converter-moshi:${ext.extra.get(RETROFIT_VERSION) as String}"
    fun getRetrofitRxAdapterVersion(ext: Project) = "com.jakewharton.retrofit:retrofit2-rxjava2-adapter:${ext.extra.get(RXADAPTER_VERSION) as String}"

    const val RETROFIT_VERSION = "retrofit_version"
    const val MOSHI_VERSION = "moshi_version"
    const val RXADAPTER_VERSION = "rxjava_adapter"
}

object Picasso {

    fun getPicassoVersion(ext: Project): String =
        "com.squareup.picasso:picasso:${ext.extra.get(PICASSO_VERSION) as String}"

    private const val PICASSO_VERSION = "picasso_version"
}

object OkHttp3 {
    fun getCoreVersion(ext: Project) = "com.squareup.okhttp3:okhttp:${ext.extra.get(OKHTTP_VERSION) as String}"
    fun getinterceptorVersion(ext: Project) = "com.squareup.okhttp3:logging-interceptor:${ext.extra.get(OKHTTP_VERSION) as String}"

    private const val OKHTTP_VERSION = "okhttp_version"
}