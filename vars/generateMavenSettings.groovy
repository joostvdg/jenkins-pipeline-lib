def call (String credentialsId, String serverId) {
    assert credentialsId: "CredentialsId is not set. It must be given as first parameter. (string, Jenkins credentialsId)"
    assert serverId: "ServerId is not set. It must be given as second parameter and should match your distributionManagement.id."

    withCredentials([usernamePassword(credentialsId: credentialsId, passwordVariable: 'PSS', usernameVariable: 'USR')]) {
        if ("${env.PSS}".startsWith('{') && "${env.PSS}".endsWith('}')) {
            // we're safe
            echo '[WARN] Encrypted maven password detected, can this agent decrypt it?'
        } else {
            echo '[WARN] Maven password not encrypted, please make sure to use the clean function (cleanMavenSettings)'
        }
        writeFile encoding: 'UTF-8', file: 'jenkins-settings.xml', text: """<settings>
            <servers>
                <server>
                    <id>${serverId}</id>
                    <username>${env.USR}</username>
                    <password>${env.PSS}</password>
                </server>
            </servers>
        </settings>"""
        echo '[INFO] Wrote settings to jenkins-settings.xml'
    }


}
