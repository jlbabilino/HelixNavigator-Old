package com.team2363.lib.json;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Map;

public class DeserializedType {
    private final Class<?> type;
    public DeserializedType(Class<?> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type is null.");
        }
        this.type = type;
    }
    public Class<?> getType() {
        return type;
    }
    // only package can access
    static DeserializedType fromType(Type type, Map<TypeVariable<?>, Class<?>> genericMap) throws IllegalArgumentException {
        if (type == null) {
            throw new IllegalArgumentException("Type is null");
        }
        if (genericMap == null) {
            throw new IllegalArgumentException("Generic Map is null.");
        }
        Class<?> baseType;
        DeserializedType deserializedType;
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            int typeArgumentsCount = typeArguments.length;
            DeserializedType[] deserializedTypeArguments = new DeserializedType[typeArgumentsCount]; // will be populated
            for (int i = 0; i < typeArgumentsCount; i++) {
                deserializedTypeArguments[i] = fromType(typeArguments[i], genericMap);
            }
            baseType = (Class<?>) parameterizedType.getRawType();
            deserializedType = new DeserializedGenericType(baseType, deserializedTypeArguments);
        } else {
            if (type instanceof TypeVariable<?>) {
                if (genericMap != null && genericMap.containsKey(type)) {
                    baseType = genericMap.get(type);
                } else {
                    throw new IllegalArgumentException("Generic Map does not contain a key for generic type " + type.getTypeName() + ".");
                }
            } else if (type instanceof Class<?>) {
                baseType = (Class<?>) type;
            } else if (type instanceof GenericArrayType) {
                throw new UnsupportedOperationException("Unable to parse DeserializedType from Type.");
            } else { // has to be wildcard type
                throw new IllegalArgumentException("Cannot use wildcard types here.");
            }
            deserializedType = new DeserializedType(baseType);
        }
        return deserializedType;
    }

    @Override
    public String toString() {
        return this.type.getSimpleName();
    }
}