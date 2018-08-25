<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    java.util.Date currentTime = new java.util.Date();//得到当前系统时间
    String currentDateTime = formatter.format(currentTime); //将日期时间格式化
%>

<c:set var="ctx" value="${pageContext.request.contextPath}/"/>
<c:set var="currentTimeMillis" value="<%=System.currentTimeMillis()%>"/>
<c:set var="currentDateTime" value="<%=currentDateTime%>"/>
<c:set var="version" value="${currentTimeMillis}"/>

