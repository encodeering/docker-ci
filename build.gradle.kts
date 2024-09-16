plugins {
    id ("me.qoomon.git-versioning")
}

allprojects {

    group = "com.encodeering"

}

gitVersioning.apply {
    refs {
        considerTagsOnBranches = true

        tag ("(?<version>[0-9.]+)") {
            version = "\${ref.version}"
        }
        branch (".+") {
            version = "\${describe.tag.version.major}.\${describe.tag.version.minor}.\${describe.tag.version.patch.next}-unstable"
        }
    }

    rev {
        version = "unstable"
    }
}
