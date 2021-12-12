import java.net.URI

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Libs.gradleTools)
        classpath(Libs.kotlinPlugin)
        classpath(Libs.navigationSafeArgsPlugin)
        classpath(Libs.googleServices)
        classpath(Libs.hiltPlugin)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.0")
        classpath(Plugins.sqlDelight)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}