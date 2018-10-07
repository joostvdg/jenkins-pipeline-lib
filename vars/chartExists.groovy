def call (String url, String expectedCode, string credentialsId, boolean insecure) {
    def secureInfo = ""
    if (insecure) {
        secureInfo = "--insecure"
    }
    def responseCode = "000"
    try {
        withCredentials([usernamePassword(
                credentialsId: credentialsId,
                usernameVariable: "USER",
                passwordVariable: "PASS"
        )]) {
            responseCode = sh(returnStdout: true, script: "curl ${secureInfo} -u $USER:$PASS -sL -w \"%{http_code}\" ${url} -o /dev/null --max-time 15").trim()
        }
    } catch(err) {
        echo "[WARN] caught ${err}"
        responseCode = '520' // CloudFlare's 520 - Unknown Error: https://en.wikipedia.org/wiki/List_of_HTTP_status_codes
    } finally {
        echo "[INFO] reponse ${responseCode}"
    }
    return responseCode == expectedCode
}