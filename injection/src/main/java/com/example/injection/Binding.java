package com.example.injection;

import java.util.ArrayList;
import java.util.List;

public class Binding {
    private boolean isSingleton = false;
    private boolean isEager = false;
    private Class<?> targetClass;
    private Object targetObject;
    private Class<? extends Provider> provider;
    private List<Key> dependencies = new ArrayList<>();

    public Binding() {

    }

    public boolean isSingleton() {
        return isSingleton;
    }

    public void setSingleton(boolean singleton) {
        isSingleton = singleton;
    }

    public boolean isEager() {
        return isEager;
    }

    public void setEager(boolean eager) {
        isEager = eager;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
    }

    public Object getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(Object targetObject) {
        this.targetObject = targetObject;
    }

    public Class<? extends Provider> getProvider() {
        return provider;
    }

    public void setProvider(Class<? extends Provider> provider) {
        this.provider = provider;
    }

    public List<Key> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<Key> dependencies) {
        this.dependencies = dependencies;
    }
}
