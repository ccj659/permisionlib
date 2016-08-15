package com.efly.permissionlib;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016/8/15.
 */

public class PermissionUtils {


    /**
     * 简单打印出UserAnnotation 类中所使用到的类注解
     * 该方法只打印了 Type 类型的注解
     * @throws ClassNotFoundException
     */
    public static void parseTypeAnnotation() throws ClassNotFoundException{
        Class clazz = Class.forName("annotation.test.UserAnnotation");
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations) {
            TestA testA = (TestA) annotation;
            System.out.println("type name = "+clazz.getName() + "  |  id = " + testA.id() + "  |  name = " + testA.name() + "  |  　　　　　　　　　　　　　　　　　　　　gid = " + testA.gid());
        }
    }

    /**
     * 简单打印出UserAnnotation 类中所使用到的方法注解
     * 该方法只打印了 Method 类型的注解
     * @throws ClassNotFoundException
     */
    public static void parseMethodAnnotation() throws ClassNotFoundException{
        Method[] methods = TestAnnotation.class.getDeclaredMethods();
        for (Method method : methods) {
             /*
             * 判断方法中是否有指定注解类型的注解
             */
            boolean hasAnnotation = method.isAnnotationPresent(TestA.class);
            if(hasAnnotation){
                TestA annotation = method.getAnnotation(TestA.class);
                System.out.println("method name = " + method.getName() + "  |  id = " +
                        annotation.id() + "  |  description = " + annotation.name() + "  |  gid = " + annotation.gid());
            }
        }
    }

    /**
     * 简单打印出UserAnnotation 类中所使用到的构造方法注解
     * 该方法只打印了 构造方法 类型的注解
     * @throws ClassNotFoundException
     */
    public static void parseConstructAnnotation()  throws ClassNotFoundException{
        Constructor[] constructors = TestAnnotation.class.getConstructors();
        for (Constructor constructor : constructors) {
            /*
             * 判断构造方法中是否有指定注解类型的注解
             */
            boolean hasAnnotation = constructor.isAnnotationPresent(TestA.class);
            if(hasAnnotation){
                 /*
                 * 根据注解类型返回方法的指定类型注解
                 */
                TestA annotation = (TestA) constructor.getAnnotation(TestA.class);
                System.out.println("constructor = " + constructor.getName()
                        + "   |   id = " + annotation.id() + "  |  description = "
                        + annotation.name() + "  |   gid= "+annotation.gid());
            }
        }
    }

    /**
     * 简单打印出UserAnnotation 类中所使用到的字段注解
     * 该方法只打印了 Method 类型的注解
     * @throws ClassNotFoundException
     */
    public static void parseFieldAnnotation() throws ClassNotFoundException{
        Field[] fields = TestAnnotation.class.getDeclaredFields();
        for (Field field : fields) {
            boolean hasAnnotation = field.isAnnotationPresent(TestA.class);
            if(hasAnnotation){
                TestA annotation = field.getAnnotation(TestA.class);
                System.out.println("Field = " + field.getName()
                        + "  |  id = " + annotation.id() + "  |  description = "
                        + annotation.name() + "  |  gid= "+annotation.gid());
            }
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println("------------------------------解析Type注解----------------------------------------------------------");
        parseTypeAnnotation();
        System.out.println("------------------------------解析Method注解-------------------------------------------------------");
        parseMethodAnnotation();
        System.out.println("------------------------------解析构造方法(Construct)注解------------------------------------------");
        parseConstructAnnotation();
        System.out.println("------------------------------解析字段(Field)注解-----------------------------------------------------");
        parseFieldAnnotation();
    }
}
