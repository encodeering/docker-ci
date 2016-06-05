package com.encodeering.docker.infrastructure

import javaposse.jobdsl.dsl.DslScriptLoader
import javaposse.jobdsl.dsl.GeneratedItems
import javaposse.jobdsl.dsl.JobManagement
import javaposse.jobdsl.dsl.MemoryJobManagement
import javaposse.jobdsl.dsl.ScriptRequest

/**
 * Date: 05.06.2016
 * Time: 14:06
 *
 * @author Michael Clausen
 * @version $Id: $Id
 */
trait Job {

    def void verify (String xml, Closure<?> assertion) {
        with (new XmlParser ().parse (new StringReader (xml)), assertion)
    }

    def Closure<GeneratedItems> load (String script) {
        URL directory            = new File ('src/main/groovy').toURI ().toURL ()
        ScriptRequest request    = new ScriptRequest (script, null, directory)

        return { JobManagement management -> new DslScriptLoader (management).runScripts ([request]) }
    }

    def JobManagement management () { new MemoryJobManagement () }

}
