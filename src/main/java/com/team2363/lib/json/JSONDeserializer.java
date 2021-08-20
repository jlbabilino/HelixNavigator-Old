package com.team2363.lib.json;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.team2363.lib.json.examples.Dog;

public class JSONDeserializer {
    public static void test() {
        JSON json = new JSON(new JSONArray(new JSONEntry[]{new JSONArray(new JSONEntry[]{new JSONNumber(5), new JSONNumber(-4), new JSONNumber(10)}), new JSONArray(new JSONEntry[]{new JSONNumber(5), new JSONNumber(-4), new JSONNumber(10)}), new JSONArray(new JSONEntry[]{new JSONNumber(5), new JSONNumber(-4), new JSONNumber(10)})}));
        try {
            // to convert json to a generic type you need to 
            @SuppressWarnings("unchecked")
            List<Integer> list = (List<Integer>) deserializeJSON(json, List.class, Integer.class);
        } catch (JSONDeserializerException e) {

        }
    }
    public void test2() throws JSONDeserializerException {
        // convert json to List<Integer>
        deserializeJSON(null, new DeserializedGenericType(List.class, Integer.class));
        // convert json to Map<String, List<Double>>
        deserializeJSON(null, new DeserializedGenericType(Map.class, new DeserializedType(String.class), new DeserializedGenericType(List.class, Double.class)));
        // covnert json to Person
        deserializeJSON(null, Person.class);
    }
    public static Object deserializeJSON(JSON json, DeserializedType type) throws IllegalArgumentException, JSONDeserializerException {
        return deserializeJSONEntry(json.getRoot(), type);
    }
    public static Object deserializeJSON(JSON json, Class<?> type, Class<?>... typeParameters) throws IllegalArgumentException, JSONDeserializerException {
        return deserializeJSONEntry(json.getRoot(), type, typeParameters);
    }
    public static Object deserializeJSON(JSON json, Class<?> type) throws IllegalArgumentException, JSONDeserializerException {
        return deserializeJSONEntry(json.getRoot(), type);
    }
    public static Object deserializeJSONEntry(JSONEntry jsonEntry, DeserializedType type) throws IllegalArgumentException, JSONDeserializerException {
        if (jsonEntry == null) {
            throw new IllegalArgumentException("The JSON entry being deserialized is null");
        }
        if (type == null) {
            throw new IllegalArgumentException("The DeserializedType object used in deserialization is null");
        }
        Object obj;
        Class<?> baseType = type.getType();
        Map<TypeVariable<?>, Class<?>> typeParametersMap = new HashMap<>();
        if (type instanceof DeserializedGenericType) {
            DeserializedGenericType genericType = (DeserializedGenericType) type;
            TypeVariable<?>[] typeParameters = baseType.getTypeParameters();
            DeserializedType[] actualTypeParameters = genericType.getTypeParameters();
            if (typeParameters.length == actualTypeParameters.length) {
                for (int i = 0; i < typeParameters.length; i++) {
                    typeParametersMap.put(typeParameters[i], actualTypeParameters[i].getType());
                }
            } else {
                throw new JSONDeserializerException("Class " + baseType.getCanonicalName() + " has " + typeParameters.length + " type parameters, but the DeserializedGenericType object provided contains " + actualTypeParameters.length + ". These values should be equal.");
            }
        }
        if (baseType.isAnnotationPresent(JSONSerializable.class)) {
            JSONEntry.Type rootType = baseType.getAnnotation(JSONSerializable.class).rootType();
            Field[] fields = baseType.getFields();
            Method[] methods = baseType.getMethods();
            int baseTypeModifiers = baseType.getModifiers();
            if (Modifier.isAbstract(baseTypeModifiers)) {
                search: {
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(DeserializedAbstractDeterminer.class)) {
                            int determinerModifiers = method.getModifiers();
                            if (method.getParameterCount() == 1 && Modifier.isStatic(determinerModifiers) && method.getParameterTypes()[0] == JSONEntry.class && method.getReturnType() == DeserializedType.class) {
                                if (jsonEntry.getType() == rootType) {
                                    try {
                                        DeserializedType result = (DeserializedType) method.invoke(new Dog(5), jsonEntry); // null since static
                                        if (baseType.isAssignableFrom(result.getType())) {
                                            obj = deserializeJSONEntry(jsonEntry, result);
                                        } else {
                                            throw new JSONDeserializerException("The abstract determiner " + method.getName() + " of type " + baseType.getCanonicalName() + " was invoked successfully, but it returned a DeserializedType that is not a descendent of the abstract class.");
                                        }
                                    } catch (IllegalAccessException | InvocationTargetException e) {
                                        throw new JSONDeserializerException("Unable to invoke abstract determiner " + method.getName() + " of type " + baseType.getCanonicalName() + ": " + e + ": " + e.getCause());
                                    }
                                } else {
                                    throw new JSONDeserializerException("Unable to use abstract determiner " + method.getName() + " of type " + baseType.getCanonicalName() + " because the JSONEntry being deserialized is not the appropriate type. The class expects a JSON " + rootType.toString() + " but the JSONEntry being deserialized is a JSON " + jsonEntry.getType().toString() + ".");
                                }
                            } else {
                                throw new JSONDeserializerException("Abstract determiner of " + baseType.getCanonicalName() + " not formatted correctly: There should be one paramter of type JSONEntry, and the return type should be Class<?>.");
                            }
                            break search;
                        }
                    }
                    throw new JSONDeserializerException("Cannot deserialize to abstract class " + baseType.getCanonicalName() + " because there is no abstract determiner present in the class. An abstract determiner is a non-abstract static method that returns type DeserializedType, has exactly one formal parameter of type JSONEntry, and is annotated with DeserializedAbstractDeterminer.");
                }
            } else {
                Constructor<?>[] constructors = baseType.getConstructors();
                Constructor<?> foundConstructor = null;
                for (Constructor<?> constructor : constructors) {
                    if (constructor.isAnnotationPresent(DeserializedJSONTarget.class)) {
                        foundConstructor = constructor;
                        break;
                    }
                }
                if (foundConstructor != null) {
                    Parameter[] constructorParameters = foundConstructor.getParameters();
                    int constructorParameterCount = foundConstructor.getParameterCount();
                    switch (rootType) {
                        case OBJECT:
                            if (jsonEntry.isObject()) {
                                Object[] instantiatedConstructorParameters = new Object[constructorParameterCount];
                                for (int i = 0; i < constructorParameterCount; i++) {
                                    if (constructorParameters[i].isAnnotationPresent(DeserializedJSONObjectValue.class)) {
                                        DeserializedJSONObjectValue annotation = constructorParameters[i].getAnnotation(DeserializedJSONObjectValue.class);
                                        DeserializedType parameterType = DeserializedType.fromType(constructorParameters[i].getParameterizedType(), typeParametersMap);
                                        if (jsonEntry.containsKey(annotation.key())) {
                                            instantiatedConstructorParameters[i] = deserializeJSONEntry(jsonEntry.getKeyedEntry(annotation.key()), parameterType);
                                        }
                                        
                                    } else {
                                        throw new JSONDeserializerException("JSON Deserializable type " + baseType.getCanonicalName() + " deserializes a JSON Object, but the target constructor contains a formal parameter that is not marked as a target for deserialized JSON object data. Please annotate all parameters of the constructor with the \"DeserializedJSONObjectValue\" annotation.");
                                    }
                                }
                                try {
                                    obj = foundConstructor.newInstance(instantiatedConstructorParameters);
                                } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                                    throw new JSONDeserializerException("Unable to instantiate type " + baseType.getCanonicalName());
                                }
                                for (Field field : fields) {
                                    if (field.isAnnotationPresent(DeserializedJSONObjectValue.class)) {
                                        DeserializedType fieldType = DeserializedType.fromType(field.getGenericType(), typeParametersMap);
                                        try {
                                            field.set(obj, deserializeJSONEntry(jsonEntry, fieldType));
                                        } catch (IllegalAccessException e) {
                                            throw new JSONDeserializerException("Unable to instantiate field " + field.getName() + " in class " + baseType.getCanonicalName() + ".");
                                        }
                                    }
                                }
                                for (Method method : methods) {
                                    if (method.isAnnotationPresent(DeserializedJSONTarget.class)) {
                                        Parameter[] methodParameters = method.getParameters();
                                        int methodParametersCount = methodParameters.length;
                                        Object[] instantiatedMethodParameters = new Object[methodParametersCount];
                                        for (int i = 0; i < methodParametersCount; i++) {
                                            if (methodParameters[i].isAnnotationPresent(DeserializedJSONObjectValue.class)) {
                                                DeserializedJSONObjectValue annotation = methodParameters[i].getAnnotation(DeserializedJSONObjectValue.class);
                                                if (jsonEntry.containsKey(annotation.key())) {
                                                    DeserializedType parameterType = DeserializedType.fromType(methodParameters[i].getParameterizedType(), typeParametersMap);
                                                    instantiatedMethodParameters[i] = deserializeJSONEntry(jsonEntry.getKeyedEntry(annotation.key()), parameterType);
                                                } else {
                                                    throw new JSONDeserializerException("Parameter index " + i + " of method " + method.getName() + " of class " + baseType.getCanonicalName() + " requests the key \"" + annotation.key() + "\" from a JSON Object, but it is not availible in the provided JSON data.");
                                                }
                                            } else {
                                                throw new JSONDeserializerException("Method " + method.getName() + " in class " + baseType.getCanonicalName() + " is marked as JSON Deserializable for JSON Objects, " + rootType.toString() + ", but one or more of its parameters is not marked with \"DeserializedJSONObjectValues\".");
                                            }
                                            
                                        }
                                        try {
                                            method.invoke(obj, instantiatedMethodParameters);
                                        } catch (IllegalAccessException | InvocationTargetException e) {
                                            throw new JSONDeserializerException("Unable to access method " + method.getName() + " of class " + baseType.getCanonicalName() + ".");
                                        }
                                    }
                                }
                            } else {
                                throw new JSONDeserializerException("Class " + baseType.getCanonicalName() + " is marked as deserializable from a JSON " + rootType.toString() + " but the JSON data provided is of type " + jsonEntry.getType().toString());
                            }
                            break;
                        case ARRAY:
                            obj = null;
                            break;
                        case BOOLEAN:
                            obj = null;
                            break;
                        case NULL:
                            obj = null;
                            break;
                        case NUMBER:
                            obj = null;
                            break;
                        case STRING:
                            obj = null;
                            break;
                        default:
                            obj = null;
                            break;
                    }
                } else {
                    throw new JSONDeserializerException("Class " + baseType.getCanonicalName() + " is marked as JSON serializable (therefore also deserializable), but no constructor was marked as a deserialization target. Please annotate ONE constructor with \"DeserializedJSONTarget\".");
                }
            }
        } else if (List.class.isAssignableFrom(baseType)) {
            if (jsonEntry.isArray()) {
                if (type instanceof DeserializedGenericType) {
                    DeserializedGenericType genericType = (DeserializedGenericType) type;
                    if (genericType.getTypeParameters().length == 1) {
                        DeserializedType subType = genericType.getTypeParameters()[0];
                        JSONEntry[] jsonEntryArray = jsonEntry.getArray();
                        List<Object> list = new ArrayList<>();
                        for (JSONEntry entry : jsonEntryArray) {
                            list.add(deserializeJSONEntry(entry, subType)); // this will throw an exception if the sub json types can't be converted to the subType
                        }
                        obj = list;
                    } else {
                        throw new JSONDeserializerException("Attempting to deserialize a JSON array to a List, but the provided DeserializedGenericType does not contain exactly one type parameter.");
                    }
                } else {
                    throw new JSONDeserializerException("Attempting to deserialize a JSON array to a List, but the provided DeserializedType object is not of type DeserializedGenericType. If you would prefer to use raw types, set the type parameter to \"Object\"");
                }
            } else {
                throw new JSONDeserializerException("Can't convert this to list");
            }
        } else if (Set.class.isAssignableFrom(baseType)) {
            obj = null;
        } else if (Map.class.isAssignableFrom(baseType)) {
            obj = null;
        } else if (baseType.isPrimitive()) {
            if (baseType == byte.class) {
                if (jsonEntry.isNumber()) {
                    obj = jsonEntry.getNumber().byteValue();
                } else {
                    throw new JSONDeserializerException("Cannot convert " + jsonEntry.getType() + " to byte.");
                }
            } else if (baseType == short.class) {
                if (jsonEntry.isNumber()) {
                    obj = jsonEntry.getNumber().shortValue();
                } else {
                    throw new JSONDeserializerException("Cannot convert " + jsonEntry.getType() + " to short.");
                }
            } else if (baseType == int.class) {
                if (jsonEntry.isNumber()) {
                    obj = jsonEntry.getNumber().intValue();
                } else {
                    throw new JSONDeserializerException("Cannot convert " + jsonEntry.getType() + " to int.");
                }
            } else if (baseType == long.class) {
                if (jsonEntry.isNumber()) {
                    obj = jsonEntry.getNumber().longValue();
                } else {
                    throw new JSONDeserializerException("Cannot convert " + jsonEntry.getType() + " to long.");
                }
            } else if (baseType == float.class) {
                if (jsonEntry.isNumber()) {
                    obj = jsonEntry.getNumber().longValue();
                } else {
                    throw new JSONDeserializerException("Cannot convert " + jsonEntry.getType() + " to float.");
                }
            } else if (baseType == double.class) {
                if (jsonEntry.isNumber()) {
                    obj = jsonEntry.getNumber().doubleValue();
                } else {
                    throw new JSONDeserializerException("Cannot convert " + jsonEntry.getType() + " to double.");
                }
            } else if (baseType == boolean.class) {
                if (jsonEntry.isBoolean()) {
                    obj = jsonEntry.getBoolean();
                } else {
                    throw new JSONDeserializerException("Cannot convert " + jsonEntry.getType() + " to boolean.");
                }
            } else /*if (baseType == char.class)*/{
                if (jsonEntry.isString()) {
                    String str = jsonEntry.getString();
                    if (str.length() >= 1) {
                        obj = str.charAt(0);
                    } else {
                        throw new JSONDeserializerException("String cannot be converted to char because there are no chars in the string.");
                    }
                } else {
                    throw new JSONDeserializerException("Cannot convert " + jsonEntry.getType() + " to char.");
                }
            }
        } else if (baseType == String.class) {
            if (jsonEntry.isString()) {
                obj = jsonEntry.getString();
            } else {
                obj = jsonEntry.toString();
            }
        } else {
            obj = null;
        }
        return obj;
    }
    public static Object deserializeJSONEntry(JSONEntry jsonEntry, Class<?> type, Class<?>... typeParameters) throws IllegalArgumentException, JSONDeserializerException {
        return deserializeJSONEntry(jsonEntry, new DeserializedGenericType(type, typeParameters));
    }
    public static Object deserializeJSONEntry(JSONEntry jsonEntry, Class<?> type) throws IllegalArgumentException, JSONDeserializerException {
        return deserializeJSONEntry(jsonEntry, new DeserializedType(type));
    }
}
