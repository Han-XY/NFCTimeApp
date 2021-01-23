package com.txbb.nfctimeapp.backend;

public interface Actor {

    void onUnknownTagRead();

    void onKnownTagRead();

    void onTagRegister(String id);

    void sync();
}
