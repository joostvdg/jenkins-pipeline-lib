def call (String url) {
    def responseCode = sh returnStdout: true, script: "curl -sL -w \"%{http_code}\" ${url} -o /dev/null --max-time 15"
    echo "[INFO] reponse ${responseCode}"
    return responseCode
}