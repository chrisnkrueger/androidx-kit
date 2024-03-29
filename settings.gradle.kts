pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}


plugins {
    id("com.gradle.enterprise") version "3.12.5"
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        publishAlways()
    }
}

rootProject.name = "androidx-kit"

include(":androidx-kit")
