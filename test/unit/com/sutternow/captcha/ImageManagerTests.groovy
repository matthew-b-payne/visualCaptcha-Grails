package com.sutternow.captcha

import gcaptcha.CaptchaTagLib
import grails.test.mixin.TestFor

/**
 * Created with IntelliJ IDEA.
 * User: mpayne
 * Date: 10/1/13
 * Time: 2:25 PM
 * To change this template use File | Settings | File Templates.
 */
class ImageManagerTests {
    void testListImageNames() {
        ImageManager im  = new ImageManager(workingDirectory: new File('C:\\java\\workspaces\\security\\gcaptcha\\web-app\\images\\visualcaptcha'))
        List <String> imageNames = im.imageNames

        imageNames.each {
            println it
        }

        Map images = im.imageMap
        images.each { name, path ->
            println "${name} - ${path}"
        }
    }



}
