<%@ page contentType="text/html;charset=UTF-8" %>
<g:set var="base" value="${request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <r:require modules="captcha"/>
    %{--<r:require modules="captcha"/>--}%
    <meta name="layout" content="main"/>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>visualCaptcha - A cool visual drag-and-drop captcha jQuery plugin by emotionLoop - Demo</title>

    <meta name="viewport" content="width=device-width,initial-scale=1">

</head>
<body class=" hasGoogleVoiceExt">
<h2>Basic Demo of visual captcha</h2>
<div id="wrapper" class="type-0">

    <div id="content">
        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
        </g:if>

        <g:if test="${flash.error}">
            <div class="message_error">${flash.error}</div>
        </g:if>

        <g:form name="frm_sample"  controller="captchaDemo"  action="sampleSubmission" method="post">
            <input type="hidden" name="form_submit" value="1" readonly="readonly">

            <p><label for="name">Name:</label> <input type="text" name="name" id="name" value="" size="30"></p>
            <cap:captcha  fieldName="IAmYourFieldName" formId="frm_sample" accessibilityFieldName="dude" layoutType="0"/>
            <p class="submit"><button type="submit" name="submit-bt">Submit form</button></p>

            <p><small>CSS types: <a href="?layoutType=0">Horizontal (default)</a> | <a href="?layoutType=1">Vertical</a>
            </small></p>
        </g:form>
    </div></div>
%{--<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>--}%
%{--<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>--}%
%{--<script src="js/visualcaptcha/visualcaptcha.src.js"></script>--}%
</body>

</html>