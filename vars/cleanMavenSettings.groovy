def call() {
    if (fileExists('jenkins-settings.xml')) {
        if (isUnix()) {
            // https://unix.stackexchange.com/questions/118217/chmod-silent-mode-how-force-exit-code-0-in-spite-of-error
            sh 'rm jenkins-settings.xml || true'
        } else {
            // we assume windows
            bat 'del "jenkins-settings.xml"'
        }
    } else {
        echo '[INFO] jenkins-settings.xml does not exist, are you in the correct folder?'
    }
}
