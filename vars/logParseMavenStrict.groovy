def call(boolean unstableOnWarning, boolean failBuildOnError) {

def logParseConfigFile = 'temprules'

writeFile encoding: 'UTF-8', file: logParseConfigFile, text: '''ok /not really/
# match line starting with \'error \', case-insensitive
error /(?i)^error /

# list of warnings here...
warning /[Ww]arning/
warning /WARNING/
warning /at java.base/

# each line containing \'mvn\' represents the start of a section for grouping errors and warnings found after the line.
# also creates a quick access link.
start /mvn/'''

echo "Written LogParse config file to ${logParseConfigFile}"
echo 'Parsing logs'
step([$class: 'LogParserPublisher', failBuildOnError: failBuildOnError, projectRulePath: logParseConfigFile, showGraphs: true, unstableOnWarning: unstableOnWarning, useProjectRule: true])

}
