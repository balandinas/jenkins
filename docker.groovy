#!groovy
// Run docker build
properties([disableConcurrentBuilds()])

pipeline {
    agent { 
        label 'master'
        }
    triggers { pollSCM('* * * * *') }
    options {
        buildDiscarder(logRotator(numToKeepStr: '10', artifactNumToKeepStr: '10'))
        timestamps()
    }
    stages {
        stage("docker login") {
            steps {
                echo " ============== docker login =================="
                withCredentials([usernamePassword(credentialsId: 'balandinas_dockerhub', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                    sh " ssh root@ubuntu2 docker login -u $USERNAME -p $PASSWORD"
                }
            }
        }
        stage("create docker image") {
            steps {
                echo " ============== start building image =================="
                sh 'scp Dockerfile root@ubuntu2:/home/ubuntu/docker/alpine/'
                sh 'ssh root@ubuntu2 \'docker build /home/ubuntu/docker/alpine/ -t balandinas/mytool:latest \''
                //sh 'ssh root@ubuntu2 docker build -t balandinas/mytool:latest '
            }
        }
        stage("docker push") {
            steps {
                echo " ============== start pushing image =================="
                sh 'ssh root@ubuntu2 \'docker push balandinas/mytool:latest \''
            }
        }
    }
}
