/**
 * Date: 05.06.2016
 * Time: 11:44
 *
 * @author Michael Clausen
 * @version $Id: $Id
 */

tree = ['docker-debian':[:]];

def traverse (map, builder) {
              map.collect {name, Map config ->
                  builder (name)
              }
}

def define (project) {
    job             (project) {
        description (project)
    }
}

traverse (tree, this.&define)
