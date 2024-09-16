plugins {
    distribution
    id ("com.palantir.docker")
}

description = """"""

distributions {
    main {
        contents {
            from ("sandbox") {
                exclude ("**/*.sh")
            }
            from ("sandbox") {
                include ("**/*.sh")
                fileMode = "0755".toInt (8)
            }
        }
    }
}

docker {
    name = "encodeering/concourse-dind:$version"

    dependsOn              (tasks.distTar.get ())
    files (file ("static"), tasks.distTar)
    pull  (true)
}

tasks {
    val version by register<Task> ("dockerVersion") {
        group =    dockerPrepare.get ().group
        dependsOn (dockerPrepare)
        doLast {
            dockerPrepare.get().outputs.files.singleFile.resolve ("version.txt").writeText ("$version")
        }
    }

    dockerPrepare.configure { finalizedBy (version) }
}
