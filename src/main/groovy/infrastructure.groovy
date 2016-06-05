import org.yaml.snakeyaml.Yaml

/**
 * Date: 05.06.2016
 * Time: 11:44
 *
 * @author Michael Clausen
 * @version $Id: $Id
 */

        config = new Yaml ().load (readFileFromWorkspace ('config.yml'))
tree  = config.tree

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

traverse (tree as Map, this.&define)
