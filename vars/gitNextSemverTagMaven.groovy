def call(pomFile) {
    def currentVersion = mavenNextReleaseVersion(pomFile)
    return gitNextSemverTag(currentVersion)
}