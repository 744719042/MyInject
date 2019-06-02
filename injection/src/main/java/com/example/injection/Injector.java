package com.example.injection;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Injector {
    private Binder binder = new Binder();
    private Map<Key, Object> singletonMap = new ConcurrentHashMap<>();
    private TrivalAssembly trivalAssembly;
    private ProviderAssembly providerAssembly;

    private Injector(Module... modules) {
        for (Module module : modules) {
            module.configure();
            binder.getBindings().putAll(module.getBinder().getBindings());
        }
        trivalAssembly = new TrivalAssembly(singletonMap);
        providerAssembly = new ProviderAssembly(trivalAssembly, singletonMap);

        Map<Key, Binding> bindings = binder.getBindings();
        for (Map.Entry<Key, Binding> entry : bindings.entrySet()) {
            Binding binding = entry.getValue();
            if (binding.isEager() && binding.isSingleton()) {
                initSingleInstance(entry.getKey());
            }
        }
    }

    private void initSingleInstance(Key key) {
        Binding binding = binder.getBinding(key);
        if (binding.getTargetObject() != null) {
            trivalAssembly.assembly(key, binder);
        } else if (binding.getTargetClass() != null) {
            trivalAssembly.assembly(key, binder);
        } else if (binding.getProvider() != null) {
            detectCircle(key, binder, new HashSet<Key>());
             providerAssembly.assembly(key, binder);
        }
    }

    public void inject(Object object) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Inject.class)) {
                Class<?> type = field.getType();
                Name name = field.getAnnotation(Name.class);
                field.setAccessible(true);
                try {
                    field.set(object, assembly(new Key(type, name)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Object assembly(Key key) {
        Binding binding = binder.getBinding(key);
        if (binding.getProvider() != null) {
            detectCircle(key, binder, new HashSet<Key>());
            return providerAssembly.assembly(key, binder);
        } else {
            return trivalAssembly.assembly(key, binder);
        }
    }

    private void detectCircle(Key key, Binder binder, Set<Key> visited) {
        if (visited.contains(key)) {
            throw new IllegalArgumentException("Circle dependency exists");
        }

        Binding binding = binder.getBinding(key);
        List<Key> dependencies = binding.getDependencies();
        if (dependencies == null || dependencies.isEmpty()) {
            return;
        }

        visited.add(key);
        for (Key dependencyKey : dependencies) {
            detectCircle(dependencyKey, binder, visited);
        }
    }
}
