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

    @Unroll
    def "creates a descriptive job named #name" (name) {
        given:
            def script = load ('infrastructure.groovy')
            def jobs   = management ()

        when:
            script (jobs)

        then:
            verify (jobs.savedConfigs.get (name)) {
                assert it.description.text () == name
                assert it.assignedNode.text () == 'docker'
            }

        where:
            name << [
                'docker-debian',
                'docker-php',
                'docker-postgres'
            ]
    }

    @Unroll
    def "creates a descriptive service job named #name-#service" (name, service) {
        given:
            def script = load ('infrastructure.groovy')
            def jobs   = management ()
            def servicename = "$name-$service".toString ()

        when:
            script (jobs)

        then:
            verify (jobs.savedConfigs.get (servicename)) {
                assert it.description.text () == servicename
                assert it.assignedNode.text () == 'docker'
                assert it.properties.EnvInjectJobProperty.info.propertiesContent.text () == "arch=amd64\naccount=encodeering\nbranch=master\nproject=$name"

                def binding = it.buildWrappers.'org.jenkinsci.plugins.credentialsbinding.impl.SecretBuildWrapper'.bindings
                                              .'org.jenkinsci.plugins.credentialsbinding.impl.StringBinding'

                assert binding.variable.text () == 'token'
                assert binding.credentialsId.text () == "$service-token"

                assert it.builders.'hudson.tasks.Shell'.command.text () == """echo "$service"\n"""
            }

        where:
            name              | service
            'docker-debian'   | 'travis'
            'docker-php'      | 'travis'
            'docker-php'      | 'semaphore'
            'docker-postgres' | 'semaphore'
    }

    @Unroll
    def "creates a trigger for the service job of #name" (name, trigger) {
        given:
            def script = load ('infrastructure.groovy')
            def jobs   = management ()

        when:
            script (jobs)

        then:
            verify (jobs.savedConfigs.get (name)) {
                assert it.publishers.'hudson.tasks.BuildTrigger'.childProjects.text () == trigger
            }

        where:
            name              | trigger
            'docker-debian'   | 'docker-debian-travis'
            'docker-php'      | 'docker-php-travis, docker-php-semaphore'
            'docker-postgres' | 'docker-postgres-semaphore'
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
                assert it.publishers.'join.JoinTrigger'.evenIfDownstreamUnstable.text () == "false"
            }

        where:
            name              | trigger
            'docker-debian'   | 'docker-php, docker-postgres'
            'docker-php'      | ''
            'docker-postgres' | ''
    }

    @Unroll
    def "creates specific views for #name" (name, regex) {
        given:
            def script = load ('infrastructure.groovy')
            def jobs   = management ()

        when:
            script (jobs)

        then:
            verify (jobs.savedViews.get (name)) {
                assert it.includeRegex.text () == regex

                assert ! it.columns.'hudson.views.StatusColumn'.isEmpty ()
                assert ! it.columns.'hudson.views.WeatherColumn'.isEmpty ()
                assert ! it.columns.'hudson.views.BuildButtonColumn'.isEmpty ()
                assert ! it.columns.'hudson.views.JobColumn'.isEmpty ()
                assert ! it.columns.'hudson.views.LastSuccessColumn'.isEmpty ()
                assert ! it.columns.'hudson.views.LastFailureColumn'.isEmpty ()
                assert ! it.columns.'hudson.views.LastDurationColumn'.isEmpty ()
            }

        where:
            name               | regex
            'docker'           | '^docker-((?!travis|semaphore).)+$'
            'docker-travis'    | '^docker-(.+)-travis$'
            'docker-semaphore' | '^docker-(.+)-semaphore$'
    }

}
