pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                sh 'cd server && ./mvnw clean && ./mvnw verify'
            }
        }
        stage('Build Image') {
            steps {
                sh "cd server && docker build -t engineer365/fleashop:${env.BUILD_ID} ."
            }
        }
    }
}
