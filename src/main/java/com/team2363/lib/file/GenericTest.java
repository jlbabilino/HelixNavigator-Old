package com.team2363.lib.file;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GenericTest<T> {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = GenericTest.class;
        // Field test = GenericTest.class.getDeclaredField("field");
        // Type testGeneric = test.getGenericType();
        // Type classGeneric = GenericTest.class.getTypeParameters()[0];
        // System.out.println(testGeneric);
        // System.out.println(classGeneric);
        // System.out.println(testGeneric.equals(classGeneric));
        
        // Field test = GenericTest.class.getDeclaredField("field");
        // Type testGeneric = test.getGenericType();
        // if (testGeneric instanceof ParameterizedType) {
        //     ParameterizedType testGenericTypeVar = (ParameterizedType) testGeneric;
        //     System.out.println("generic type parameters: ");
        //     Type[] typeArgs = testGenericTypeVar.getActualTypeArguments();
        //     for (Type arg : typeArgs) {
        //         System.out.println(arg instanceof TypeVariable<?>);
        //     }
        //     System.out.println(testGenericTypeVar.getRawType());
        // }
        // System.out.println(testGeneric);

        Method genericMethodA = clazz.getMethod("genericMethodA");
        TypeVariable<?> genericMethodATypeVariable = genericMethodA.getTypeParameters()[0];

        Method genericMethodB= clazz.getMethod("genericMethodB");
        TypeVariable<?> genericMethodBTypeVariable = genericMethodB.getTypeParameters()[0];

        System.out.println(genericMethodATypeVariable.equals(genericMethodBTypeVariable));

    }
    public GenericTest() {
    }
    public Map<String, T> field;
    public T blah;
    public Integer normalField;

    public <@TestAnnotation(String.class) N> void genericMethodA() {
    }
    public <N> void genericMethodB() {
    }
    @Target(ElementType.TYPE_PARAMETER)
    public static @interface TestAnnotation {
        public Class<?> value();
    }
}
