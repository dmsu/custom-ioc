package com.dimazombie;

public class Injector {
    private DependenciesConfig config;

    Injector(DependenciesConfig config) {
        this.config = config;
    }

    <T> T get(Class<T> clazz) {
        return config.findInstance(clazz);
    }
}
