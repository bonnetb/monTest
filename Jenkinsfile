pipeline {
 agent {
      label 'maven'
 }
 options {
        buildDiscarder(logRotator(numToKeepStr: '3'))
}
 stages {
  stage('build') {
   steps {
    sh 'mvn clean install'
   }
  }
 }
}
