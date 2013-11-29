<%--
  Created by IntelliJ IDEA.
  User: mpayne
  Date: 11/28/12
  Time: 4:16 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title><g:layoutTitle default="RUT UI"/></title>

    <!-- Le fav and touch icons -->
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
    %{--		<link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">--}%
    %{--		<link rel="apple-touch-icon" sizes="72x72" href="${resource(dir: 'images', file: 'apple-touch-icon-72x72.png')}">--}%
    %{--		<link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-114x114.png')}">--}%

    <r:layoutResources/>
    <g:layoutHead/>
</head>
<body>
<div>  <g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>
    <g:if test="${flash.error}">
        <div class="alert alert-error">
            ${flash.error}
        </div>
    </g:if>
    <g:layoutBody/></div>
<r:layoutResources/>
</body>
</html>