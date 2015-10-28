package dasniko.ozark.react;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.ozark.engine.ServletViewEngine;
import org.glassfish.ozark.engine.ViewEngineContextImpl;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.mvc.Models;
import javax.mvc.engine.Priorities;
import javax.mvc.engine.ViewEngineContext;
import javax.mvc.engine.ViewEngineException;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Niko Köbler, http://www.n-k.de, @dasniko
 */
@Priority(Priorities.FRAMEWORK)
public class ReactViewEngine extends ServletViewEngine {

    private static final String viewPrefix = "react:";

    @Inject
    React react;

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean supports(String view) {
        return view.startsWith(viewPrefix);
    }

    @Override
    public void processView(ViewEngineContext context) throws ViewEngineException {
        // parse view and extract the actual template and the react.js function to call
        String view = context.getView();
        String[] viewParts = view.substring(viewPrefix.length()).split("\\?");
        if (viewParts.length < 2) {
            throw new ViewEngineException("You have to specify at least a view and a function (e.g. react:view.jsp?function=renderOnServer)!");
        }

        String template = viewParts[0];

        Map<String, String> params = parseQueryString(viewParts[1]);
        String function = params.get("function");

        String dataKey = params.getOrDefault("data", "data");
        String contentKey = params.getOrDefault("content", "content");

        // get "data" from model
        Models models = context.getModels();
        Object data = models.get(dataKey);

        // call given js function on data
        String content = react.render(function, data);

        // and put results as string in model
        models.put(contentKey, content);
        try {
            models.put(dataKey, mapper.writeValueAsString(data));
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

    private Map<String, String> parseQueryString(final String query) {
        return Arrays.asList(query.split("&")).stream().map(p -> p.split("=")).collect(Collectors.toMap(s -> s[0], s -> s[1]));
    }
}
