import java.util.Arrays

def call(String gitChangeSet, String changeRoot) {
    return parse(gitChangeSet, changeRoot)
}

@NonCPS
String[] parse(String gitChangeSet, String changeRoot) {
    if (gitChangeSet == null || gitChangeSet.trim().equals("")) {
        return new String[0];
    }

    String[] changeSetFolders = new String[256];
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
        boolean present = false;
        for (int k =0; k < changeSetFolders.length; k++) {
            if(changeSetFolders[k] != null && changeSetFolders[k].equals(changeSetItem)) {
                present = true; // has to be unique
            }
        }
        if (!present) {
            changeSetFolders[changeSetFolderCount] = changeSetItem;
            changeSetFolderCount++;
        }
    }

    changeSetFolders = Arrays.copyOf(changeSetFolders, changeSetFolderCount);

    return changeSetFolders;
}