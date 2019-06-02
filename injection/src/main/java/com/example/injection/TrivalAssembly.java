package com.example.injection;

import java.util.Map;

public class TrivalAssembly {
    private Map<Key, Object> singletonMap;

    TrivalAssembly(Map<Key, Object> singletonMap) {
        this.singletonMap = singletonMap;
    }

    public Object assembly(Key key, Binder binder) {
        Binding binding = binder.getBinding(key);
        if (binding.isSingleton() && singletonMap.containsKey(key)) {
            return singletonMap.get(key);
        }

        Object object = null;
        if (binding.getTargetObject() != null) {
             object = binding.getTargetObject();
        } else if (binding.getTargetClass() != null) {
            try {
                object = binding.getTargetClass().newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }

        if (binding.isSingleton() && object != null) {
            singletonMap.put(key, object);
        }

        return object;
    }
}
