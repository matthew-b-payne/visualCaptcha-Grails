package visualCaptcha

import com.sutternow.captcha.AnswerInfo
import com.sutternow.captcha.CaptchaValue
import com.sutternow.captcha.VisualCaptcha
import grails.converters.JSON
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.plugins.support.aware.GrailsApplicationAware
import static javax.servlet.http.HttpServletResponse.*


class CaptchaController implements  GrailsApplicationAware  {
    GrailsApplication grailsApplication

    VisualCaptcha visualCaptcha

    def index() {
    }


    def audio(String t, String r) {
        // reference adding headers  http://java.dzone.com/articles/grails-goodness-using-header

        final String grailsVersion = grailsApplication.metadata.getGrailsVersion()
        final String groovyVersion = GroovySystem.version

        AnswerInfo audioAnswerInfo = visualCaptcha.getAudioAnswer(r)

        if (audioAnswerInfo) {
            header 'X-Powered-By', "Grails: $grailsVersion, Groovy: $groovyVersion"
            header 'Pragma', 'public'
            header 'Expires', '0'
            header 'Cache-Control', 'must-revalidate, post-check=0, pre-check=0'
            header 'Cache-Control', 'private'
            def mimeType, extension, file

            //CaptchaValue captchaValue = session.captchaValue
            File audioFile = audioAnswerInfo.filePath
            switch (t) {
                case "ogg":
                    mimeType = 'audio/ogg'
                    extension = 'ogg'
                    audioFile = new File(audioFile.absolutePath.replace('.mp3', extension))
            // lets fall through

                default:
                    mimeType = 'audio/mpeg';
                    extension = 'mp3';
            }

            header 'Content-Type', mimeType
            header 'Content-Transfer-Encoding', 'binary'
            header 'Content-Length', audioFile.length()
            file = audioAnswerInfo.filePath
            response.setContentType("application/octet-stream")
            response.setHeader("Content-disposition", "attachment;filename=${file.getName()}")
            response.outputStream << file.newInputStream()
        }  else {
            response.status =   SC_FORBIDDEN
            def responseMap = [:]
            responseMap.message = "Not allowed to access audio file"
            render(responseMap as JSON)
        }

    }

}
