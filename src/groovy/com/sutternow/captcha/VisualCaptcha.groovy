package com.sutternow.captcha

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


    def messageDigest = MessageDigest.getInstance("SHA1")

    /*  Map<String,String> imageMap = ['Airplane':'airplane.png', 'Ballons':'ballons', 'Camera': 'camera.png',
      'Car': 'car.png', 'Cat': 'cat.png',]
  */

    public VisualCaptcha(ImageManager imageManager) {
        imageManager = imageManager
        imageMap = imageManager.imageMap
    }

    public VisualCaptcha(ImageManager imageManager, String resourcePath) {
        imageManager = imageManager
        imageMap = imageManager.imageMap
        this.resourcePath = resourcePath
    }

    public void init() {
        loadAudio()
    }

    private void loadAudio() {

        List<File> audioFiles = []

        new AnswerInfo('10', new File(resourcePath + File.separatorChar + '5times2.mp3'),
        new AnswerInfo('20', self::$audiosPath . '2times10.mp3'),
        new AnswerInfo('6', self::$audiosPath . '5plus1.mp3'),
        new AnswerInfo('7', self::$audiosPath . '4plus3.mp3'),
        new AnswerInfo('4', self::$audiosPath . 'add3to1.mp3'),
        new AnswerInfo('green', self::$audiosPath . 'addblueandyellow.mp3'),
        new AnswerInfo('white', self::$audiosPath . 'milkcolor.mp3'),
        new AnswerInfo('2', self::$audiosPath . 'divide4by2.mp3'),
        new AnswerInfo('yes', self::$audiosPath . 'sunastar.mp3'),
        new AnswerInfo('no', self::$audiosPath . 'yourobot.mp3'),
        new AnswerInfo('12', self::$audiosPath . '6plus6.mp3'),
        new AnswerInfo('100', self::$audiosPath . '99plus1.mp3'),
        new AnswerInfo('blue', self::$audiosPath . 'skycolor.mp3'),
        new AnswerInfo('3', self::$audiosPath . 'after2.mp3'),
        new AnswerInfo('24', self::$audiosPath . '12times2.mp3'),
        new AnswerInfo('5', self::$audiosPath . '4plus1.mp3'),


        audioPath).eachFileMatch( ~".*${prefix}.*mp3" ) {
            println it.name
            audioFiles << it
        }
    }


    public VisualCaptcha() {

        /*imageManager = imageManager
        imageMap = imageManager.imageMap*/
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
