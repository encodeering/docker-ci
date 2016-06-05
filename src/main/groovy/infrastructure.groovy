import org.yaml.snakeyaml.Yaml

/**
 * Date: 05.06.2016
 * Time: 11:44
 *
 * @author Michael Clausen
 * @version $Id: $Id
 */

tree = """
---
docker-debian:
  service: [travis]
  downstream:
    - docker-php:
        service: [travis, semaphore]
    - docker-postgres:
        service: [travis]
"""

def traverse (map, builder) {
              map.collect {name, Map config ->
                  builder (name,     config.getOrDefault ('service',    []),
                                     config.getOrDefault ('downstream', []).collect {next -> traverse (next as Map, builder)}.flatten ())
                  name
              }
}

def define (project, services, downstreams) {
    def servicename = { "${project}-${it}" }

    job             (project) {
        description (project)

        publishers {
            joinTrigger {
                downstream (services.collect (servicename))
                projects   (* downstreams)
            }
        }
    }

    services.each {service ->
        job (            servicename (service)) {
            description (servicename (service))
        }
    }
}

traverse (new Yaml ().load (tree) as Map, this.&define)
