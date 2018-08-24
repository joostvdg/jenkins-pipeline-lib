def call(String tagName) {
    assert tagName: 'I need tagName to be valid'
    def createTagCommand = """
    git add --all
    git commit -m "release \$(VERSION)" --allow-empty
    git tag -fa ${tagName} -m "Jenkins created version ${tagName}"
    git push origin v\$(VERSION)
    """
    if (isUnix()) {
        sh createTagCommand
    } else {
        bat createTagCommand
    }
}
