package com.team2363.lib.json;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Collection;
import java.util.HashMap;

public class JSONSerializer {
    public static JSON serializeJSON(Object obj) {
        return new JSON(serializeJSONEntry(obj));
    }

    /**
     * 
     * @param obj
     * @return
     */
    public static JSONEntry serializeJSONEntry(Object obj) {
        JSONEntry entry;
        if (obj == null) {
            entry = new JSONNull();
        } else {
            Class<?> cls = obj.getClass();
            if (cls.isAnnotationPresent(JSONSerializable.class)) {
                Field[] fields = cls.getDeclaredFields();
                Method[] methods = cls.getMethods();
                switch (cls.getAnnotation(JSONSerializable.class).rootType()) {
                    case OBJECT:
                        Map<String, JSONEntry> objectMap = new HashMap<>();
                        for (Field field : fields) {
                            if (field.isAnnotationPresent(SerializedJSONObjectElement.class) && field.canAccess(obj)) {
                                try {
                                    objectMap.put(field.getAnnotation(SerializedJSONObjectElement.class).key(), serializeJSONEntry(field.get(obj)));
                                } catch (IllegalAccessException e) { // this will never occur because it was checked for (Field.canAccess)
                                }
                            }
                        }
                        for (Method method : methods) {
                            if (method.isAnnotationPresent(SerializedJSONObjectElement.class) && method.canAccess(obj) && method.getParameterCount() == 0 && method.getReturnType() != void.class) {
                                try {
                                    objectMap.put(method.getAnnotation(SerializedJSONObjectElement.class).key(), serializeJSONEntry(method.invoke(obj)));
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                }
                            }
                        }
                        entry = new JSONObject(objectMap);
                        break;
                    case ARRAY:
                        Map<Integer, JSONEntry> arrayMap = new HashMap<>();
                        for (Field field : fields) {
                            if (field.isAnnotationPresent(SerializedJSONArrayElement.class) && field.canAccess(obj)) {
                                try {
                                    arrayMap.put(field.getAnnotation(SerializedJSONArrayElement.class).index(), serializeJSONEntry(field.get(obj)));
                                } catch (IllegalAccessException e) {
                                }
                            }
                        }
                        for (Method method : methods) {
                            if (method.isAnnotationPresent(SerializedJSONArrayElement.class) && method.canAccess(obj) && method.getParameterCount() == 0 && method.getReturnType() != void.class) {
                                try {
                                    arrayMap.put(method.getAnnotation(SerializedJSONArrayElement.class).index(), serializeJSONEntry(method.invoke(obj)));
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                }
                            }
                        }
                        int largestIndex = 0;
                        for (int key : arrayMap.keySet()) {
                            if (key > largestIndex) { // find largest index user has placed on any annotated field or getter method
                                largestIndex = key;
                            }
                        }
                        JSONEntry[] arrayEntries = new JSONEntry[largestIndex + 1];
                        for (int i = 0; i <= largestIndex; i++) {
                            JSONEntry arrayMapEntry = arrayMap.get(i);
                            if (arrayMapEntry == null) {
                                arrayEntries[i] = new JSONNull();
                            } else {
                                arrayEntries[i] = arrayMapEntry;
                            }
                        }
                        entry = new JSONArray(arrayEntries);
                        break;
                    default:
                        entry = new JSONNull();
                    break;
                }
            } else if (obj instanceof Collection<?>) {
                Collection<?> objCollection = (Collection<?>) obj;
                int collectionSize = objCollection.size();
                JSONEntry[] collectionEntryArray = new JSONEntry[collectionSize];
                int index = 0;
                for (Object objInCollection : objCollection) {
                    collectionEntryArray[index] = serializeJSONEntry(objInCollection);
                    index++;
                }
                entry = new JSONArray(collectionEntryArray);
            } else if (cls.isArray()) {
                int arrayLength = Array.getLength(obj);
                JSONEntry[] arrayArray = new JSONEntry[arrayLength];
                for (int i = 0; i < arrayLength; i++) {
                    arrayArray[i] = serializeJSONEntry(Array.get(obj, i));
                }
                entry = new JSONArray(arrayArray);
            } else if (obj instanceof Boolean) {
                entry = new JSONBoolean((boolean) obj);
            } else if (obj instanceof Number) {
                entry = new JSONNumber((Number) obj);
            } else { // if unable to do anything else just treat it like a string. Strings will also go to this.
                entry = new JSONString(obj.toString());
            }
        }
        return entry;
    }
}
