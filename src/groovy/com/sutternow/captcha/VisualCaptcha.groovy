package com.sutternow.captcha

import com.sutternow.commons.RandomUtil
import groovy.util.logging.Slf4j
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsHttpSession
import org.springframework.util.DigestUtils

import java.security.MessageDigest

import static java.util.Collections.shuffle

/**
 * Created with IntelliJ IDEA.
 * User: mpayne
 * Date: 10/1/13
 * Time: 10:44 AM
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
class VisualCaptcha {

    Random rand = new Random()
    private formId = 'frm_captcha';
    private type = 0;
    private fieldName = 'captcha-value';
    private accessibilityFieldName = 'captcha-accessibility-value';
    private html = '';
    private hash = '';
    private hashSalt = '';
    private answers = []
    private options = []
    private optionsProperties = []
    private accessibilityOptions = []
    private accessibilityFile = '';
    private accessibilityAnswer = '';
    private value = '';
    private valueProperties = []
    private ImageManager imageManager
    private htmlClass = 'inc/visualcaptcha.class.html.php';
    public imagesPath = 'images/visualcaptcha';

    public audioPath = 'audio';

    String resourcePath;

    public imageFile = 'image.php';
    public audioFile = 'audio.php';
    Map<String, String> imageMap
    List<AnswerInfo> audioFiles = []


    /*  Map<String,String> imageMap = ['Airplane':'airplane.png', 'Ballons':'ballons', 'Camera': 'camera.png',
      'Car': 'car.png', 'Cat': 'cat.png',]
  */

    public VisualCaptcha(ImageManager imageManager) {
        imageManager = imageManager
        imageMap = imageManager.imageMap
        loadAudio()
    }

    public VisualCaptcha(ImageManager imageManager, String resourcePath) {
        imageManager = imageManager
        imageMap = imageManager.imageMap
        this.resourcePath = resourcePath
    }

    @Deprecated
    public VisualCaptcha() {

        /*imageManager = imageManager
        imageMap = imageManager.imageMap*/
    }

    String  md5Hash(String somethingToHash){
        MessageDigest.getInstance("MD5").
                digest(somethingToHash.getBytes("UTF-8")).
                encodeHex().
                toString()
    }


    public void init() {
        loadAudio()
    }

    private void loadAudio() {

        audioFiles << new AnswerInfo('10', new File(resourcePath + File.separatorChar + '5times2.mp3'))
        audioFiles << new AnswerInfo('20',  new File(resourcePath + File.separatorChar + '2times10.mp3'))
        audioFiles << new AnswerInfo('6',  new File(resourcePath + File.separatorChar + '5plus1.mp3'))
        audioFiles << new AnswerInfo('7',  new File(resourcePath + File.separatorChar + '4plus3.mp3'))
        audioFiles << new AnswerInfo('4',  new File(resourcePath + File.separatorChar + 'add3to1.mp3'))
        audioFiles << new AnswerInfo('green',  new File(resourcePath + File.separatorChar + 'addblueandyellow.mp3'))
        audioFiles << new AnswerInfo('white',  new File(resourcePath + File.separatorChar + 'milkcolor.mp3'))
        audioFiles << new AnswerInfo('2',  new File(resourcePath + File.separatorChar + 'divide4by2.mp3'))
        audioFiles << new AnswerInfo('yes',  new File(resourcePath + File.separatorChar + 'sunastar.mp3'))
        audioFiles << new AnswerInfo('no',  new File(resourcePath + File.separatorChar + 'yourobot.mp3'))
        audioFiles << new AnswerInfo('12',  new File(resourcePath + File.separatorChar + '6plus6.mp3'))
        audioFiles << new AnswerInfo('100',  new File(resourcePath + File.separatorChar + '99plus1.mp3'))
        audioFiles << new AnswerInfo('blue',  new File(resourcePath + File.separatorChar + 'skycolor.mp3'))
        audioFiles << new AnswerInfo('3',  new File(resourcePath + File.separatorChar + 'after2.mp3'))
        audioFiles << new AnswerInfo('24',  new File(resourcePath + File.separatorChar + '12times2.mp3'))
        audioFiles << new AnswerInfo('5',  new File(resourcePath + File.separatorChar + '4plus1.mp3'))

        audioFiles.each {
             it.encryptedName = RandomUtil.getRandomId()
        }

        audioPath.eachFileMatch( ~".*${prefix}.*mp3" ) {
            println it.name
            audioFiles << it
        }
    }


    AnswerInfo getAudioAnswer(String id) {
       return audioFiles.find{
            it.encryptedName==id
        }
    }





    public void reload() {
        imageMap = imageManager.imageMap
    }

    public List<String> getImageNames() {
        List<String> imageNames = imageMap.keySet().toList()
        java.util.Collections.shuffle(imageNames)
        return imageNames
    }

    public String getImageText(String imageName) {
        String imageText = imageName.replace(".png", "")
        return imageText.capitalize()

    }


    public CaptchaValue getRandomCaptchaValue(int numberOfChoices) {
        List<String> imageNames = imageMap.keySet().toList()
        shuffle(imageNames)
        List<String> choices = imageNames[0..(numberOfChoices - 1)]
        shuffle(choices)

        int imageChoiceIdx =  rand.nextInt(numberOfChoices)
        log.error "choices is ${imageChoiceIdx}"
        String imageName = choices[imageChoiceIdx]  // get random choice
        return new CaptchaValue(imageName: imageName, imageText: getImageText(imageName), imageChoices: choices)
    }

    public CaptchaValue getRandomCaptchaValue() {
        String imageName = getRandomImageName()
        return new CaptchaValue(imageName: imageName, imageText: getImageText(imageName))
    }


    public String getRandomImageName() {
        List<String> imageNames = imageMap.keySet().toList()
        java.util.Collections.shuffle(imageNames)
        return imageNames[0]
    }

    /**
     * Get the time-dependent variable for nonce creation.
     * This function is based on Wordpress' wp_nonce_tick().
     *
     * @since 1.1
     * @param $life Integer number of seconds for the tick to be valid. Defaults to 86400 (24 hours)
     * @return int
     */
    private int nonceTick() {
        def life = 86400
        return Math.ceil(new Date().time / life)
    }

    /**
     * Private encrypt method. Encrypts a string using sha1()
     *
     * @since 4.0
     * @param $string String to encrypt
     * @return $encryptedString String encrypted
     */
    private String encrypt(String src) {

        String encryptedString = encodeAsSHA1(src)
        /*  String encryptedString = sha1( this.hashSalt + this.nonceTick(1800) + '::encrypt::' + src + '::' + this.getRequestSession() );
          messageDigest.update(hashSalt.getBytes() + this.nonceTick(1800))
          messageDigest.update(src.getBytes())
          // new BigInteger(1, messageDigest.digest()).toString(16).padLeft(40, '0')
          return encryptedString;*/
    }

    /**
     * Public getImageFilePath method. Returns the image file path in the session, for the given index
     *
     * @since 4.1
     * @param $i Integer index number
     * @param $getRetina Boolean should the images be @2x or not. Defaults to false
     * @return String with the path to the current image file according to the index
     */
    public String getImageFilePath(int i, boolean getRetina = false) {
        def imageEncryptedValue = $_SESSION[this.hash + '::options'][i];
        String imagePath = $_SESSION[this.hash + '::optionsProperties'][imageEncryptedValue][0];

        if (!getRetina) {
            return imagePath;
        } else {
            def retinaPath = imagePath.replace('.png', '@2x.png')
            return retinaPath;
        }
    }

    /**
     * Public getAudioFilePath method. Returns the current audio file path in the session, if set
     *
     * @since 4.0
     * @return String with the path to the current acessibility audio file
     */
    public String getAudioFilePath(GrailsHttpSession session) {
        return session[this.hash + '::accessibilityFile'];
    }

}
