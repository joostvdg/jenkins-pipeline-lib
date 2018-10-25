def call () {
    def lintResult = sh returnStdout: true, script: 'hadolint Dockerfile'
    if (lintResult.trim() == '') {
        println 'Lint finished with no errors'
    } else {
        println 'Error found in Lint'
        println "${lintResult}"
        currentBuild.result = 'UNSTABLE'
    }
}