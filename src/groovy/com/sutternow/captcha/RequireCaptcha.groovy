package com.sutternow.captcha


import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Target([ElementType.FIELD, ElementType.TYPE]) // Annotation is for actions as well as controller so target is field and for class
@Retention(RetentionPolicy.RUNTIME) // We need it at run time to identify the annotated controller and action

@interface RequireCaptcha {
    String[] include() default []  // If set to include some of the actions of controller

    String[] exclude() default [] // To exclude some of the actions of controller

    String fieldName() default "captcha" // Default controller when the field not in session is set to index page

    String onFailController() default "error" // Default controller when the field not in session is set to index page

    String onFailAction() default "error" // Default action when the field not in session is set to index page

}
