def call(currentVersion) {
    def versionMask = "v${currentVersion}.*"
    def gitTags = retrieveGitTagsForVersion(versionMask)
    def tagsArray = gitTags.split('\n')
    echo "[INFO] determining new version from current version ${currentVersion} and tags: $gitTags"
    def newVersion = getNewVersion(tagsArray, currentVersion)
    echo "[INFO] new version ${newVersion}"
    return newVersion
}

/**
 * Retrieve a (array)list of git tags for a version in the current git checkout.
 *
 * @param versionMask the version to filter the tags for, e.g. v1.4.*
 * @return the output from the git command (git tag -l ...), which results in a multi-line response with the tags
 */
String retrieveGitTagsForVersion(versionMask) {
    String command = "git tag -l \"${versionMask}\""
    String response = ""
    if (isUnix()) {
        response = sh returnStdout: true, script: command
    } else {
        response = bat returnStdout: true, script: command
    }
    return response
}

/**
 * Will construct a new version with an updated Patch number.
 * <br/>
 * Following the <a href="http://semver.org/">SemVer</a> versioning scheme -> major.minor.patch.
 *  <br/>
 *  Assumptions:
 *  <br/>
 *  <ul>
 *      <li>currentVersion is SemVer Major.Minor</li>
 *      <li>listOfExistingVersions is array (Array or ArrayList) of SemVer Major.Minor.Patch strings</li>
 *      <li>The existing versions and currentVersion share the same Major.Minor</li>
 *      <li>listOfExistingVersions can be empty, should not be null</li>
 *  </ul>
 *  <br/>
 *  The goal is set the current version to patch number that increments the current version with a new max patch number.
 *
 * @param listOfExistingVersions array(List) of SemVer version strings
 * @param currentVersion the current version that needs to be incremented
 * @return
 */
@NonCPS
def getNewVersion(def listOfExistingVersions, String currentVersion) {
    assert listOfExistingVersions: "We need listOfExistingVersions to be valid"
    assert currentVersion: "We need currentVersion to be valid"

    List<Integer> filteredVersions = new ArrayList<Integer>();
    for (int i =0; i < listOfExistingVersions.size(); i++) {
        String raw = listOfExistingVersions[i]
        def versionElements = raw.split('\\.')
        if (versionElements.size() > 2) {
            def patchRaw = versionElements[2] // major.minor.patch -> v1.4.*
            if (patchRaw.matches('\\d+')) {
                filteredVersions.add(new Integer(patchRaw))
            } else if (patchRaw.contains('-')) {
                int index = patchRaw.indexOf('-')
                def patch = patchRaw.substring(0, index)
                filteredVersions.add(new Integer(patch))
            }
        }
    }
    Collections.sort(filteredVersions)
    int patchPrevious = 0
    int patchNext = patchPrevious
    if (filteredVersions.isEmpty()) {
        echo "We found no existing tag, so version will be .0"
    } else {
        patchPrevious = filteredVersions.get(filteredVersions.size() - 1)
        patchNext = patchPrevious + 1
    }
    return currentVersion + "." + patchNext
}