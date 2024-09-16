plugins {
    distribution
}

description = """"""

distributions {
    main {
        contents {
            from ("src") {
                exclude ("**/*.sh")
            }
            from ("src") {
                include ("**/*.sh")
                fileMode = "0755".toInt (8)
            }
        }
    }
}
