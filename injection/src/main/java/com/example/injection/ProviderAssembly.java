package com.example.injection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class ProviderAssembly {
    private TrivalAssembly trivalAssembly;
    private Map<Key, Object> singletonMap;

    ProviderAssembly(TrivalAssembly trivalAssembly, Map<Key, Object> singletonMap) {
        this.trivalAssembly = trivalAssembly;
        this.singletonMap = singletonMap;
    }

    public Object assembly(Key key, Binder binder) {
        Binding binding = binder.getBinding(key);
        if (binding.isSingleton() && singletonMap.containsKey(key)) {
            return singletonMap.get(key);
        }

        List<Key> dependencies = binding.getDependencies();
        if (dependencies == null || dependencies.isEmpty()) {
            try {
                Object object = binding.getProvider().newInstance().get();
                if (binding.isSingleton() && object != null) {
                    singletonMap.put(key, object);
                }
                return object;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            Object[] objects = new Object[dependencies.size()];
            Class<?>[] paramTypes = new Class[dependencies.size()];
            for (int i = 0; i < objects.length; i++) {
                Key dependencyKey = dependencies.get(i);
                Binding dependencyBinding = binder.getBinding(key);
                if (dependencyBinding.getProvider() != null) {
                    objects[i] = assembly(dependencyKey, binder);
                } else {
                    objects[i] = trivalAssembly.assembly(dependencyKey, binder);
                }
                paramTypes[i] = dependencyKey.getClazz();
            }

            try {
                Constructor<?> constructor = binding.getProvider().getConstructor(paramTypes);
                Object object = ((Provider) (constructor.newInstance(objects))).get();
                if (binding.isSingleton() && object != null) {
                    singletonMap.put(key, object);
                }
                return object;
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
