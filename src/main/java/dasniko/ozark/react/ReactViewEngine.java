package dasniko.ozark.react;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.ozark.engine.ServletViewEngine;
import org.glassfish.ozark.engine.ViewEngineContextImpl;

import javax.annotation.Priority;
import javax.mvc.Models;
import javax.mvc.engine.Priorities;
import javax.mvc.engine.ViewEngineContext;
import javax.mvc.engine.ViewEngineException;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Niko Köbler, http://www.n-k.de, @dasniko
 */
@Priority(Priorities.FRAMEWORK)
public class ReactViewEngine extends ServletViewEngine {

    private static final String viewPrefix = "react:";

    React react = new React();
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean supports(String view) {
        return view.startsWith(viewPrefix);
    }

    @Override
    public void processView(ViewEngineContext context) throws ViewEngineException {
        // parse view and extract the actual template and the react.js function to call
        String view = context.getView();
        String function;
        String template;
        try {
            URI uri = new URI(view);
            String[] specificParts = uri.getSchemeSpecificPart().split("\\?");
            template = specificParts[0];
            String query = specificParts[1];
            Map<String, String> params = parse(query);
            function = params.get("function");
        } catch (URISyntaxException e) {
            throw new ViewEngineException(e);
        }

        // get "data" from model
        Models models = context.getModels();
        Object data = models.get("data");

        // call given js function on data
        String content = react.render(function, data);

        // and put results as string in model
        models.put("content", content);
        try {
            models.put("data", mapper.writeValueAsString(data));
        } catch (JsonProcessingException e) {
            throw new ViewEngineException(e);
        }

        // create a new context with the actual view and forward to ServletViewEngine
        ViewEngineContext ctx = new ViewEngineContextImpl(template, models,
                context.getRequest(), context.getResponse(), context.getUriInfo(),
                context.getResourceInfo(), context.getConfiguration());

        try {
            forwardRequest(ctx, "*.jsp", "*.jspx");
        } catch (ServletException | IOException e) {
            throw new ViewEngineException(e);
        }
    }

    private Map<String, String> parse(final String query) {
        return Arrays.asList(query.split("&")).stream().map(p -> p.split("=")).collect(Collectors.toMap(s -> decode(index(s, 0)), s -> decode(index(s, 1))));
    }

    private static <T> T index(final T[] array, final int index) {
        return index >= array.length ? null : array[index];
    }

    private static String decode(final String encoded) {
        try {
            return encoded == null ? null : URLDecoder.decode(encoded, "UTF-8");
        } catch(final UnsupportedEncodingException e) {
            throw new RuntimeException("Impossible: UTF-8 encoding is required.", e);
        }
    }
}
