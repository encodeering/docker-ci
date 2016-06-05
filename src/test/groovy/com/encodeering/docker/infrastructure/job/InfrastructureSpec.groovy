package com.encodeering.docker.infrastructure.job

import com.encodeering.docker.infrastructure.Job
import spock.lang.Specification
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
            }
    }

}
