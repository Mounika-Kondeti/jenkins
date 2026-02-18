def call(String buildStatus = 'SUCCESS') {
    def subject = "${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'"
    def body = """<p>Build status: ${buildStatus}</p>
<p>Job: <a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a></p>"""

    // Send email
    emailext(
        to: 'mounikakondeti@planetc.net',  // replace with your email
        subject: subject,
        body: body,
        recipientProviders: [[$class: 'DevelopersRecipientProvider']]
    )
}
