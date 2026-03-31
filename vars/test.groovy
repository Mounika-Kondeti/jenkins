def notifyBuild(String buildStatus = 'STARTED') {
  buildStatus =  buildStatus ?: 'SUCCESSFUL'
  def jobUserId, jobUserName
//get user id and name of the build started user.
  wrap([$class: 'BuildUser']) {
      jobUserId = "${BUILD_USER_ID}"
      jobUserName = "${BUILD_USER}"
}

  println("Started By: ${jobUserName}")
  def colorName = 'RED'
  def colorCode = '#FF0000'
  def summary = "${buildStatus}: `${env.JOB_NAME}` #${env.BUILD_NUMBER}:\n${env.BUILD_URL} env: `stage` triggered by: `${jobUserId}`"

  // Override default values based on build status
  if (buildStatus == 'STARTED') {
    color = 'YELLOW'
    colorCode = '#FFFF00'
  } else if (buildStatus == 'SUCCESSFUL') {
    color = 'GREEN'
    colorCode = '#00FF00'
  } else {
    color = 'RED'
    colorCode = '#FF0000'
  }

  // Send notifications
   //slackSend (color: colorCode, message: summary)
  
//   // Send email notifications
      emailext (
      subject: "$buildStatus : Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
      mimeType: 'text/html',
      body: """<p>$buildStatus : Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'</p>
        <p>Check console output at <a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a></p>""",
    //  recipientProviders: [developers(), requestor()],
      to: 'mounikakondeti@planetc.net')

}
