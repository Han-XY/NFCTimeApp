package com.txbb.nfctimeapp.backend;

import com.txbb.nfctimeapp.TagProperties;

import java.util.Map;

public interface Actor {

    void onUnknownTagRead();

    void onKnownTagRead();

    void onTagRegister(String id);

    void sync(Map<String, TagProperties> tags);
}
