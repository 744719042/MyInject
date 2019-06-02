package com.example.injection;

import java.util.List;

public class GlobalModuleRegistry {
    private List<Module> modules;

    private GlobalModuleRegistry() {
    }

    private static class GlobalModuleRegistryHolder {
        private static final GlobalModuleRegistry INSTANCE = new GlobalModuleRegistry();
    }

    public static GlobalModuleRegistry getGlobalModuleRegistry() {
        return GlobalModuleRegistryHolder.INSTANCE;
    }

    public void registerGlobalModule(Module module) {
        modules.add(module);
    }
}
