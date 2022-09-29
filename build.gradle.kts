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
        classpath("com.android.tools.build:gradle:7.2.2")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}