<%--
  Created by IntelliJ IDEA.
  User: yzlin
  Date: 2019/4/13
  Time: 0:46
  To change this template use File | Settings | File Templates.
  param
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<nav class="navbar navbar-default  navbar-fixed-top">
    <div class="container-fluid">
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><s:a action="index">首页</s:a></li>
                <li><s:a action="buyTicket">机票预订</s:a></li>
                <li><s:a action="showTicket">预订信息维护</s:a></li>
                <li><s:a action="loginOut">退出系统</s:a></li>
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <c:if test="${sessionScope.user == null}">
                    <li><a href="#" data-toggle="modal" data-target="#myModal">登录</a></li>
                    <li><s:a action="index">注册</s:a></li>
                </c:if>
                <c:if test="${sessionScope.user != null}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                           aria-expanded="false">
                                欢迎你：${sessionScope.user.userName} | 下拉菜单<span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li><s:a action="changePassword">修改密码</s:a></li>
                            <li><s:a action="loginOut">退出登录</s:a></li>
                        </ul>
                    </li>
                </c:if>
            </ul>
            <form action="/searchTicket" class="navbar-form navbar-right" method="get">
                <div class="form-group">
                    <input type="text" name="name" class="form-control" placeholder="按名字查找机票">
                </div>
                <button type="submit" class="btn btn-default" style="font-size: 20px"><span
                        class="glyphicon glyphicon-zoom-in"></span></button>
            </form>
        </div>
    </div>
</nav>
<div style="height: 50px"></div>

<c:if test="${sessionScope.user == null}">
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h2>在线机票预订系统</h2>
                </div>
                <div class="modal-body">
                    <s:form action="login" cssClass="form-horizontal" theme="bootstrap">
                        <s:textfield name="user.userName" key="login.username"/>
                        <s:password name="user.password" key="login.password"/>
                        <s:submit value = "%{getText('login.submit')}" cssClass="btn btn-info col-md-2 col-md-offset-3 btn-lg" />
                        <s:reset value = "%{getText('login.reset')}" cssClass="btn btn-warning col-md-2 col-md-offset-2 btn-lg"/>
                    </s:form>
                </div>
            </div>
        </div>
    </div>
</c:if>