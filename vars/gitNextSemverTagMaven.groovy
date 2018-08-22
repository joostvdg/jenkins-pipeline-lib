def call() {
    def currentVersion = mavenNextReleaseVersion()
    return gitNextSemverTag('currentVersion')
}