<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: yzlin
  Date: 2019/4/30
  Time: 19:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
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
        <s:form action="changeNewPassword" method="POST" theme="bootstrap">
            <div class="form-group ">
                <label class="control-label" >用户名</label>
                <div class="controls">
                    <p>${sessionScope.user.userName}</p>
                </div>
            </div>
            <s:password name="oldPassword" label="旧密码"/>
            <s:password name="newPassword" label="新密码"/>
            <s:password name="newPasswordAgain" label="确认新密码"/>
            <s:submit value="确认" cssClass="btn btn-info col-md-2 col-md-offset-3 btn-lg"/>
            <s:reset value="重置" cssClass="btn btn-warning col-md-2 col-md-offset-2 btn-lg"/>
        </s:form>
        <s:fielderror>
            <s:param>error</s:param>
        </s:fielderror>
    </div>
</div>

<script src="${pageContext.request.contextPath}/static/js/canvas-nest.min.js"
        color="0,0,255" opacity='0.7' zIndex="-2" count="99"></script>
</body>
</html>
