package com.dimazombie;

import com.dimazombie.annotations.AutoSearch;
import com.dimazombie.annotations.Component;
import com.dimazombie.annotations.ComponentType;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Stream;

public class DependenciesConfig {
    private Map<Class<?>, Object> regInstances = new HashMap<>();
    private String path;

    DependenciesConfig() {
    }

    DependenciesConfig(String path) {
        this.path = path;
        loadBeansByAutoSearch();
    }

    private void loadBeansByAutoSearch() {
        loadBeansWithoutDep();
        loadBeansWithDep();
    }

    private void loadBeansWithoutDep() {
        beanClassesStream()
                .filter(c -> c.getConstructors().length == 1)
                .filter(c -> c.getConstructors()[0].getParameterCount() == 0)
                .forEach(this::registerBean);
    }

    private void loadBeansWithDep() {
        beanClassesStream()
                .filter(c -> c.getConstructors()[0].getParameterCount() > 0)
                .filter(c -> c.getConstructors()[0].getAnnotation(AutoSearch.class) != null)
                .forEach(this::registerBeanWithDep);

    }

    private Stream<? extends Class<?>> beanClassesStream() {
        return new FastClasspathScanner(path)
                .scan()
                .getNamesOfClassesWithAnnotation(Component.class)
                .stream()
                .map(this::loadClass)
                .filter(c -> c.getConstructors().length == 1);
    }

    private void registerBeanWithDep(Class<?> beanClass) {
        System.out.println("registerBeanWithDep=>" + beanClass);
        Class<?> beanType = getBeanType(beanClass);
        Constructor<?> constructor = beanClass.getConstructors()[0];
        Class<?>[] paramTypes = constructor.getParameterTypes();
        Object[] params = new Object[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
                params[i] = findInstance(paramTypes[i]);
            }
        Object instance = loadObjectWithParams(constructor, params);
        register(instance).as(beanType).complete();
    }

    private Object loadObjectWithParams(Constructor<?> constructor, Object[] params) {
        try {
                return constructor.newInstance(params);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("Cannot create instance with params", e);
            }
    }

    private Class<?> getBeanType(Class<?> beanClass) {
        System.out.println("beanClass=>" + beanClass);
        Class<?> beanType = beanClass;
        ComponentType typeAnn = beanClass.getAnnotation(ComponentType.class);
        if (typeAnn != null) {
            beanType = typeAnn.value();
        }
        return beanType;
    }

    private void registerBean(Class<?> beanClass) {
        System.out.println("registerBean=>" + beanClass);
        Class<?> beanType = getBeanType(beanClass);
        register(beanClass).as(beanType).complete();
    }

    private Class<?> loadClass(String name) {
        try {
            return getClass().getClassLoader().loadClass(name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot load class", e);
        }
    }

    Registration register(Class<?> regClass) {
        Object instance = loadObject(regClass);
        return new Registration(instance);
    }

    Registration register(Object instance) {
        return new Registration(instance);
    }

    private Object loadObject(Class<?> regClass) {
        System.out.println("regClass=>" + regClass);
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
