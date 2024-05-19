pipeline {

    agent {
        docker {
            label 'memphis-jenkins-big-fleet,'
            // image 'gradle:8.6'
            // image 'gradle:7.3.0'            
            image 'gradle:8.6.0-jdk21-alpine'
            args '-u root'
        }
    } 

    environment {
            HOME           = '/tmp'
            TOKEN          = credentials('maven-central-token')
            GPG_PASSPHRASE = credentials('gpg-key-passphrase')
    }

    stages {
        stage('Alpha Release') {
            when {
                branch '*-alpha'
            }            
            steps {
                sh "echo $JAVA_HOME"
                sh "java -version"
                sh "javac -version"

                script {
                    def version = readFile('version-alpha.conf').trim()
                    env.versionTag = version
                    echo "Using version from version-alpha.conf: ${env.versionTag}" 
                    sh "apk add curl"
                    // setupGPG()     
                    publishClients() 
                    uploadBundleAndCheckStatus()                         
                }
            }
        }
        stage('Beta Release') {
            when {
                branch '*-beta'
            }            
            steps {
                script {
                    def version = readFile('version-beta.conf').trim()
                    env.versionTag = version
                    echo "Using version from version-beta.conf: ${env.versionTag}"  
                    setupGPG()     
                    publishClients() 
                    uploadBundleAndCheckStatus()                
                }                     
            }
        }
        stage('Prod Release') {
            when {
                branch '3.5.1'
            }            
            steps {
                script {
                    def version = readFile('version.conf').trim()
                    env.versionTag = version
                    echo "Using version from version.conf: ${env.versionTag}"
                    setupGPG()     
                    publishClients() 
                    uploadBundleAndCheckStatus()                                              
                }
            }
        }
        stage('Create Release'){
            when {
                branch '3.5.1'
            }       
            steps {               
                sh """
                    curl -L https://github.com/cli/cli/releases/download/v2.40.0/gh_2.40.0_linux_amd64.tar.gz -o gh.tar.gz 
                    tar -xvf gh.tar.gz
                    mv gh_2.40.0_linux_amd64/bin/gh /usr/local/bin 
                    rm -rf gh_2.40.0_linux_amd64 gh.tar.gz
                """
                withCredentials([string(credentialsId: 'gh_token', variable: 'GH_TOKEN')]) {
                sh """
                gh release create $versionTag --generate-notes
                """
                }
            }
        }                              
    }
    post {
        always {
            cleanWs()
        }
    }    
}

// Function to setup GPG
def setupGPG() {
    withCredentials([file(credentialsId: 'gpg-key', variable: 'GPG_KEY')]) {
        sh """
            apt update
            apt install -y gnupg
        """
        sh """
            echo '${env.GPG_PASSPHRASE}' | gpg --batch --yes --passphrase-fd 0 --import $GPG_KEY
            echo "allow-loopback-pinentry" > ~/.gnupg/gpg-agent.conf
            echo RELOADAGENT | gpg-connect-agent
            echo "D64C041FB68170463BE78AD7C4E3F1A8A5F0A659:6:" | gpg --import-ownertrust 
            gpg --batch --pinentry-mode loopback --passphrase '${env.GPG_PASSPHRASE}' --export-secret-keys -o clients/secring.gpg
        """
    }
}

// Function to publish clients using Gradle
def publishClients() {
    sh "./gradlew :clients:publish -Pversion=${env.versionTag} -Psigning.password=${env.GPG_PASSPHRASE}"
    sh "rm /tmp/kafka-clients/ai/superstream/kafka-clients/maven-metadata.xml*"
}

// Function to upload a bundle and check deployment status
def uploadBundleAndCheckStatus() {
    def response = sh(script: """
        cd /tmp/kafka-clients
        tar czvf ai.tar.gz ai
        curl --request POST \\
             --verbose \\
             --header 'Authorization: Bearer ${env.TOKEN}' \\
             --form bundle=@ai.tar.gz \\
             'https://central.sonatype.com/api/v1/publisher/upload?name=kafka-clients-${env.versionTag}'
    """, returnStdout: true).trim()
    def id = response.split("\n").last().trim()
    echo "Extracted ID: ${id}"
    sleep(10)
    def output = sh(script: """
        curl --request POST \\
             --verbose \\
             --header 'Authorization: Bearer ${env.TOKEN}' \\
             'https://central.sonatype.com/api/v1/publisher/status?id=${id}'
    """, returnStdout: true).trim()

    echo "Curl Output: ${output}"
    if (output.contains('FAILED')) {
        error "Deployment FAILED. Exiting with error."
    } else {
        echo "Deployment is successful."
    }
}