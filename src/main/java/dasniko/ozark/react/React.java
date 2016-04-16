package dasniko.ozark.react;

import javax.script.Invocable;
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
            nashorn.eval(read("/META-INF/resources/webjars/react/0.14.8/react.min.js"));
            nashorn.eval(read("/js/bookBox.js"));
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
        return nashorn;
    });

    public String render(Object object) {
        try {
            Object html = ((Invocable) engineHolder.get()).invokeFunction("renderServer", object);
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