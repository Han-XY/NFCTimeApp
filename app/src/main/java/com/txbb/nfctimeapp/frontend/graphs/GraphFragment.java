package com.txbb.nfctimeapp.frontend.graphs;

import androidx.lifecycle.ViewModelProvider;

import android.app.Fragment;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.txbb.nfctimeapp.R;

public class GraphFragment extends Fragment {

    private GraphViewModel mViewModel;

    public static GraphFragment newInstance() {
        return new GraphFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_graph, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(GraphViewModel.class);
//        // TODO: Use the ViewModel
    }

}