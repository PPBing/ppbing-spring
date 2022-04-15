package com.ppbing.spring;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class PpbingApplicationContext {

    private Class configClass;

    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();


    public PpbingApplicationContext(Class configClass) {
        this.configClass = configClass;

        //解析配置类

        scan(configClass);

    }

    private void scan(Class configClass) {
        ComponentScan annotation = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
        String path = annotation.value();
        path = path.replace(".", "/");
        // 扫描--->找Component注解的类--->生成Bean对象

        ClassLoader classLoader = PpbingApplicationContext.class.getClassLoader();
        URL resource = classLoader.getResource("com/ppbing/test");
        File file = new File(resource.getFile());

        File[] files = file.listFiles();
        for (File f : files) {
            String fileName = f.getAbsolutePath();
            if (fileName.endsWith(".class")) {
                String className = fileName.substring(fileName.indexOf("com"), fileName.indexOf(".class"));
                className = className.replace("\\", ".");

                try {
                    Class clazz = classLoader.loadClass(className);

                    if (clazz.isAnnotationPresent(Component.class)) {
//                        System.out.println(clazz);

                        Component annotation1 = (Component) clazz.getAnnotation(Component.class);
                        String beanName = annotation1.value();

                        BeanDefinition beanDefinition = new BeanDefinition();
                        beanDefinition.setClazz(clazz);

                        if (clazz.isAnnotationPresent(Scope.class)) {
                            Scope annotation2 = (Scope) clazz.getAnnotation(Scope.class);
                        } else {
                            beanDefinition.setScope("singleton");
                        }
                    }

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }


//

        }
    }

    public Object getBean(String beanName) {
        if (beanDefinitionMap.containsKey(beanName)) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if (beanDefinition.getScope().equals("singleton")) {

            } else {

            }


        } else {
            throw new NullPointerException();
        }

        return null;
    }
}
