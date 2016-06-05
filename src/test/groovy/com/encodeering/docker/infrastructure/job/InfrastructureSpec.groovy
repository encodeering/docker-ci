package com.encodeering.docker.infrastructure.job

import com.encodeering.docker.infrastructure.Job
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Date: 05.06.2016
 * Time: 11:40
 *
 * @author Michael Clausen
 * @version $Id: $Id
 */
class InfrastructureSpec extends Specification implements Job {

    def 'creates a descriptive job' () {
        given:
            def script = load ('infrastructure.groovy')
            def jobs   = management ()

        when:
            script (jobs)

        then:
            verify (jobs.savedConfigs.get ('docker-debian')) {
                assert it.description.text () == 'docker-debian'
                assert it.assignedNode.text () == 'docker'
            }
    }

    def 'creates a descriptive service job' () {
        given:
            def script = load ('infrastructure.groovy')
            def jobs   = management ()

        when:
            script (jobs)

        then:
            verify (jobs.savedConfigs.get ('docker-debian-travis')) {
                assert it.description.text () == 'docker-debian-travis'
                assert it.assignedNode.text () == 'docker'
                assert it.properties.EnvInjectJobProperty.info.propertiesContent.text () == 'arch=amd64\naccount=encodeering\nbranch=master\nproject=docker-debian'

                def binding = it.buildWrappers.'org.jenkinsci.plugins.credentialsbinding.impl.SecretBuildWrapper'.bindings
                                              .'org.jenkinsci.plugins.credentialsbinding.impl.StringBinding'

                assert binding.variable.text () == 'token'
                assert binding.credentialsId.text () == 'travis-token'

                assert it.builders.'hudson.tasks.Shell'.command.text () == 'echo "travis"\n'
            }
    }

    def 'creates a trigger for the service job' () {
        given:
            def script = load ('infrastructure.groovy')
            def jobs   = management ()

        when:
            script (jobs)

        then:
            verify (jobs.savedConfigs.get ('docker-debian')) {
                assert it.publishers.'hudson.tasks.BuildTrigger'.childProjects.text () == 'docker-debian-travis'
            }
    }

    @Unroll
    def "creates a reference for downstream builds of #name" (name, trigger) {
        given:
            def script = load ('infrastructure.groovy')
            def jobs   = management ()

        when:
            script (jobs)

        then:
            verify (jobs.savedConfigs.get (name)) {
                assert it.publishers.'join.JoinTrigger'.joinProjects.text () == trigger
            }

        where:
            name              | trigger
            'docker-debian'   | 'docker-php, docker-postgres'
            'docker-php'      | ''
            'docker-postgres' | ''
    }

}
