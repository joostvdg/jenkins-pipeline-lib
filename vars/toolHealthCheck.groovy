def call (String url) {
    def responseCode = sh returnStdout: true, script: 'curl -sL -w "%{http_code}" localhost:8080 -o /dev/null --max-time 15'
    return responseCode
}