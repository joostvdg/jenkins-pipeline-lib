def call(String url, credentialsId) {
    assert url: 'Url is not set (first param)'
    assert cerdentialsId: 'credentailsId is not set (second param)'
    assert url.contains('https://'): 'Url is not a https url, this only works with "https://....git" kind of urls'

    withCredentials([string(credentialsId: credentialsId, variable: 'TOKEN')]) {
        def alteredUrl = url.replace("http://", "https://${env.TOKEN}:x-oauth-basic@")
        sh "git remote set-url origin ${alteredUrl}"
    }
}