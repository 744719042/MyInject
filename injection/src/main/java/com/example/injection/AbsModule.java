package com.example.injection;

import android.util.Pair;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public abstract class AbsModule implements Module {
    private Binder binder;

    private BindingBuilder builder;

    public AbsModule() {
        this.binder = new Binder();
    }

    public BindingBuilder bind(Class<?> clazz) {
        builder = new BindingBuilder();
        builder.keyClass(clazz);
        return builder;
    }

    public BindingBuilder annotatedWith(Name name) {
        builder.name(name);
        return builder;
    }

    public BindingBuilder eager() {
        builder.eager();
        return builder;
    }

    public BindingBuilder singleton() {
        builder.singleton();
        return builder;
    }

    public void to(Class<?> clazz) {
        builder.targetClass(clazz);
        Pair<Key, Binding> pair = builder.build();
        binder.addBinding(pair.first, pair.second);
        builder = null;
    }

    public void toInstance(Object object) {
        builder.targetObject(object);
        Pair<Key, Binding> pair = builder.build();
        binder.addBinding(pair.first, pair.second);
        builder = null;
    }

    public void toProvider(Class<? extends Provider> provider) {
        if (provider.isAnnotationPresent(Singleton.class)) {
            builder.singleton();
        }
        if (provider.isAnnotationPresent(Eager.class)) {
            builder.eager();
        }

        Constructor<?> constructors[] = provider.getConstructors();
        List<Key> keyList = new ArrayList<>();
        for (Constructor constructor : constructors) {
            if (constructor.isAnnotationPresent(Inject.class)) {
                Annotation[][] annotations = constructor.getParameterAnnotations();
                Class<?> paramTypes[] = constructor.getParameterTypes();
                if (paramTypes != null && paramTypes.length > 0) {
                    if (annotations != null && annotations.length > 0) {
                        for (int i = 0; i < annotations.length; i++) {
                            Annotation[] paramAnnotations = annotations[i];
                            Name name = null;
                            if (paramAnnotations != null && paramAnnotations.length > 0) {
                                for (Annotation annotation : paramAnnotations) {
                                    if (annotation instanceof Name) {
                                        name = (Name) annotation;
                                        break;
                                    }
                                }
                            }
                            Key key = new Key(paramTypes[i], name);
                            keyList.add(key);
                        }
                    }
                }

                break;
            }
        }
        builder.provider(provider);
        Pair<Key, Binding> pair = builder.build();
        pair.second.setDependencies(keyList);
        binder.addBinding(pair.first, pair.second);
        builder = null;
    }

    @Override
    public Binder getBinder() {
        return binder;
    }

    private static class BindingBuilder {
        private Class<?> keyClass;
        private Name name;
        private Class<?> targetClass;
        private Object targetObject;
        private boolean isEager;
        private boolean isSingleton;
        private Class<? extends Provider> provider;

        public BindingBuilder() {
        }

        public BindingBuilder keyClass(Class<?> keyClass) {
            this.keyClass = keyClass;
            return this;
        }

        public BindingBuilder name(Name name) {
            this.name = name;
            return this;
        }

        public BindingBuilder targetClass(Class<?> targetClass) {
            this.targetClass = targetClass;
            return this;
        }

        public BindingBuilder targetObject(Object object) {
            this.targetObject = object;
            return this;
        }

        public BindingBuilder provider(Class<? extends Provider> provider) {
            this.provider = provider;
            return this;
        }

        public BindingBuilder eager() {
            isEager = true;
            return this;
        }

        public BindingBuilder singleton() {
            isSingleton = true;
            return this;
        }

        public Pair<Key, Binding> build() {
            Key key = new Key(keyClass, name);
            Binding binding = new Binding();
            binding.setEager(isEager);
            binding.setSingleton(isSingleton);
            binding.setProvider(provider);
            binding.setTargetClass(targetClass);
            binding.setTargetObject(targetObject);

            return new Pair<>(key, binding);
        }
    }
}
