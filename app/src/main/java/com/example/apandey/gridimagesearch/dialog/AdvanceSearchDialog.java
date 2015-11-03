package com.example.apandey.gridimagesearch.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.apandey.gridimagesearch.R;
import com.example.apandey.gridimagesearch.models.FilterQuery;

/**
 * Created by apandey on 11/1/15.
 */
public class AdvanceSearchDialog extends DialogFragment {
    private EditText editImageSz;
    private EditText editImageColor;
    private EditText editImageType;
    private EditText editSiteFilter;
    private Button btnCancel;
    private Button btnSave;

    public interface DismissDialogListener {
        void onFinishSettingDialog(FilterQuery query);
    }

    public AdvanceSearchDialog() {
    }

    public static AdvanceSearchDialog newInstance(FilterQuery query) {
        AdvanceSearchDialog dialog = new AdvanceSearchDialog();
        Bundle args = new Bundle();
        args.putSerializable("filterSettings", query);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_advanced_search, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get field from view
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        editImageSz = (EditText) view.findViewById(R.id.evImgSize);
        editImageType = (EditText) view.findViewById(R.id.evImageType);
        editImageColor = (EditText) view.findViewById(R.id.evClrFilter);
        editSiteFilter = (EditText) view.findViewById(R.id.evSiteFilter);

        btnSave = (Button) view.findViewById(R.id.btnSave);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);


        editImageSz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editImageSz.setText("");
            }
        });
        editImageColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editImageColor.setText("");
            }
        });
        editImageType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editImageType.setText("");
            }
        });
        editSiteFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSiteFilter.setText("");
            }
        });
        btnSave.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                DismissDialogListener listener = (DismissDialogListener) getActivity();
                FilterQuery filterSettings = new FilterQuery(editImageSz.getText().toString(),
                        editImageType.getText().toString(),
                        editImageColor.getText().toString(),
                        editSiteFilter.getText().toString());
                listener.onFinishSettingDialog(filterSettings);
                dismiss();
            }
        });

        btnCancel.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
