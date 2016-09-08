package dasniko.ozark.react;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.mvc.Models;
import javax.mvc.engine.Priorities;
import javax.mvc.engine.ViewEngine;
import javax.mvc.engine.ViewEngineContext;
import javax.mvc.engine.ViewEngineException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * @author Niko Köbler, http://www.n-k.de, @dasniko
 */
@Priority(Priorities.FRAMEWORK)
public class ReactViewEngine implements ViewEngine {

    @Inject
    React react;
    @Inject
    ServletContext servletContext;

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean supports(String view) {
        return view.startsWith("react:");
    }

    @Override
    public void processView(ViewEngineContext context) throws ViewEngineException {
        // parse view and extract the actual template and the react.js function to call
        String view = context.getView(); // react:react.jsp?function=BookApp.renderServer
        String template = view.substring("react:".length(), view.indexOf("?"));
        String function = view.substring(view.indexOf("function=") + 9);

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

        try {
            processRequest(context, models, template);
        } catch (ServletException | IOException e) {
            throw new ViewEngineException(e);
        }
    }

    private void processRequest(final ViewEngineContext context, final Models models, final String view) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = null;
        HttpServletRequest request = context.getRequest();

        for (String name : models) {
            request.setAttribute(name, models.get(name));
        }

        final String viewExtension = getViewExtension(view);
        for (Map.Entry<String, ? extends ServletRegistration> e : servletContext.getServletRegistrations().entrySet()) {
            final Collection<String> mappings = e.getValue().getMappings();
            if (mappings.contains(viewExtension)) {
                requestDispatcher = servletContext.getNamedDispatcher(e.getKey());
                request = new HttpServletRequestWrapper(context.getRequest()) {
                    @Override
                    public String getRequestURI() {
                        return resolveView(context, view);
                    }

                    @Override
                    public String getServletPath() {
                        return getRequestURI();
                    }

                    @Override
                    public String getPathInfo() {
                        return null;
                    }

                    @Override
                    public StringBuffer getRequestURL() {
                        return new StringBuffer(getRequestURI());
                    }
                };
            }
        }

        if (requestDispatcher == null) {
            requestDispatcher = servletContext.getRequestDispatcher(resolveView(context, view));
        }

        requestDispatcher.forward(request, context.getResponse());
    }

    private String getViewExtension(final String view) {
        return "*" + view.substring(view.lastIndexOf("."));
    }

    private String resolveView(final ViewEngineContext context, final String view) {
        if (!view.startsWith("/")) {
            String viewFolder = (String) context.getConfiguration().getProperty(VIEW_FOLDER);
            viewFolder = viewFolder == null ? DEFAULT_VIEW_FOLDER : viewFolder;
            viewFolder += !viewFolder.endsWith("/") ? "/" : "";
            return viewFolder + view;
        }
        return view;
    }
}
