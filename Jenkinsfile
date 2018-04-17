pipeline {
 agent any
 options {
        buildDiscarder(logRotator(numToKeepStr: '3'))
}
 stages {
  stage('build') {
   steps {
    checkout scm
    sh 'mvn clean install'
   }
  }
 }
}
