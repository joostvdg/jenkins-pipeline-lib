// Requires: https://wiki.jenkins.io/display/JENKINS/Pipeline+Utility+Steps+Plugin

def call(String pomFile) {
    def version = readVersionFromPom()
    echo "[INFO] Maven pom version: ${version}"
    return version
}

@NonCPS
def readVersionFromPom(String pomFile) {
    def pom = readMavenPom file: pomFile
    def version = pom.version
    version = version.replace("-SNAPSHOT", "")
    return version
}