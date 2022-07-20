node {
  stage('SCM') {
    checkout scm
  }
  stage('SonarQube Analysis') {
    def mvn = tool 'maven';
    withSonarQubeEnv() {
      "cmd /c cd /d wechat";
      sh "${mvn}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=wechat"
    }
  }
}
