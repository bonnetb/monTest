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
  stage('publish') {
   steps {
    sh "mvn deploy -DskipTests=true -DaltDeploymentRepository=nexus::default::http://admin:admin123@nexus3-mon-projet-de-test.apps.sodigital.io/repository/maven-releases/"
   }
  }
  
  stage('build image') {
   steps {
    openshiftBuild bldCfg: "montest2", checkForTriggeredDeployments: 'false',
                   namespace: "mon-projet-de-test", showBuildLogs: 'true',
                   verbose: 'false', waitTime: '', waitUnit: 'sec',
                   env: [ [ name: 'WAR_FILE_LOCATION', value: "http://admin:admin123@nexus3-mon-projet-de-test.apps.sodigital.io/repository/maven-releases/org/springframework/gs-spring-boot/0.1.0/gs-spring-boot-0.1.0.jar" ] ]

    // Tag the new build as "x.y.build-z"
    /*openshiftTag alias: 'false', destStream: 'tasks', destTag: "${version}.build-${BUILD_NUMBER}",
                 destinationNamespace: 'tasks-dev', namespace: 'tasks-dev',
                 srcStream: 'tasks', srcTag: 'latest', verbose: 'false'*/
   }
  }
  
 }
}
