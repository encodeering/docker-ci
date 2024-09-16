pluginManagement {
    repositories {
        gradlePluginPortal ()
    }
    plugins {
        id ("me.qoomon.git-versioning") version "6.4.4"
        id ("com.palantir.docker") version "0.36.0"
    }
}

rootProject.name = "docker-ci"
include ("modules:ci")
