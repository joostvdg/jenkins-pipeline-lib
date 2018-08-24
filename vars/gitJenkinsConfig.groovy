def call() {
    sh 'git checkout master'
    sh 'git config --global credential.helper store'
    //sh 'git config --global '
}