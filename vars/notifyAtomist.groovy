import groovy.json.JsonOutput
import com.github.joostvdg.atomist.Atomist

/*
 * Notify the Atomist services about the status of a build based from a
 * git repository.
 */
def call(buildStatus, buildPhase="FINALIZED",
                  endpoint="https://webhook.atomist.com/atomist/jenkins") {

    Atomist atomist = new Atomist(this)

    def payload = JsonOutput.toJson([
            name: env.JOB_NAME,
            duration: currentBuild.duration,
            build      : [
                    number: env.BUILD_NUMBER,
                    phase: buildPhase,
                    status: buildStatus,
                    full_url: env.BUILD_URL,
                    scm: atomist.getSCMInformation()
            ]
    ])

    sh "curl --silent -XPOST -H 'Content-Type: application/json' -d '${payload}' ${endpoint}"
}