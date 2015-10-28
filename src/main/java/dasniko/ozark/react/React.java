package dasniko.ozark.react;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

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
public class React {

    private static final String DEFAULT_JS_RESOURCE_PATH = "/js";
    private static final String RESOURCE_PATH = System.getProperty("mvc.reactjs.resourcePath", DEFAULT_JS_RESOURCE_PATH);

    private ThreadLocal<NashornScriptEngine> engineHolder = ThreadLocal.withInitial(() -> {
        NashornScriptEngine nashorn = (NashornScriptEngine) new NashornScriptEngineFactory().getScriptEngine();

        // initial basically needed files for dealing with react
        List<String> jsResources = Arrays.asList(
                "nashorn-polyfill.js",
                "META-INF/resources/webjars/react/0.13.3/react.js"
        );

        // add all resources from custom application
        jsResources.addAll(getAllFilesFromResourcePath());
        // and load/evaluate them within nashorn
        jsResources.stream().map(p -> {
            try {
                return nashorn.eval(read(p));
            } catch (ScriptException e) {
                throw new RuntimeException(e);
            }
        });

        return nashorn;
    });

    private List<String> getAllFilesFromResourcePath() {
        URL url = this.getClass().getClassLoader().getResource(RESOURCE_PATH);
        if (url != null && url.getProtocol().equals("file")) {
            try {
                return Arrays.asList(new File(url.toURI()).list()).stream().map(p -> RESOURCE_PATH + "/" + p).collect(Collectors.toList());
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        } else {
            // TODO do something
            return new ArrayList<>();
        }
    }

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