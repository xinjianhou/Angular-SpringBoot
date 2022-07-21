node {
  stage('SCM') {
    checkout scm
  }
  stage('SonarQube Analysis') {
    def mvn = tool 'maven';
    withSonarQubeEnv() {
      sh "cd wechat;
	${mvn}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=wechat"
    }
  }
}
