// parameterized build example for readFromPropertiesFile groovy example

pipeline {    
    agent any
    
    parameters {
        string(name: "BLAH1", defaultValue: "NOT_VALID", trim: true, description: "BLAH1 Value")
        string(name: "BLAH2", defaultValue: "NOT_VALID", trim: true, description: "BLAH2 Value")
        choice(name: 'BLAH3', choices: ['NOT_VALID', 'VALID'], description: 'BLAH3 Value') 
    }

    stages {
        stage("Build") {
            steps {
                echo "Blah1: $params.BLAH1"
                echo "Blah2: $params.BLAH2"
                echo "Blah3: $params.BLAH3"
            }
        }
    }
}
