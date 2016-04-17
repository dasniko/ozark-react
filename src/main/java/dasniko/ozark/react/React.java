package dasniko.ozark.react;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.Invocable;
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
public class React {

    private static final String DEFAULT_JS_RESOURCE_PATH = "/js";
    private static final String RESOURCE_PATH = System.getProperty("mvc.reactjs.resourcePath", DEFAULT_JS_RESOURCE_PATH);

    private ThreadLocal<ScriptEngine> engineHolder = ThreadLocal.withInitial(() -> {
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

    String render(String function, Object object) {
        try {
            Object html;
            if (function.contains(".")) {
                String[] parts = function.split("\\.");
                html = ((ScriptObjectMirror) engineHolder.get().get(parts[0])).callMember(parts[1], object);
            } else {
                html = ((Invocable) engineHolder.get()).invokeFunction(function, object);
            }
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