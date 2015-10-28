package dasniko.ozark.react;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

import javax.script.ScriptException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @author Niko Köbler, http://www.n-k.de, @dasniko
 */
public class React {

    private ThreadLocal<NashornScriptEngine> engineHolder = ThreadLocal.withInitial(() -> {
        NashornScriptEngine nashorn = (NashornScriptEngine) new NashornScriptEngineFactory().getScriptEngine();
        try {
            nashorn.eval(read("nashorn-polyfill.js"));
            nashorn.eval(read("META-INF/resources/webjars/react/0.13.3/react.js"));
            nashorn.eval(read("META-INF/resources/webjars/showdown/0.3.1/src/showdown.js"));
            nashorn.eval(read("bookBox.js"));
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
        return nashorn;
    });

    public  String render(String function, Object object) {
        try {
            Object html = engineHolder.get().invokeFunction(function, object);
            return String.valueOf(html);
        }
        catch (Exception e) {
            throw new IllegalStateException("failed to render react component", e);
        }
    }

    private Reader read(String path) {
        InputStream in = getClass().getClassLoader().getResourceAsStream(path);
        return new InputStreamReader(in);
    }
}