def call(pomFile) {
    def currentVersion = mavenNextReleaseVersion(pomFile)
    echo "[INFO] Found version=${currentVersion}"
    return gitNextSemverTag('currentVersion')
}