<%@ page contentType="text/html;charset=UTF-8" %>
<g:set var="base" value="${request.contextPath}"/>
<html>
<head>
    <meta name="layout" content="main" />
  <title>Demo page</title>
    <link rel="stylesheet" href="${resource(dir:'css/visualcaptcha',file:'visualcaptcha.css')}" />
    <r:require module="captcha"/>
</head>
<body>
       <cap:captcha fieldName="secret" formId="formie" accessibilityFieldName="dude"/>
</body>
</html>