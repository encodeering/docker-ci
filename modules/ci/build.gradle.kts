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
