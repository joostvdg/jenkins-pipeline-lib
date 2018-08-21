def call (String credentialsId) {
    assert credentialsId: "CredentialsId must be given as parameter."

    withCredentials([usernamePassword(credentialsId: 'github', passwordVariable: 'PSS', usernameVariable: 'USR')]) {
        if ("${env.PSS}".startsWith('{') && "${env.PSS}".endsWith('}')) {
            // we're safe
            echo '[WARN] Encrypted maven password detected, can this agent decrypt it?'
        } else {
            echo '[WARN] Maven password not encrypted, please make sure to use the clean function (cleanMavenSettings)'
        }
        writeFile encoding: 'UTF-8', file: 'jenkins-settings.xml', text: '''<settings>
            <servers>
                <server>
                    <id>artifactory</id>
                    <username>${env.USR}</username>
                    <password>${env.PSS}</password>
                </server>
            </servers>
        </settings>'''
        echo '[INFO] Wrote settings to jenkins-settings.xml'
    }


}
