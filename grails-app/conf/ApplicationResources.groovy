modules = {

    captcha {
        dependsOn 'jquery', 'jquery-ui'
        resource url: 'css/visualcaptcha/visualcaptcha.css'
        resource id: 'js', url: 'js/visualcaptcha//visualcaptcha.src.js'
    }

}