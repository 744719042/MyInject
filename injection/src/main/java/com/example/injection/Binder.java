package com.example.injection;

import java.util.HashMap;
import java.util.Map;

public class Binder {
    private Map<Key, Binding> bindings = new HashMap<>();

    public void addBinding(Key key, Binding binding) {
        bindings.put(key, binding);
    }

    public Map<Key, Binding> getBindings() {
        return bindings;
    }

    public Binding getBinding(Key key) {
        return bindings.get(key);
    }
}
