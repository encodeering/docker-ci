plugins {
    id ("me.qoomon.git-versioning")
}

allprojects {

    group = "com.encodeering"

}

gitVersioning.apply {
    refs {
        considerTagsOnBranches = true

        branch (".+") {
            version = "\${describe.tag.version.major}.\${describe.tag.version.minor}.\${describe.tag.version.patch.next}-unstable"
        }
        tag ("(?<version>[0-9.]+)") {
            version = "\${ref.version}"
        }
    }

    rev {
        version = "unstable"
    }
}
