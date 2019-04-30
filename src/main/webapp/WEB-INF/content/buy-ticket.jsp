<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: yzlin
  Date: 2019/4/2
  Time: 17:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><s:text name="buyTicket.head.title"/></title>
    <script src="<c:url value="/static/js/jquery.min.js"/>"></script>
    <sb:head/>
</head>
<style>
    label {
        font-size: 1.5em;
    }
</style>
<body>
<jsp:include page="navbar.jsp"/>
<div class="container">
    <div class="col-md-12">
        <div class="page-header">
            <h1><s:text name="buyTicket.title"/></h1>
        </div>
        <s:form action="doBuyTicket" cssClass="form-horizontal well" theme="bootstrap">
                <s:textfield name="ticket.name" key="buyTicket.name" cssClass="input-lg" value="%{#session.user.userName}"/>
                <s:radio name="ticket.sex" labelposition="inline"
                         list="%{#{'male':getText('buyTicket.sex.male'),'female':getText('buyTicket.sex.female')}}"
                         key="buyTicket.sex" cssErrorClass="foo" value="'male'"/>
                <s:textfield name="ticket.originating" key="buyTicket.originating"  cssClass="input-lg"/>
                <s:textfield name="ticket.destination" key="buyTicket.destination"  cssClass="input-lg"/>
                <s:textfield name="ticket.departureDate" key="buyTicket.departureDate"  cssClass="input-lg"/>
                <s:textfield name="ticket.idCard" key="buyTicket.idCard"  cssClass="input-lg"/>
                <s:submit value = "%{getText('buyTicket.submit')}" cssClass="btn btn-info col-md-2 col-md-offset-3 btn-lg" />
                <s:reset value = "%{getText('buyTicket.reset')}" cssClass="btn btn-warning col-md-2 col-md-offset-2 btn-lg"/>
        </s:form>
    </div>
</div>
<script src="${pageContext.request.contextPath}/static/js/canvas-nest.min.js"
        color="0,0,255" opacity='0.7' zIndex="-2" count="99"></script>
</body>
</html>
