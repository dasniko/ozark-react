package dasniko.ozark.react;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @author Niko Köbler, http://www.n-k.de, @dasniko
 */
public class NashornPoolFactory extends BasePooledObjectFactory<ScriptEngine> {

    @Override
    public ScriptEngine create() throws Exception {
        ScriptEngine nashorn = new ScriptEngineManager().getEngineByName("nashorn");

        // add all needed resources for our application
        nashorn.eval(read("nashorn-polyfill.js"));
        nashorn.eval(read("/js/app.js"));

        return nashorn;
    }

    @Override
    public PooledObject<ScriptEngine> wrap(ScriptEngine scriptEngine) {
        return new DefaultPooledObject<>(scriptEngine);
    }

    private Reader read(String path) {
        InputStream in = getClass().getClassLoader().getResourceAsStream(path);
        return new InputStreamReader(in);
    }
}
