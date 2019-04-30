<%--
  Created by IntelliJ IDEA.
  User: yzlin
  Date: 2019/4/1
  Time: 17:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title><s:text name="login.head.title"/></title>
    <script src="<c:url value="/static/js/jquery.min.js"/>"></script>
    <sb:head/>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<div class="container">
    <div class="col-md-12">
        <h1><s:text name="login.title"/></h1>
        <s:fielderror>
            <s:param>login.error</s:param>
        </s:fielderror>
        <s:form action="login" method="POST" theme="bootstrap">
            <s:textfield name="user.userName" key="login.username"/>
            <s:password name="user.password" key="login.password"/>
            <s:submit value = "%{getText('login.submit')}" cssClass="btn btn-info col-md-2 col-md-offset-3 btn-lg" />
            <s:reset value = "%{getText('login.reset')}" cssClass="btn btn-warning col-md-2 col-md-offset-2 btn-lg"/>
        </s:form>
        <hr>
        <h1><s:text name="register.title"/></h1>
        <s:fielderror>
            <s:param>register.error</s:param>
        </s:fielderror>
        <s:form action="register" method="POST" theme="bootstrap">
            <s:textfield name="registerUser.userName" key="register.username"/>
            <s:password name="registerUser.password" key="register.password"/>
            <s:password name="registerUser.passwordAgain" key="register.passwordAgain"/>
            <s:submit value = "%{getText('register.submit')}" cssClass="btn btn-info col-md-2 col-md-offset-3 btn-lg" />
            <s:reset value = "%{getText('register.reset')}" cssClass="btn btn-warning col-md-2 col-md-offset-2 btn-lg"/>
        </s:form>
    </div>
</div>
<script src="${pageContext.request.contextPath}/static/js/canvas-nest.min.js"
        color="0,0,255" opacity='0.7' zIndex="-2" count="99"></script>
</body>
</html>
