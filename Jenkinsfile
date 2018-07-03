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

  stage('deploy') {
   steps {
    sh "mvn fabric8:resource fabric8:deploy"
   }
  }
  
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
 }
}
