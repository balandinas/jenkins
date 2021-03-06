#!groovy
// Check ubuntu2 properties
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
                sh 'ssh root@ubuntu2 \'hostname\''
            }
        }
        stage("Second step") {
            steps {
                sh 'ssh root@ubuntu2 \'uptime\''
            }
        }
    }
}