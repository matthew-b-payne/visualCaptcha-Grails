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
<h2>Basic Demo of visual captcha</h2>
<div id="wrapper" class="type-0">
    <div id="content">
        <g:form name="frm_sample" controller="filterProtectedDemo"  action="sampleSubmission" method="post">
            <input type="hidden" name="form_submit" value="1" readonly="readonly">

            <p><label for="name">Name:</label> <input type="text" name="name" id="name" value="" size="30"></p>
            <cap:captcha  formId="frm_sample" accessibilityFieldName="dude" layoutType="0"/>
            <p class="submit"><button type="submit" name="submit-bt">Submit form</button></p>

            <p><small>CSS types: <a href="?layoutType=0">Horizontal (default)</a> | <a href="?layoutType=1">Vertical</a>
            </small></p>
        </g:form>
    </div></div>
</body>
</html>