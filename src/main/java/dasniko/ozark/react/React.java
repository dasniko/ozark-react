package dasniko.ozark.react;

import jdk.nashorn.api.scripting.NashornScriptEngine;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @author Niko Köbler, http://www.n-k.de, @dasniko
 */
public class React {

    private ThreadLocal<NashornScriptEngine> engineHolder = new ThreadLocal<NashornScriptEngine>() {
        @Override
        protected NashornScriptEngine initialValue() {
            NashornScriptEngine nashornScriptEngine = (NashornScriptEngine) new ScriptEngineManager().getEngineByName("nashorn");
            try {
                nashornScriptEngine.eval(read("nashorn-polyfill.js"));
                nashornScriptEngine.eval(read("META-INF/resources/webjars/react/0.13.3/react.js"));
                nashornScriptEngine.eval(read("META-INF/resources/webjars/showdown/0.3.1/src/showdown.js"));
                nashornScriptEngine.eval(read("bookBox.js"));
            } catch (ScriptException e) {
                throw new RuntimeException(e);
            }
            return nashornScriptEngine;
        }
    };

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