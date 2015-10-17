<%@ page import="javax.enterprise.inject.spi.CDI" %>
<%@ page import="javax.mvc.MvcContext" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    MvcContext mvc = CDI.current().select(MvcContext.class).get();
    response.sendRedirect(mvc.getContextPath() + mvc.getApplicationPath() + "/react");
%>
