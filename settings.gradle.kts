pluginManagement {
    repositories {
        gradlePluginPortal ()
    }
    plugins {
        id ("me.qoomon.git-versioning") version "6.4.4"
    }
}

rootProject.name = "docker-ci"
include ("modules:ci")
