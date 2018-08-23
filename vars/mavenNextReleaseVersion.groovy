// Requires: https://wiki.jenkins.io/display/JENKINS/Pipeline+Utility+Steps+Plugin

def call(String pomFile) {
    def pom = readMavenPom file: pomFile
    String version = "${pom.version}"
    version = version.replace("-SNAPSHOT", "")
    echo "[INFO] Maven pom version: ${version}"
    return version
}
