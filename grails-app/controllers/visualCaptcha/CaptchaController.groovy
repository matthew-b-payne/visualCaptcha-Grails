package visualCaptcha

import com.sutternow.captcha.VisualCaptcha

class CaptchaController {

    VisualCaptcha visualCaptcha

    def index() {


    }


    def audio(String t) {
    // reference adding headers  http://java.dzone.com/articles/grails-goodness-using-header
        header 'X-Powered-By', "Grails: $grailsVersion, Groovy: $groovyVersion"
        header 'Pragma: public'
        header 'Expires: 0'
        header 'Cache-Control: must-revalidate, post-check=0, pre-check=0'
        header 'Cache-Control: private'
        def mimeType, extension, file

        visualCaptcha.audiosPath

        switch ( t ) {
            case "ogg":
                mimeType = 'audio/ogg'
                extension = 'ogg'
                $file = str_replace( '.mp3', '.ogg', $file );
        // lets fall through
            case "mp3":
                mimeType = 'audio/mp3'
                extension = 'mp3'
                $file = str_replace( '.mp3', '.ogg', $file );

            default:
                mimeType = 'audio/mpeg';
                extension = 'mp3';
        }




        header "Content-Type: $mimeType"
        header 'Content-Transfer-Encoding: binary'
        header( 'Content-Length: ' . filesize($file) )

        file = new File(params.fileDir)
        response.setContentType("application/octet-stream")
        response.setHeader("Content-disposition", "attachment;filename=${file.getName()}")

        response.outputStream << file.newInputStream()


    }

}
