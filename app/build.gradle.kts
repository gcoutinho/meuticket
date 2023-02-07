import Room.getRoom
import Room.getRoomCompiler
import Room.getRoomKtx

android {
    packagingOptions {
        exclude("META-INF/*.kotlin_module")
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    defaultConfig {
        vectorDrawables.useSupportLibrary = true
    }

    packagingOptions {
        exclude("META-INF/*.kotlin_module")
    }
}



dependencies {

    implementation("androidx.work:work-runtime-ktx:2.7.1")
//    annotationProcessor("androidx.room:room-compiler:$room_version")

    // To use Kotlin annotation processing tool (kapt)
    implementation(Room.getRoom(rootProject))
    kapt(Room.getRoomCompiler(rootProject))
    implementation(Room.getRoomKtx(rootProject))

    safraImplementation(files("libs/IntegracaoSafra-v2.0.aar"))

    implementation(Support.getCoreKtxVersion(rootProject))
    implementation(Support.getMultidexVersion(rootProject))
    implementation(Support.getSupportV7Version(rootProject))
    implementation(Support.getSupportConstraintLayoutVersion(rootProject))
    implementation(Support.getSupportCardViewVersion(rootProject))
    implementation(Support.getSupportDesignVersion(rootProject))
    implementation(Support.getSupportAnimationVersion(rootProject))
    implementation(Support.getAndroidXFragment(rootProject))

    kapt(Dagger.getDaggerCompilerVersion(rootProject))
    kapt(Dagger.getDaggerProcessorVersion(rootProject))
    implementation(Dagger.getDaggerCoreVersion(rootProject))
    implementation(Dagger.getDaggerAndroidVersion(rootProject))
    implementation(Dagger.getDaggerSupportVersion(rootProject))


    implementation(Kotlin.getKotlinStdlibVersion(rootProject))
    implementation(Kotlin.getKotlinReflectVersion(rootProject))
    implementation(OkHttp3.getCoreVersion(rootProject))
    implementation(OkHttp3.getinterceptorVersion(rootProject))
    implementation(Retrofit.getRetrofitCoreVersion(rootProject))
    implementation(Retrofit.getRetrofitMoshiVersion(rootProject))
    implementation(Retrofit.getRetrofitMoshiConverterVersion(rootProject))
    implementation(Retrofit.getRetrofitRxAdapterVersion(rootProject))
    implementation(Picasso.getPicassoVersion(rootProject))

    val coroutinesVersion = "1.3.0"
    //Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

    ViewModel.setup(rootProject).forEach { implementation(it) }

}