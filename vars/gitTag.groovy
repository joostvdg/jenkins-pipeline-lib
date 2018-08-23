def call(String tagName) {
    assert tagName: 'I need tagName to be valid'
    def createTagCommand = "git tag -a ${tagName} -m \"Jenkins created version ${tagName}\""
    if (isUnix()) {
        sh createTagCommand
    } else {
        bat createTagCommand
    }
}
