pipeline {

    agent {
        docker {
            label 'memphis-jenkins-big-fleet,'
            image 'gradle:7.3.0'
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
                }

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
                        gpg --batch --pinentry-mode loopback --passphrase '${env.GPG_PASSPHRASE}' --export-secret-keys --export-secret-keys -o clients/secring.gpg

                    """
                }               
                sh """

                    ./gradlew :clients:publish -Pversion=${env.versionTag} -Psigning.password=${env.GPG_PASSPHRASE}
                """
                sh "rm /tmp/kafka-clients/ai/superstream/kafka-clients/maven-metadata.xml*"
                script {
                    // Execute multiple shell commands within a single sh block
                    def response = sh(script: """

                        cd /tmp/kafka-clients

                        tar czvf ai.tar.gz ai

                        curl --request POST \\
                             --verbose \\
                             --header 'Authorization: Bearer ${env.TOKEN}' \\
                             --form bundle=@ai.tar.gz \\
                             'https://central.sonatype.com/api/v1/publisher/upload?name=kafka-clients-${env.versionTag}&publishingType=AUTOMATIC'
                    """, returnStdout: true).trim()

                    def id = response.split("\n").last().trim()
                    echo "Extracted ID: ${id}"

                    def status = 'PENDING'
                    while (status == 'PENDING') {
                        // Execute curl and capture the output
                        def output = sh(script: """
                            curl --verbose \\
                                 --header 'Authorization: Bearer ${env.TOKEN}' \\
                                 'https://central.sonatype.com/api/v1/publisher/status?id=${env.ID}'
                        """, returnStdout: true).trim()

                        // Print the output for debugging
                        echo "Curl Output: ${output}"

                        // Check the output for specific statuses
                        if (output.contains('PENDING')) {
                            // Sleep for 5 seconds before retrying
                            sleep time: 5, unit: 'SECONDS'
                        } else if (output.contains('FAILED')) {
                            // If output contains 'FAILED', exit the script with an error
                            echo "Build status check failed."
                            error "Exiting due to failure status detected."
                        } else {
                            echo 'Deployment was successfully'
                        }
                    }
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
                }

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
                        gpg --batch --pinentry-mode loopback --passphrase '${env.GPG_PASSPHRASE}' --export-secret-keys --export-secret-keys -o clients/secring.gpg

                    """
                }               
                sh """

                    ./gradlew :clients:publish -Pversion=${env.versionTag} -Psigning.password=${env.GPG_PASSPHRASE}
                """
                sh "rm /tmp/kafka-clients/ai/superstream/kafka-clients/maven-metadata.xml*"
                script {
                    // Execute multiple shell commands within a single sh block
                    def response = sh(script: """

                        cd /tmp/kafka-clients

                        tar czvf ai.tar.gz ai

                        curl --request POST \\
                             --verbose \\
                             --header 'Authorization: Bearer ${env.TOKEN}' \\
                             --form bundle=@ai.tar.gz \\
                             https://central.sonatype.com/api/v1/publisher/upload?publishingType=AUTOMATIC
                    """, returnStdout: true).trim()

                    def id = response.split("\n").last().trim()
                    echo "Extracted ID: ${id}"
                }                     
            }
        }
        stage('Prod Release') {
            when {
                branch '*-prod'
            }            
            steps {
                script {
                    def version = readFile('version.conf').trim()
                    env.versionTag = version
                    echo "Using version from version.conf: ${env.versionTag}"                        
                }

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
                        gpg --batch --pinentry-mode loopback --passphrase '${env.GPG_PASSPHRASE}' --export-secret-keys --export-secret-keys -o clients/secring.gpg

                    """
                }               
                sh """

                    ./gradlew :clients:publish -Pversion=${env.versionTag} -Psigning.password=${env.GPG_PASSPHRASE}
                """
                sh "rm /tmp/kafka-clients/ai/superstream/kafka-clients/maven-metadata.xml*"
                script {
                    // Execute multiple shell commands within a single sh block
                    def response = sh(script: """

                        cd /tmp/kafka-clients

                        tar czvf ai.tar.gz ai

                        curl --request POST \\
                             --verbose \\
                             --header 'Authorization: Bearer ${env.TOKEN}' \\
                             --form bundle=@ai.tar.gz \\
                             https://central.sonatype.com/api/v1/publisher/upload?name=kafka-clients-${env.versionTag}&publishingType=AUTOMATIC
                    """, returnStdout: true).trim()

                    def id = response.split("\n").last().trim()
                    echo "Extracted ID: ${id}"
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
