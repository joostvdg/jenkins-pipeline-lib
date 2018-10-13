def call(String gitChangeSet, String changeRoot) {
    return parse(gitChangeSet, changeRoot)
}

@NonCPS
String[] parse(String gitChangeSet, String changeRoot) {
    if (gitChangeSet == null || gitChangeSet.trim().equals("")) {
        return new String[0];
    }

    String[] changeSetFolders = new String[1];
    int changeSetFolderCount = 0;
    String[] changeSetItems = gitChangeSet.split("\n");
    for (int i = 0; i < changeSetItems.length; i++) {
        String changeSetItem = changeSetItems[i];
        if (!changeSetItem.startsWith(changeRoot)) {
            continue; // it's not within scope
        }
        changeSetItem = changeSetItem.replace(changeRoot, "");
        if (!changeSetItem.contains("/")) {
            continue; // it's likely it was a change to the folder's permissions, no file
        }
        changeSetItem = changeSetItem.substring(0, changeSetItem.indexOf("/"));
        if (changeSetFolders.length < changeSetFolderCount + 1) {
            changeSetFolders = new String[changeSetFolderCount + 1];
        }
        changeSetFolders[changeSetFolderCount] = changeSetItem;
        changeSetFolderCount++;
    }

    return changeSetFolders;
}