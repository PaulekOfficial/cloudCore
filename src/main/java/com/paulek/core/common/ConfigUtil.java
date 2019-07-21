package com.paulek.core.common;

import org.diorite.cfg.system.Template;
import org.diorite.cfg.system.TemplateCreator;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ConfigUtil {

    @SuppressWarnings("unchecked")
    public static <T> T loadConfig(File file, Class<T> implementationClass){

        Constructor<T> constructor = (Constructor<T>) ReflectionUtils.getConstructor(implementationClass);
        Template<T> template = TemplateCreator.getTemplate(implementationClass);

        T config = null;

        if(!file.exists()){
            try{
                config = template.fillDefaults(constructor.newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e){
                e.printStackTrace();
            }
        } else {
            try {
                config = template.load(file);
                if(config == null){
                    config = template.fillDefaults(implementationClass.newInstance());
                }
            } catch (IOException | IllegalAccessException | InstantiationException e){
                e.printStackTrace();
            }
        }

        try{
            template.dump(file, config, false);
        } catch (IOException e){
            e.printStackTrace();
        }

        return config;
    }

}
