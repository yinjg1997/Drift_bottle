package cn.edu.zust.common.jwt;

import java.lang.annotation.*;

/**
 * 如何定义一个注解,首先要理解注解就是一个类class
 *
 * @Target 用来修饰注解类的元注解,也就是注解的注解，,只有一个枚举类型的 ElementType 的属性,其枚举值有:
 * > 枚举值为ANNOTATION_TYPE, CONSTRUCTOR, METHOD, FIELD, LOCAL_VARIABLE, PACKAGE, PARAMETER, TYPE
 *
 * @Retention 用来修饰注解类, 只有一个枚举类型的 RetentionPolicy 属性,其枚举值有:
 * > 用@Retention(RetentionPolicy.CLASS)修饰的注解，表示注解的信息被保留在class文件(字节码文件)中当程序编译时，但不会被虚拟机读取在运行的时候；
 * > 用@Retention(RetentionPolicy.SOURCE )修饰的注解,表示注解的信息会被编译器抛弃，不会留在class文件中，注解的信息只会留在源文件中；
 * > 用@Retention(**RetentionPolicy.RUNTIME** )修饰的注解，表示注解的信息被保留在class文件(字节码文件)中当程序编译时，会被虚拟机保留在运行时，
 *
 * 像是spring的@Service注解 定义了一个String类型的 value 属性, 其中默认值为""
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JwtIgnore {
}
