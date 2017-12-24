package com.dimazombie;

import java.util.*;

public class DependenciesConfig {
    private Map<Class<?>, Object> regInstances = new HashMap<>();

    Registration register(Class<?> regClass) {
        Object instance = loadObject(regClass);
        return new Registration(instance);
    }

    private Object loadObject(Class<?> regClass) {
        try{
            return regClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException("Cannot load class instance", e);
        }
    }

    <T> T findInstance(Class<T> type) {
        Class<?> key = regInstances.keySet().stream().filter(i -> Objects.equals(i, type)).findFirst().get();
        return (T) regInstances.get(key);
    }

    class Registration {
        private Object instance;
        private Class<?> type;

        Registration(Object instance) {
            this.instance = instance;
            this.type = instance.getClass();
        }

        Registration as(Class<?> type) {
            this.type = type;
            return this;
        }

        void complete() {
            regInstances.put(type, instance);
        }
    }

}
