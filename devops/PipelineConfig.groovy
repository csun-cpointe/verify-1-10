/*
 * NOTE: This file is intended for use with the SDP Jenkins Templating Engine v2.0+
 * which supports fetching the job template and configuration from an SCM location.
 * 
 * Originally generated from: templates/devops/jte.PipelineConfig.groovy.vm
 */
libraries {
	git {
		github
	}
	maven {
		mavenId = "maven"
	}
}
