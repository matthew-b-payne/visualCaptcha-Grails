package com.sutternow.captcha

import gcaptcha.CaptchaTagLib
import grails.test.mixin.TestFor
import org.junit.Test

/**
 * Created with IntelliJ IDEA.
 * User: mpayne
 * Date: 10/1/13
 * Time: 3:10 PM
 * To change this template use File | Settings | File Templates.
 */

class VisualCaptchaTests {
    @Test()
    void testShuffledImages() {
        ImageManager im  = new ImageManager(workingDirectory: new File('C:\\java\\workspaces\\security\\gcaptcha\\web-app\\images\\visualcaptcha'))

        VisualCaptcha vc = new VisualCaptcha(im)

        println "There are a count of ${vc.imageNames.size()} images"
        List <String> imageNames = vc.imageNames
        List <String> imageNames2 = vc.imageNames

       // test they are not equal after shuffle
       assert !imageNames.equals(imageNames2)
       assert vc.imageNames.size() == 41

        Map images = im.imageMap
        images.each { name, path ->
            println "${name} - ${path}"
        }
    }

    @Test()
    void testImageNamesNotPath() {
        ImageManager im  = new ImageManager(workingDirectory: new File('C:\\java\\workspaces\\security\\gcaptcha\\web-app\\images\\visualcaptcha'))

        VisualCaptcha vc = new VisualCaptcha(im)

        println "There are a count of ${vc.imageNames.size()} images"
        List <String> imageNames = vc.imageNames

        imageNames.each {
            println "${it}"
        }

        assert "Scissors".equals(vc.getImageText("scissors.png"))

    }


}
