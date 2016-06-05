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
"""

def traverse (map, builder) {
              map.collect {name, Map config ->
                  builder (name,     config.getOrDefault ('service',    []))
              }
}

def define (project, services) {
    def servicename = { "${project}-${it}" }

    job             (project) {
        description (project)
    }

    services.each {service ->
        job (            servicename (service)) {
            description (servicename (service))
        }
    }
}

traverse (new Yaml ().load (tree) as Map, this.&define)
