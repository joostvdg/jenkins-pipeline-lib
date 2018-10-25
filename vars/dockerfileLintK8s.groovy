def call (def fileName = 'Dockerfile') {
    def lintResult = sh returnStdout: true, script: "hadolint ${fileName}"
    if (lintResult.trim() == '') {
        println 'Lint finished with no errors'
    } else {
        println 'Error found in Lint'
        println "${lintResult}"
        currentBuild.result = 'UNSTABLE'
    }
}