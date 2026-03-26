def call(String podtemplatename, Closure body) {

    podTemplate(
        label: podtemplatename,
        nodeSelector: "name=contido-jenkins-agent-node",
        containers: [
            containerTemplate(name: 'aws-cont', image: '909463554763.dkr.ecr.ap-south-1.amazonaws.com/org-tools:awscli-1.0', command: 'cat', ttyEnabled: true, privileged: true),
            containerTemplate(name: 'buildah-cont', image: '909463554763.dkr.ecr.ap-south-1.amazonaws.com/org-tools:buildah-1.0', command: 'sleep 99999', ttyEnabled: true, privileged: true),
            containerTemplate(name: 'k8s', image: '909463554763.dkr.ecr.ap-south-1.amazonaws.com/org-tools:k8s-image', command: 'cat', ttyEnabled: true, privileged: true),
            containerTemplate(name: 'trivy-cont', image: '909463554763.dkr.ecr.ap-south-1.amazonaws.com/org-tools:trivy', command: 'sleep 99999', ttyEnabled: true, privileged: true)
        ],
        volumes: [
            emptyDirVolume(mountPath: '/home/jenkins/agent', memory: false)
        ]
    ) {
        node(podtemplatename) {
            body()   
        }
    }
}
