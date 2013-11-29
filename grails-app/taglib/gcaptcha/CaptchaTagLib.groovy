package gcaptcha

import com.sutternow.captcha.CaptchaValue
import com.sutternow.captcha.ImageManager
import com.sutternow.captcha.VisualCaptcha

class CaptchaTagLib {

    static namespace = "cap"


    String resourcePath = servletContext.getRealPath('/visualcaptcha')
    ImageManager imageManager = new ImageManager(workingDirectory: new File('/images/visualcaptcha' + File.pathSeparator + 'choices'))
    VisualCaptcha vc = new VisualCaptcha(imageManager,resourcePath )

    def captcha = { attrs ->
        def formId = attrs.formId
        def fieldName = attrs.fieldName ?: UUID.randomUUID().toString() //attrs.fieldName
        def accessibilityFieldName = attrs.accessibilityFieldName
        Long datetime = new Date().time
        int layoutType = attrs.int("layoutType") ?: 0 // horizontal (0) | vertial (1)
        def audioFile = 'audio.php' // TODO, use audio controller, long audio files and get random one
        String base = request.contextPath
        def imageLimit = (layoutType == 0) ? 5 : 4 // only 4 for Vertical
        CaptchaValue captchaValue = vc.getRandomCaptchaValue(imageLimit)
        captchaValue.fieldName=fieldName
        session.captchaValue = captchaValue


        out << """
              <script>
            window.vCVals = {
	        'f': '$formId',
	        'n': '$fieldName',
	        'a': '$accessibilityFieldName'
            };
            </script>
    <div class="eL-captcha type-$layoutType clearfix">
	<p class="eL-explanation type-$layoutType"><span class="desktopText">Drag the <strong>${captchaValue.imageText}</strong> 'to the circle on the side'.</span><span class="mobileText">'Touch the'<strong>${captchaValue.imageText}</strong> 'to move it to the circle on the side'.</span></p>
	<div class="eL-possibilities type-${layoutType} clearfix"> """
        captchaValue.imageChoices.eachWithIndex { imageName, idx ->
            String path = vc.imageMap.get(imageName).value
            out << """<img src="${request.contextPath}/${vc.imagesPath}/choices/${imageName}?i=${idx + 1}&r=${datetime}" class="vc-${idx + 1}" data-value="${imageName}" alt="" title="">"""
        }
        out << """</div>
	<div class="eL-where2go type-${layoutType} clearfix">
		<p>'Drop<br>Here'</p>
	</div>
	<p class="eL-accessibility type-${layoutType}"><a href="#" title="Accessibility option: listen to a question and answer it!"><img src="${vc.imagesPath}/accessibility.png" alt="Accessibility option: listen to a question and answer it!"></a></p>
	<div class="eL-accessibility type-${layoutType}">
		<p>Type below the <strong> answer </strong> to what you hear. Numbers or words:</p>
		<audio preload="preload">
			<source src="${base}/captcha/audio?t=ogg&amp;r=${datetime}" type="audio/ogg">
			<source src="${base}/captcha/audio?t=mp3&amp;r=${datetime}" type="audio/mpeg">
			Your browser does not support the audio element.
		</audio>
	</div>
</div>

             """

        /*User currentUser = session[User.SESSION_NAME]

        if(currentUser){
            out << "User: <strong>${currentUser.name}</strong> "
            out << """[${link(action:"logout", controller:"simpleLogin"){"Logout"}}]"""
        }   else {
            out << """[${link(action:"login", controller:"simpleLogin"){"Login"}}]"""
        }*/
    }


    private void setNewValue() {
        //User user = session[AdminKeys.ADMIN_USER_SESSION_KEY]


    }

    /**
     * Show body if user has elevated super privledges
     *
     * @attr user REQUIRED the field name
     */
    def showIfSU = { attrs, body ->
        /* User user = session[AdminKeys.ADMIN_USER_SESSION_KEY]
         if (user && !user.roles.disjoint(suRoles)  ) {
             out << body()
         }*/
    }

}
