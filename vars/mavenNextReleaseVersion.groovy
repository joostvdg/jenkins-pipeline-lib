// Requires: https://wiki.jenkins.io/display/JENKINS/Pipeline+Utility+Steps+Plugin

def call(String pomFile) {
    return readVersionFromPom()
}

@NonCPS
def readVersionFromPom(String pomFile) {
    def pom = readMavenPom file: pomFile
    return pom.version.replace("-SNAPSHOT", "")
}