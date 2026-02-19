def call(String repo_uri, String reponame, String tagname) {
    container("buildah-cont") {
        stage("build and push image") {
            sh 'buildah --version'
            println "======================================== \n     Building the Dockerfile... \n========================================"
            sh "buildah login -u AWS --password-stdin < /home/jenkins/agent/ecr_login.sh $repo_uri"
            sh "buildah bud -t $repo_uri/$reponame:$tagname"
            println "============================================= \n      Pushing the image with tagname... \n============================================="
            sh "buildah push $repo_uri/$reponame:$tagname"
            sh 'buildah images'

            println "===============================================\n Scanning the image... \n======================================================="
        }
    }

    container("trivy-cont") {
        sh "trivy -v"
        sh "trivy image --no-progress $repo_uri/$reponame:$tagname > Vulnerability_report.txt"

        stage('Send Email with Vulnerability Report') {
            emailext attachmentsPattern: 'Vulnerability_report.txt',
                     subject: "URGENT: Image Scan Report for ${reponame}:${tagname}",
                     mimeType: 'text/html',
                     body: """<p>Vulnerability scan results for <strong>${reponame}:${tagname}</strong>.</p>
                              <p>Please find the attached scan report.</p>""",
                     to :'mohit.sh@planetc.net,nidhi@planetc.net,cc:suryanarayana@planetc.net,cc:mounikakondeti@planetc.net,cc:pravat@planetc.net'
        }
    }
}
