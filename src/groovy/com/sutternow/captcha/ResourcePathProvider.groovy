package com.sutternow.captcha

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware

/**
 * Created by mpayne on 12/24/13.
 */
class ResourcePathProvider implements ApplicationContextAware {

    ApplicationContext applicationContext

    public String getPath(String relativePath){
        return applicationContext.getResource(relativePath).getFile().toString()
    }

    public String getRealPath(String relativePath){
        return applicationContext.getResource(relativePath).getFile().getAbsolutePath()
    }


    public String  getPath(Map absolutePathConfig){
        return absolutePathConfig.path
    }
}
