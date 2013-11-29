<?php
/**
 * visualCaptcha HTML class by emotionLoop - 2013.08.17
 *
 * This class handles the HTML for the main visualCaptcha class.
 *
 * This license applies to this file and others without reference to any other license.
 *
 * @author emotionLoop | http://emotionloop.com
 * @link http://visualcaptcha.net
 * @package visualCaptcha
 * @license GNU GPL v3
 * @version 4.2.0
 */
namespace visualCaptcha;

class HTML {
	
	public function __construct() {
	}
	
	public static function get( $type, $fieldName, $accessibilityFieldName, $formId, $captchaText, $options ) {
		$html = '';
		
		ob_start();
?>
<script>
window.vCVals = {
	'f': '<?php echo $formId; ?>',
	'n': '<?php echo $fieldName; ?>',
	'a': '<?php echo $accessibilityFieldName; ?>'
};
</script>
<div class="eL-captcha type-<?php echo $type; ?> clearfix">
	<p class="eL-explanation type-<?php echo $type; ?>"><span class="desktopText"><?php echo 'Drag the'; ?> <strong><?php echo $captchaText; ?></strong> <?php echo 'to the circle on the side'; ?>.</span><span class="mobileText"><?php echo 'Touch the'; ?> <strong><?php echo $captchaText; ?></strong> <?php echo 'to move it to the circle on the side'; ?>.</span></p>
	<div class="eL-possibilities type-<?php echo $type; ?> clearfix">
<?php
		$limit = count( $options );

		for ( $i = 0; $i < $limit; $i++ ) {
			$name = $options[ $i ];
?>
		<img src="<?php echo Captcha::$imageFile; ?>?i=<?php echo ($i + 1); ?>&amp;r=<?php echo time(); ?>" class="vc-<?php echo ($i + 1); ?>" data-value="<?php echo $name; ?>" alt="" title="">
<?php
		}
?>
	</div>
	<div class="eL-where2go type-<?php echo $type; ?> clearfix">
		<p><?php echo 'Drop<br>Here'; ?></p>
	</div>
	<p class="eL-accessibility type-<?php echo $type; ?>"><a href="#" title="<?php echo 'Accessibility option: listen to a question and answer it!'; ?>"><img src="<?php echo Captcha::$imagesPath; ?>accessibility.png" alt="<?php echo 'Accessibility option: listen to a question and answer it!'; ?>"></a></p>
	<div class="eL-accessibility type-<?php echo $type; ?>">
		<p><?php echo 'Type below the'; ?> <strong><?php echo 'answer'; ?></strong> <?php echo 'to what you hear. Numbers or words:'; ?></p>
		<audio preload="preload">
			<source src="<?php echo Captcha::$audioFile; ?>?t=ogg&amp;r=<?php echo time(); ?>" type="audio/ogg">
			<source src="<?php echo Captcha::$audioFile; ?>?t=mp3&amp;r=<?php echo time(); ?>" type="audio/mpeg">
			<?php echo 'Your browser does not support the audio element.'; ?>
		</audio>
	</div>
</div>
<?php
		$html = ob_get_clean();
		return $html;
	}
}
?>