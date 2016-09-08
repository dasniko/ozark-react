package dasniko.ozark.react;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.apache.commons.pool2.ObjectPool;

import javax.inject.Inject;
import javax.script.ScriptEngine;

/**
 * @author Niko Köbler, http://www.n-k.de, @dasniko
 */
public class React {

    @Inject
    private ObjectPool<ScriptEngine> scriptEnginePool;

    String render(String function, Object object) {
        try {
            ScriptEngine scriptEngine = scriptEnginePool.borrowObject();

            String[] parts = function.split("\\.");
            Object html = ((ScriptObjectMirror) scriptEngine.get(parts[0])).callMember(parts[1], object);

            scriptEnginePool.returnObject(scriptEngine);
            return String.valueOf(html);
        } catch (Exception e) {
            throw new IllegalStateException("failed to render react component", e);
        }
    }
}
