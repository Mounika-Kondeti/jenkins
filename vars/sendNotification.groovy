def call(String buildStatus = 'STARTED') {
    buildStatus = buildStatus ?: 'SUCCESSFUL'
    def jobUserId, jobUserName
    wrap([$class: 'BuildUser']) {
        jobUserId = "${BUILD_USER_ID}"
        jobUserName = "${BUILD_USER}"
    }

    println("Started By: ${jobUserName}")
    def colorCode = '#FF0000'
    if (buildStatus == 'STARTED') colorCode = '#FFFF00'
    else if (buildStatus == 'SUCCESSFUL') colorCode = '#00FF00'

    def summary = "${buildStatus}: `${env.JOB_NAME}` #${env.BUILD_NUMBER}:\n${env.BUILD_URL} env: `stage` triggered by: `${jobUserId}`"


    emailext(
        subject: "$buildStatus : Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
        mimeType: 'text/html',
        body: """<p>$buildStatus : Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'</p>
                 <p>Check console output at <a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a></p>""",
        to: 'suryanarayana@planetc.net,mounikakondeti@planetc.net'
    )
}
