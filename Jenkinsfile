pipeline {
 agent any
 tools {
 # maven '3.5.0'
 # jdk 'java8'
 }
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
