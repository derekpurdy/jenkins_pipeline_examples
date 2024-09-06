// How to read properties from a file and call a parameterized build job

pipeline {
    agent any

    stages {
        stage("Drop parameters to a file") {
            steps {
                sh(script: "echo 'BLAH1=BLAH1' > ${WORKSPACE}/test.vars")
                sh(script: "echo 'BLAH2=BLAH2' >> ${WORKSPACE}/test.vars")
                sh(script: "echo 'BLAH3=VALID' >> ${WORKSPACE}/test.vars")
            }
        }
        stage("Read from a Properties file") {
            steps{
                script {
                    def propertiesFile = "${WORKSPACE}/test.vars"
                    if (fileExists(propertiesFile)) {
                        def parameterProperties = readProperties(file: propertiesFile)
                        def params = parameterProperties.collect { key, value ->
                            string(name: key, value: value)
                        }
                        build job: 'Parameterizedtest',
                            parameters: params,
                            propagate: true,
                            wait: true
                    } else {
                        error "Properties file does not exist: ${propertiesFile}"
                    }
                }
            }
        }
    }
}
