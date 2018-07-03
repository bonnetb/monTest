pipeline {
 agent {
  label 'maven'
 }
 options {
  buildDiscarder(logRotator(numToKeepStr: '5'))
}
 stages {
  stage('build') {
   steps {
    checkout scm
    sh 'mvn clean install'
   }
  }
  stage('publish-to-nexus') {
   steps {
    sh "mvn deploy -DaltDeploymentRepository=nexus::default::http://admin:admin123@nexus3-mon-projet-de-test.apps.sodigital.io/repository/maven-snapshots/"
   }
  }
  stage('publish-to-docker-register') {
   steps {
    sh "mvn -Ddocker.push.registry=docker-registry.default.svc:5000 -DaltDeploymentRepository=nexus::default::http://admin:admin123@nexus3-mon-projet-de-test.apps.sodigital.io/repository/maven-snapshots/ -Dfabric8.mode=openshift fabric8:build"
   }
  }
  stage('create dev config map') {
   steps {
    sh "oc create configmap config --from-file=dev.properties -n mon-projet-de-test -o yaml --dry-run | oc apply -f -"
   }
  }
/*  
  stage('deploy') {
   steps {
    sh "mvn fabric8:resource fabric8:deploy"
   }
  }
  */
  stage('promote to qual') {
   steps {
    /*sh "mvn fabric8:apply -Dfabric8.namespace=dev"*/
    /*milestone label: 'milestone'*/
    input 'Déployer en qualif ?'
   }
  }
  stage('create qual config map') {
   steps {
    sh "oc create configmap config --from-file=qual.properties -n dev -o yaml --dry-run | oc apply -n dev -f  -"
   }
  }
  stage('publish-to-docker-register-dev') {
   steps {
    sh "mvn -Ddocker.push.registry=docker-registry.default.svc:5000 -DaltDeploymentRepository=nexus::default::http://admin:admin123@nexus3-mon-projet-de-test.apps.sodigital.io/repository/maven-snapshots/ -Dfabric8.mode=openshift -Dfabric8.namespace=dev fabric8:build"
   }
  }
  /*
  stage('deploy') {
   steps {
      sh "rm -rf oc-build && mkdir -p oc-build/deployments"
    sh "cp target/*.war oc-build/deployments/ROOT.war"
    // clean up. keep the image stream
    sh "oc project mon-projet-de-test"
    sh "oc delete bc,dc,svc,route -l application=monappliweb -n mon-projet-de-test"
    // create build. override the exit code since it complains about existing imagestream
    sh "oc new-build --name=monappliweb --image-stream=jboss-eap70-openshift --binary=true --labels=application=monappliweb -n mon-projet-de-test || true"
    // build image
    sh "oc start-build monappliweb --from-dir=oc-build --wait=true -n mon-projet-de-test"
    // deploy image
    sh "oc new-app monappliweb:latest -n mon-projet-de-test"
    sh "oc expose svc/monappliweb -n mon-projet-de-test"
   }
  }
  
  /*stage('build image') {
   steps {
    openshiftBuild bldCfg: "montest2", checkForTriggeredDeployments: 'false',
                   namespace: "mon-projet-de-test", showBuildLogs: 'true',
                   verbose: 'false', waitTime: '', waitUnit: 'sec',
                   env: [ [ name: 'WAR_FILE_LOCATION', value: "http://admin:admin123@nexus3-mon-projet-de-test.apps.sodigital.io/repository/maven-snapshots/org/springframework/gs-spring-boot/0.1.0/gs-spring-boot-0.1.0.jar" ] ]
*/
    // Tag the new build as "x.y.build-z"
    /*openshiftTag alias: 'false', destStream: 'tasks', destTag: "${version}.build-${BUILD_NUMBER}",
                 destinationNamespace: 'tasks-dev', namespace: 'tasks-dev',
                 srcStream: 'tasks', srcTag: 'latest', verbose: 'false'*/
 /*  }
  }*/
 }
}
