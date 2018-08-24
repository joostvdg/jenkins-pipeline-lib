def call(String tagName) {
    assert tagName: 'I need tagName to be valid'
    def createTagCommand = """
    git add --all
    git commit -m "release ${tagName}" --allow-empty
    git tag -fa ${tagName} -m "Jenkins created version ${tagName}"
    git push origin ${tagName}
    """
    if (isUnix()) {
        sh createTagCommand
    } else {
        bat createTagCommand
    }
}
