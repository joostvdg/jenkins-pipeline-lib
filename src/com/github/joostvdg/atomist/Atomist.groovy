package com.github.joostvdg.atomist

import java.io.Serializable

class Atomist implements Serializable {
    def steps

    Atomist(steps) {this.steps = steps}

    /*
     * Retrieve current SCM information from local checkout
     */
    def getSCMInformation() {
        def gitRemoteUrl = steps.sh(returnStdout: true, script: 'git config --get remote.origin.url').trim()
        def gitCommitSha = steps.sh(returnStdout: true, script: 'git rev-parse HEAD').trim()
        def gitBranchName = steps.sh(returnStdout: true, script: 'git name-rev --always --name-only HEAD').trim().replace('remotes/origin/', '')

        return [
                url: gitRemoteUrl,
                branch: gitBranchName,
                commit: gitCommitSha
        ]
    }

}
