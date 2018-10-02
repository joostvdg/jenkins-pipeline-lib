def call (imageLine) {
    // def imageLine = 'appregister-backend:latest'
    writeFile file: 'anchore_images', text: imageLine
    anchore name: 'anchore_images', bailOnFail: false, inputQueries: [
            [query: 'cve-scan all'],
            [query: 'list-packages all'],
            [query: 'list-files all'],
            [query: 'show-pkg-diffs base']
    ]
}