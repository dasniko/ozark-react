package dasniko.ozark.react;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @author Niko Köbler, http://www.n-k.de, @dasniko
 */
public class React {

    private ThreadLocal<ScriptEngine> engineHolder = ThreadLocal.withInitial(() -> {
        ScriptEngine nashorn = new ScriptEngineManager().getEngineByName("nashorn");
        try {
            nashorn.eval(read("/nashorn-polyfill.js"));
            nashorn.eval(read("/js/app.js"));
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
        return nashorn;
    });

    String render(Object object) {
        try {
            Object html = ((ScriptObjectMirror) engineHolder.get().get("BookApp")).callMember("renderServer", object);
            return String.valueOf(html);
        } catch (Exception e) {
            throw new IllegalStateException("failed to render react component", e);
        }
    }

    private Reader read(String path) {
        InputStream in = getClass().getClassLoader().getResourceAsStream(path);
        return new InputStreamReader(in);
    }
}