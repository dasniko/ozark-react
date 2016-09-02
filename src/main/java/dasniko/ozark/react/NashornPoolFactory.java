package dasniko.ozark.react;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Niko Köbler, http://www.n-k.de, @dasniko
 */
public class NashornPoolFactory extends BasePooledObjectFactory<ScriptEngine> {

    private static final String DEFAULT_JS_RESOURCE_PATH = "/js";
    private static final String RESOURCE_PATH = System.getProperty("mvc.reactjs.resourcePath", DEFAULT_JS_RESOURCE_PATH);

    @Override
    public ScriptEngine create() throws Exception {
        ScriptEngine nashorn = new ScriptEngineManager().getEngineByName("nashorn");

        // initial basically needed files for dealing with react
        List<String> jsResources = new ArrayList<>();
        jsResources.add("nashorn-polyfill.js");

        // add all resources from custom application
        jsResources.addAll(getAllFilesFromResourcePath());
        // and load/evaluate them within nashorn
        jsResources.forEach(p -> {
            try {
                nashorn.eval(read(p));
            } catch (ScriptException e) {
                throw new RuntimeException(e);
            }
        });

        return nashorn;
    }

    @Override
    public PooledObject<ScriptEngine> wrap(ScriptEngine scriptEngine) {
        return new DefaultPooledObject<>(scriptEngine);
    }

    private List<String> getAllFilesFromResourcePath() {
        URL url = this.getClass().getClassLoader().getResource(RESOURCE_PATH);
        if (url != null && url.getProtocol().equals("file")) {
            try {
                return Arrays.stream(new File(url.toURI()).list()).map(p -> RESOURCE_PATH + "/" + p).collect(Collectors.toList());
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        } else {
            // TODO do something
            return new ArrayList<>();
        }
    }

    private Reader read(String path) {
        InputStream in = getClass().getClassLoader().getResourceAsStream(path);
        return new InputStreamReader(in);
    }
}
