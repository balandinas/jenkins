#!groovy
properties([disableConcurrentBuilds()])

pipeline {
    agent {
        label 'master'
        }
    options {
        buildDiscarder(logRotator(numToKeepStr: '10', artifactNumToKeepStr: '10'))
        timestamps()
    }
    stages {
        stage("First step") {
            steps {
                sh 'scp Dockerfile root@ubuntu2:/home/ubuntu/docker/alpine/'
                sh 'ssh root@ubuntu2 \'docker build /home/ubuntu/docker/alpine/\''
            }
        }
        stage("Second step") {
            steps {
                sh 'ssh root@ubuntu2 \'uptime\''
            }
        }
    }
}