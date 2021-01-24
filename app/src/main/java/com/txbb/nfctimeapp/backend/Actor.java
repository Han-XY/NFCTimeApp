package com.txbb.nfctimeapp.backend;

import com.txbb.nfctimeapp.TagProperties;
import com.txbb.nfctimeapp.frontend.AppState;

import java.util.Map;

public interface Actor {

    void onTagRegisterSuccess(String id);

    void onTagRegisterFailure();

    void onScanRequest(AppState nextState);

    void onBadRegister();

    void sync(Map<String, TagProperties> tags);

}
