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
    checkout scm
    sh 'mvn clean install'
   }
  }
  stage('publish'){
   sh "${mvn} deploy -DskipTests=true -DaltDeploymentRepository=nexus::default::http://nexus3-mon-projet-de-test.apps.sodigital.io/"
  }
 }
}
