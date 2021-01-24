package com.txbb.nfctimeapp.frontend.editor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.txbb.nfctimeapp.FrontBackInterface;
import com.txbb.nfctimeapp.R;
import com.txbb.nfctimeapp.TagProperties;
import com.txbb.nfctimeapp.backend.Actor;
import com.txbb.nfctimeapp.backend.CustomActivity;
import com.txbb.nfctimeapp.category.Category;
import com.txbb.nfctimeapp.category.CategoryButtonGroup;
import com.txbb.nfctimeapp.frontend.AppState;
import com.txbb.nfctimeapp.frontend.GeneralEditorFragment;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditorFragment extends GeneralEditorFragment implements Actor {

    private String tagId;
    private String oldTagName;
    private Category oldTagCategory;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_editor, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /* Display the information of the selected tag. */
        this.addCategoryButtons(R.id.editorTableLayout);

        CustomActivity activity = (CustomActivity) getActivity();
        this.tagId = activity.getSelectedTagId();
        this.oldTagName = activity.getSelectedTagTitle();
        this.oldTagCategory = activity.getSelectedTagCategory();

        EditText editText = getActivity().findViewById(R.id.editorEditText);
        editText.setText(oldTagName);

        this.buttonGroup.select(oldTagCategory.getCategoryId());




        CategoryButtonGroup buttonGroup = this.buttonGroup;

        FloatingActionButton fabEdit = getActivity().findViewById(R.id.editFab);
        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = getActivity().findViewById(R.id.editorEditText);
                if (editText.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter a name", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!hasSelectedCategory()) {
                    Toast.makeText(getActivity(), "Please select a category", Toast.LENGTH_LONG).show();
                    return;
                }

                startEdit(editText.getText().toString(), buttonGroup.getSelectedCategory());
            }
        });

        FloatingActionButton fabDelete = getActivity().findViewById(R.id.deleteFab);
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDelete();
            }
        });
    }

    private void startDelete() {
        Log.i("NFC", "Starting delete");
        CustomActivity activity = (CustomActivity) getActivity();
        FrontBackInterface frontBackInterface = activity.getFrontBackInterface();

        String tagId = activity.getSelectedTagId();

        frontBackInterface.deleteTag(tagId);

        // assume success
    }

    private void startEdit(String name, int category) {
        Log.i("NFC", "Starting edit");
        CustomActivity activity = (CustomActivity) getActivity();
        FrontBackInterface frontBackInterface = activity.getFrontBackInterface();

        String tagId = activity.getSelectedTagId();

        frontBackInterface.updateTagProperties(tagId, new TagProperties(name, category));
    }


    @Override
    public void onTagRegisterSuccess(String id) {

    }

    @Override
    public void onTagRegisterFailure() {

    }

    @Override
    public void onScanRequest(AppState nextState) {

    }

    @Override
    public void onBadRegister() {

    }

    @Override
    public void sync(Map<String, TagProperties> tags) {

    }

}