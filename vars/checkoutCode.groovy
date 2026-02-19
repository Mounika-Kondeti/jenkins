def call(String branch) {
    stage('checkout-code') {
        println "======================================== \n     Cloning the git repo... \n========================================"
        checkout([
            $class: 'GitSCM',
            branches: [[name: "*/${branch}"]],
            extensions: [],
            userRemoteConfigs: [[
                credentialsId: 'suryadesy_github',
                url: 'https://github.com/Desynova/Contido.git'
            ]]
        ])
    }
}
