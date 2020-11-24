pipeline {
	agent {
		label 'migration'
	}
  
	tools {
		maven 'apache-maven-latest'
		jdk 'oracle-jdk8-latest'
	}
  
	environment {
		BUILD_KEY = (github.isPullRequest() ? CHANGE_TARGET : BRANCH_NAME).replaceFirst(/^v/, '')
		CAPELLA_PRODUCT_PATH = "${WORKSPACE}/capella/eclipse/eclipse"
  	}
  
  	stages {
  	
		stage('Generate Target Platform') {
	    	steps {        
	        	script { 
		        	if(github.isPullRequest()){
		        	    github.buildStartedComment()
		        	}
		
		        	currentBuild.description = BUILD_KEY
		        	
		        	sh 'env'
		        	sh 'mvn clean verify -f releng/org.polarsys.capella.transition.system2subsystem.target/pom.xml'
	       		}         
	     	}
	    }
	    
    	stage('Build and Package') {
      		steps {
      			script {
      				def customParams = github.isPullRequest() ? '-DSKIP_SONAR=true' : '-Psign'
      	    
      	    		sh "mvn -Djacoco.skip=true -DjavaDocPhase=none ${customParams} clean package -f pom.xml"
	       		}         
	     	}
	    }
    
		stage('Deploy to Nightly') {
      		steps {
				script {
		    		def deploymentDirName = 
		    			(github.isPullRequest() ? "${BUILD_KEY}-${BRANCH_NAME}-${BUILD_ID}" : "${BRANCH_NAME}-${BUILD_ID}")
		    			.replaceAll('/','-')		
					
					deployer.addonNightlyDropins("${WORKSPACE}/releng/org.polarsys.capella.transition.system2subsystem.site/target/*-dropins-*.zip", deploymentDirName)
					deployer.addonNightlyUpdateSite("${WORKSPACE}/releng/org.polarsys.capella.transition.system2subsystem.site/target/*-updateSite-*.zip", deploymentDirName)					

	       		}         
	     	}
	    }
	    
	    stage('Download Capella') {
        	steps {
        		script {
	        		def capellaURL = capella.getDownloadURL("1.4.x", 'linux', '')
	        		
	        		sh "curl -k -o capella.zip ${capellaURL}"
					sh "unzip -q capella.zip"

	       		}         
	     	}
	    }

    	stage('Install test features') {
        	steps {
        		script {
	        		sh "chmod 755 ${CAPELLA_PRODUCT_PATH}"
	        		
	        		eclipse.installFeature("${CAPELLA_PRODUCT_PATH}", 'http://download.eclipse.org/tools/orbit/downloads/drops/R20130827064939/repository', 'org.jsoup')	        		
	        		eclipse.installFeature("${CAPELLA_PRODUCT_PATH}", capella.getTestUpdateSiteURL("1.4.x"), 'org.polarsys.capella.test.feature.feature.group')
	        		
	        		eclipse.installFeature("${CAPELLA_PRODUCT_PATH}", "file:/${WORKSPACE}/releng/org.polarsys.capella.transition.system2subsystem.site/target/repository/".replace("\\", "/"), 'org.polarsys.capella.transition.system2subsystem.feature.feature.group')
	        		eclipse.installFeature("${CAPELLA_PRODUCT_PATH}", "file:/${WORKSPACE}/releng/org.polarsys.capella.transition.system2subsystem.site/target/repository/".replace("\\", "/"), 'org.polarsys.capella.transition.system2subsystem.tests.feature.feature.group')
	       		}         
	     	}
	    }
	    
    	stage('Run tests') {
        	steps {
        		script {
        			wrap([$class: 'Xvnc', takeScreenshot: false, useXauthority: true]) {
		        		
		        		tester.runUITests("${CAPELLA_PRODUCT_PATH}", 'AllSystem2SubsystemTests', 'org.polarsys.capella.transition.system2subsystem.tests.ju', 
		        			['org.polarsys.capella.transition.system2subsystem.tests.AllSystem2SubsystemTests'])		        			 
	        		}
	        		
	        		tester.publishTests()
				}
			}
		}
		
		stage('Sonar') {
			steps {
				script {
					sonar.runSonar("eclipse_capella-sss-transition", "eclipse/capella-sss-transition", 'sonarcloud-token-sss-transition')
				}
			}
		}
		
	}
  
	post {
    	always {
       		archiveArtifacts artifacts: '**/*.log, *.log, *.xml, **/*.layout, *.exec'
    	}
    	
    	success  {
    		script {
    			if(github.isPullRequest()){
        			github.buildSuccessfullComment()
        		}
        	}
    	}
    	
	    unstable {
	    	script {
	    		if(github.isPullRequest()){
	        		github.buildUnstableComment()
	        	}
	        }
	    }
    
	    failure {
	    	script {
	    		if(github.isPullRequest()){
	        		github.buildFailedComment()
	        	}
	        }
	    }
	    
	    aborted {
	    	script {
	    		if(github.isPullRequest()){
	        		github.buildAbortedComment()
	        	}
	        }
	    }
	}
}