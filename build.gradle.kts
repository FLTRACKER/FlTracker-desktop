import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.contracts.model.structure.UNKNOWN_COMPUTATION.type
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.storage.CacheResetOnProcessCanceled.enabled

plugins {
    kotlin("jvm") version "1.5.31"
    id("org.jetbrains.compose") version "1.0.0"
}

group = "com.fltracker"
version = "1.0"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(files("/lib/jna-platform-5.10.0.jar"))
    implementation(files("/lib/jna-platform-jpms-5.10.0.jar"))
    implementation(files("/lib/webp-imageio-core-0.1.3.jar"))

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    implementation("net.java.dev.jna:jna:5.5.0")
    testImplementation(kotlin("test"))
    implementation(compose.desktop.currentOs)
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

//kotlin {
//    macosX64("native") { // on macOS
//        linuxX64("native") // on Linux
//        mingwX64("native") // on Windows
//        binaries {
//            executable()
//        }
//    }
//}

tasks.withType<Jar> {
    manifest {
        attributes ["Main-Class"] = "MainKt"
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "FLTracker"
            packageVersion = "1.0.0"
        }
    }
}