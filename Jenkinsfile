node {
  stage('SCM') {
    checkout scm
  }
  stage('SonarQube Analysis') {
    def mvn = tool '/usr/local/Cellar/maven/3.8.6';
    withSonarQubeEnv() {
      cd wechart;
      sh "${mvn}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=wechat"
    }
  }
}
