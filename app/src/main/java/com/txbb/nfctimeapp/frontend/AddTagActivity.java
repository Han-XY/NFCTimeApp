package com.txbb.nfctimeapp.frontend;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import com.txbb.nfctimeapp.R;
import com.txbb.nfctimeapp.TagProperties;
import com.txbb.nfctimeapp.backend.Actor;
import com.txbb.nfctimeapp.backend.CustomActivity;

import java.util.List;
import java.util.Map;

public class AddTagActivity extends CustomActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tag);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New activity!");  // provide compatibility to all the versions
    }

}