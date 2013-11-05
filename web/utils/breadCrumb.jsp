<%@page import="javax.faces.context.FacesContext"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<div id="navbar">
  <jsp:useBean class="sfunctionals.BreadCrumb" id="breadCrumb" scope="session" />
  <% breadCrumb.add(((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURL().toString(), request.getParameter("title")); %>
  <c:if test="${breadCrumb.links.size() - 2 >= 0}">
    <c:forEach items="${breadCrumb.links}" end="${breadCrumb.links.size() - 2}" var="link">
        <a href="${link.href}">${breadCrumbMsg.getString(link.title)}</a> &raquo;
    </c:forEach>
  </c:if>
  <b><h:outputText value="#{breadCrumbMsg.getString(breadCrumb.links.getLast().title)}" /></b>
</div>