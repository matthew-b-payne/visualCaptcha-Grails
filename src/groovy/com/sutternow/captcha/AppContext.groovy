package com.sutternow.captcha

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import org.springframework.web.context.ServletConfigAware

import javax.servlet.ServletConfig

/**
 * Created by mpayne on 12/27/13.
 */
@Configuration
class AppContext implements ServletConfigAware {

    ServletConfig servletConfig
    String visualCaptchaPath

    @Bean() //initMethod = "setup", destroyMethod = "cleanup"
    public ImageManager imageManager() {
        ImageManager imageManager = new ImageManager(new File(visualCaptchaPath));
        return imageManager;
    }

    @Bean(initMethod = "init") //, destroyMethod = "cleanup"
    public VisualCaptcha visualCaptcha() {
        VisualCaptcha vc = new VisualCaptcha(imageManager(),visualCaptchaPath );
        return vc;
    }

    @Override
    void setServletConfig(ServletConfig servletConfig) {
       this.servletConfig = servletConfig
       visualCaptchaPath =  servletConfig.getServletContext().getRealPath(VisualCaptcha.captchaPath)
    }
}
