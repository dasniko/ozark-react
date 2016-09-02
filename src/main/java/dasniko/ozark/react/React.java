package dasniko.ozark.react;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.apache.commons.pool2.ObjectPool;

import javax.inject.Inject;
import javax.script.Invocable;
import javax.script.ScriptEngine;

/**
 * @author Niko Köbler, http://www.n-k.de, @dasniko
 */
public class React {

    @Inject
    private ObjectPool<ScriptEngine> scriptEnginePool;

    String render(String function, Object object) {
        try {
            Object html;
            ScriptEngine scriptEngine = scriptEnginePool.borrowObject();
            if (function.contains(".")) {
                String[] parts = function.split("\\.");
                html = ((ScriptObjectMirror) scriptEngine.get(parts[0])).callMember(parts[1], object);
            } else {
                html = ((Invocable) scriptEngine).invokeFunction(function, object);
            }
            scriptEnginePool.returnObject(scriptEngine);
            return String.valueOf(html);
        } catch (Exception e) {
            throw new IllegalStateException("failed to render react component", e);
        }
    }
}
