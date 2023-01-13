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

//    implementation(project(Depends.Module.permissions))

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))


}