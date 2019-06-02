package com.example.injection;

public interface Module {
    void configure();
    Binder getBinder();
}
