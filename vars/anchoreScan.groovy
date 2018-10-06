def call (imageLine) {
    writeFile file: 'anchore_images', text: imageLine
    anchore bailOnFail: false, bailOnPluginFail: false, name: 'anchore_images'
}