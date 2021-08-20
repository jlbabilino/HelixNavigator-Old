package com.team2363.lib.file;

import java.lang.reflect.Field;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.team2363.lib.json.DeserializedGenericType;
import com.team2363.lib.json.DeserializedType;

public class DeserializedTypeTest<T, U, V> {
    public Map<Map<String, Map<List<T>, String>>, List<Set<Map<U, V>>>> map;
    // Map<Map<String, Map<List<Integer>, String>>, List<Set<Map<Double, Float>>>>
    public static void main(String[] args) throws Exception {
        Class<?> clazz = DeserializedTypeTest.class;
        TypeVariable<?> tVar = clazz.getTypeParameters()[0];
        TypeVariable<?> uVar = clazz.getTypeParameters()[1];
        TypeVariable<?> vVar = clazz.getTypeParameters()[2];
        Map<TypeVariable<?>, Class<?>> genericMap = Map.of(tVar, Integer.class, uVar, Double.class, vVar, Float.class);
        Field field = clazz.getDeclaredField("map");
        DeserializedGenericType test = (DeserializedGenericType) DeserializedType.fromType(field.getGenericType(), genericMap);
        System.out.println(test);
    }
}
