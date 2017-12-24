package com.dimazombie;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DependenciesConfig {
    private List<Object> regInstances = new ArrayList<Object>();

    void register(Class<?> regClass){
        Object instance = loadObject(regClass);
        this.regInstances.add(instance);
    }

    private Object loadObject(Class<?> regClass) {
        try{
            return regClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException("Cannot load class instance", e);
        }
    }

    <T> T findInstance(Class<T> type){
        return (T) regInstances.stream().filter(i -> Objects.equals(i.getClass(), type)).findFirst().get();
    }

}
