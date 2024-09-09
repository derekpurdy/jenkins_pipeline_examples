// How to read properties from a file and call a parameterized build job

pipeline {
    agent any

    stages {
        stage("Drop parameters to a file") {
            steps {
                sh(script: "echo 'BLAH1=VALID' > ${WORKSPACE}/test.vars")
                sh(script: "echo 'BLAH2=VALID' >> ${WORKSPACE}/test.vars")
                sh(script: "echo 'BLAH3=VALID' >> ${WORKSPACE}/test.vars")
            }
        }
        stage("Read from a Properties file") {
            steps{
                script {
                    def PROPERTIES_FILE = "${WORKSPACE}/test.vars"
                    if (fileExists(PROPERTIES_FILE)) {
                        def PARAMETER_PROPERTIES = readProperties(file: PROPERTIES_FILE)
                        def PARAMS = PARAMETER_PROPERTIES.collect { key, value ->
                            string(name: key, value: value)
                        }
                        build job: 'Parameterizedtest',
                            parameters: PARAMS,
                            propagate: true,
                            wait: true
                    } else {
                        error "Properties file does not exist: ${PROPERTIES_FILE}"
                    }
                }
            }
        }
    }
}
