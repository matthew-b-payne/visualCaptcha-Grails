import com.sutternow.captcha.VisualCaptcha

class GcaptchaGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.2 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def title = "Gcaptcha Plugin" // Headline display name of the plugin
    def author = "Matthew Payne"
    def authorEmail = "matthew.b.payne@gmail.com"
    def description = '''Grails port of visual captcha plugin'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/gcaptcha"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
//    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    // Online location of the plugin's browseable source code.
//    def scm = [ url: "http://svn.codehaus.org/grails-plugins/" ]

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithSpring = {

        resourcePathProvider(com.sutternow.captcha.ResourcePathProvider)

        captchaFilePath(org.springframework.core.io.FileSystemResource, "/WEB-INF/visualcaptcha" + File.separator + 'choices') {

        }

        imageManager(com.sutternow.captcha.ImageManager) {
            //ResourcePathProvider resourcePathProvider
            workingDirectory = "#{resourcePathProvider.getRealPath('/WEB-INF/visualcaptcha/choices')}" // ref('captchaFilePath').file
        }

        visualCaptcha(com.sutternow.captcha.VisualCaptcha, ref("imageManager"), "#" ){ bean ->
            bean.initMethod = 'init'
            resourcePathProvider = ref('resourcePathProvider')
            resourcePath =  "#{resourcePathProvider.getRealPath('/WEB-INF/visualcaptcha/choices')}"
        }
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
