package com.example.hazelcast.common.util;

public abstract class TypeConvertUtils {

    /**
     * Convert the given object to the inferred return type.
     *
     * @param object object to be converted.
     * @param <T>    converted type
     * @return converted object.
     */
    public static <T> T cast(Object object){
        return (T) object;
    }
}
