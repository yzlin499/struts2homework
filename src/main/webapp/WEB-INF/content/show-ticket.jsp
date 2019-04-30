<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: yzlin
  Date: 2019/4/3
  Time: 1:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><s:text name="showTicket.head.title"/></title>
    <script src="<c:url value="/static/js/jquery.min.js"/>"></script>
    <sb:head/>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<div class="container">
    <div class="col-md-12">
        <div class="page-header">
            <h1><s:text name="showTicket.title"/></h1>
            <nav aria-label="...">
                <ul class="pager">
                    <li class="previous ${requestScope.page==1?'disabled':''}"><a href="${requestScope.page==1?'#':(requestScope.actionName.concat("?page=").concat(requestScope.page-1).concat("&name=").concat(requestScope.name))}"><span aria-hidden="true">&larr;</span> 上一页</a></li>
                    <li><a>第${requestScope.page}页</a></li>
                    <li class="next ${requestScope.page>=requestScope.pageSum?'disabled':''}"><a href="${requestScope.page>=requestScope.pageSum?'#':requestScope.actionName.concat("?page=").concat(requestScope.page+1).concat("&name=").concat(requestScope.name)}">下一页 <span aria-hidden="true">&rarr;</span></a></li>
                </ul>
            </nav>
        </div>
        <jsp:include page="ticket-list.jsp"/>
    </div>
</div>

<script src="${pageContext.request.contextPath}/static/js/canvas-nest.min.js"
        color="0,0,255" opacity='0.7' zIndex="-2" count="99"></script>
</body>
</html>
