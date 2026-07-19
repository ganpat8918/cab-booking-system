pipeline {

    agent any

    environment {
        IMAGE_NAME = "bhulu79/cab-booking-system:latest"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Workspace') {
            steps {
                sh 'pwd'
                sh 'ls -la'
            }
        }

        stage('Java') {
            steps {
                sh 'java -version'
            }
        }

        stage('Maven Wrapper') {
            steps {
                sh 'chmod +x mvnw'
                sh './mvnw -version'
            }
        }

        stage('Build') {
            steps {
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t $IMAGE_NAME .'
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(
                        credentialsId: 'dockerhub',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS')]) {

                    sh '''
                    echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                    docker push $IMAGE_NAME
                    '''
                }
            }
        }

    }

    post {

        success {
            echo 'Pipeline Completed Successfully'
        }

        failure {
            echo 'Pipeline Failed'
        }

    }

}