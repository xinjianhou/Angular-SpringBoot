node {
  stage('SCM') {
    checkout scm
  }
  stage('SonarQube Analysis') {
    def mvn = tool 'maven';
    withSonarQubeEnv() {
      cd wechart;
      sh "${mvn}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=wechat"
    }
  }
}
