package com.team2363.lib.json;

import java.util.Arrays;

public class DeserializedGenericType extends DeserializedType {
    // examples:
    //     // List<Integer>
    //     DeserializedType test = new DeserializedGenericType(List.class, Integer.class);
    //     // Map<String, Integer>
    //     DeserializedType test1 = new DeserializedGenericType(Map.class, String.class, Integer.class);
    //     // Map<String, List<Integer>> (very wordy lol)
    //     DeserializedType test2 = new DeserializedGenericType(Map.class, new DeserializedType(String.class), new DeserializedGenericType(List.class, new DeserializedType(Integer.class)));
    private final DeserializedType[] typeParameters;
    public DeserializedGenericType(Class<?> type, DeserializedType... typeParameters) {
        super(type);
        if (typeParameters == null) {
            throw new IllegalArgumentException("Type parameters are null.");
        }
        if (typeParameters.length == 0) {
            throw new IllegalArgumentException("No type parameters provided.");
        }
        for (DeserializedType typeParameter : typeParameters) {
            if (typeParameter == null) {
                throw new IllegalArgumentException("A provided type parameter is null.");
            }
        }
        this.typeParameters = Arrays.copyOf(typeParameters, typeParameters.length);
    }
    public DeserializedGenericType(Class<?> type, Class<?>... typeParameters) {
        super(type);
        if (typeParameters != null) {
            int length = typeParameters.length;
            this.typeParameters = new DeserializedType[length];
            for (int i = 0; i < length; i++) {
                this.typeParameters[i] = new DeserializedType(typeParameters[i]);
            }
        } else {
            this.typeParameters = null;
        }
    }
    public DeserializedType[] getTypeParameters() {
        return typeParameters;
    }

    @Override
    public String toString() {
        String str = Arrays.toString(typeParameters);
        return getType().getSimpleName() + "<" + str.substring(1, str.length() - 1) + ">";
    }
}
