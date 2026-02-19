def call() {
    container("aws-cont") {
        stage('retrieve ecr token') {
            sh """
                aws ecr get-login-password --region ap-south-1 > /home/jenkins/agent/ecr_login.sh
            """
        }
    }
}
