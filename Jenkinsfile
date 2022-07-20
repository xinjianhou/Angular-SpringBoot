node {
  stage('SCM') {
    checkout scm
  }
  stage('SonarQube Analysis') {
    def mvn = tool 'mvn';
    withSonarQubeEnv() {
      cd wechart;
      sh "${mvn} clean verify sonar:sonar -Dsonar.projectKey=wechat"
    }
  }
}
