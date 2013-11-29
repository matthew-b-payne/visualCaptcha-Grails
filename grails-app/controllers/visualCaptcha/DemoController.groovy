package visualCaptcha

import com.sutternow.captcha.CaptchaValue

class DemoController {

    def sampleSubmission() {
        def name = params.name

        CaptchaValue captchaValue = session.captchaValue
        if (captchaValue == null) {
            render "your test failed"
        } else {
            captchaValue.fieldName
            String answer = params[captchaValue.fieldName]

            if (answer == captchaValue.imageName) {
                render """Your name is ${name} and
                and you answer is ${answer}

            """
            } else {
                render "answer is incorrect test"
            }
        }
    }
}
