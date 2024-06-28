// # Licensed to the Apache Software Foundation (ASF) under one or more
// # contributor license agreements.  See the NOTICE file distributed with
// # this work for additional information regarding copyright ownership.
// # The ASF licenses this file to You under the Apache License, Version 2.0
// # (the "License"); you may not use this file except in compliance with
// # the License.  You may obtain a copy of the License at
// #
// #    http://www.apache.org/licenses/LICENSE-2.0
// #
// # Unless required by applicable law or agreed to in writing, software
// # distributed under the License is distributed on an "AS IS" BASIS,
// # WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// # See the License for the specific language governing permissions and
// # limitations under the License.

pipeline {

    agent {
        docker {
            label 'memphis-jenkins-big-fleet,'
            image 'gradle:8.5-jdk21'
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
                script {
                    def version = readFile('version-alpha.conf').trim()
                    env.versionTag = version
                    echo "Using version from version-alpha.conf: ${env.versionTag}" 
                    setupGPG()     
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
                branch '3.7.0'
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
                branch '3.7.0'
            }       
            steps {               
                sh """
                    curl -L https://github.com/cli/cli/releases/download/v2.40.0/gh_2.40.0_linux_amd64.tar.gz -o gh.tar.gz 
                    tar -xvf gh.tar.gz
                    mv gh_2.40.0_linux_amd64/bin/gh /usr/local/bin 
                    rm -rf gh_2.40.0_linux_amd64 gh.tar.gz
                """
                withCredentials([sshUserPrivateKey(keyFileVariable:'check',credentialsId: 'main-github')]) {
                sh """
                GIT_SSH_COMMAND='ssh -i $check -o StrictHostKeyChecking=no' git config --global user.email "jenkins@memphis.dev"
                GIT_SSH_COMMAND='ssh -i $check -o StrictHostKeyChecking=no' git config --global user.name "Jenkins"   
                GIT_SSH_COMMAND='ssh -i $check -o StrictHostKeyChecking=no' git config --global --add safe.directory ${WORKSPACE}
                GIT_SSH_COMMAND='ssh -i $check -o StrictHostKeyChecking=no' git tag -a $versionTag -m "$versionTag"
                GIT_SSH_COMMAND='ssh -i $check -o StrictHostKeyChecking=no' git push origin $versionTag
                """
                }                
                withCredentials([string(credentialsId: 'gh_token', variable: 'GH_TOKEN')]) {
                sh """
                gh release create $versionTag /tmp/kafka-clients/kafka-client-${env.versionTag}.tar.gz --generate-notes
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
        tar czvf kafka-client-${env.versionTag}.tar.gz ai
        curl --request POST \\
             --verbose \\
             --header 'Authorization: Bearer ${env.TOKEN}' \\
             --form bundle=@kafka-client-${env.versionTag}.tar.gz \\
             'https://central.sonatype.com/api/v1/publisher/upload?name=kafka-clients-${env.versionTag}&publishingType=AUTOMATIC'
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
