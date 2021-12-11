package com.example.wonderland_can301cw1;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ProfileFragment extends Fragment {

    private View root;
    private ImageView ivAvator;
    private EditText editNickName;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmmss", Locale.CHINA);
    private int REQUEST_CAMERA = 110;
    private int REQUEST_PICKER = 111;
    private File file;
    private AppCompatImageButton aImgBtn1;
    private TextView userName;
    private MyCallBack myCallBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (root == null) {
            root = inflater.inflate(R.layout.fragment_profile, container, false);
        }
        init();

        // Inflate the layout for this fragment
        return root;
    }

    private void init() {
        ivAvator = (ImageView) root.findViewById(R.id.profile0);
        aImgBtn1 = root.findViewById(R.id.changeName);
        userName = root.findViewById(R.id.profile_username);
//        EditText et = new EditText(getActivity());

        String[] mode = {"Camera", "Pick", "Cancel"};
        ListView listView = new ListView(getActivity());
        listView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        listView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.simple_list_item, mode));
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        dialog.setContentView(listView);

        // click the avator, pop up the dialog;
        ivAvator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        // bottom pop up menu
        listView.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    // camera
//                    System.out.println("Camera");
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    file = new File(getContext().getExternalCacheDir(), simpleDateFormat.format(new Date()) + ".png");
                    Uri uriForFile = FileProvider.getUriForFile(getContext(), "com.example.wonderland_can301cw1.fileProvider", file);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
                    startActivityForResult(intent, REQUEST_CAMERA);
                    break;
                case 1:
                    // select
                    Intent intent1 = new Intent(Intent.ACTION_PICK);
                    intent1.setType("image/*");
                    startActivityForResult(intent1, REQUEST_PICKER);
                    break;
                case 2:
                    dialog.dismiss();
                    break;
                default:
                    break;
            }
        });

        // username changing listener
        aImgBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et = new EditText(getActivity());
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Please Enter New Username")
                        .setIcon(R.drawable.face1)
                        .setView(et).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (myCallBack != null) {
                            myCallBack.myBack(et.getText().toString());
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create();
                builder.show();

            }
        });

        myCallBack = new MyCallBack() {
            @Override
            public void myBack(String str) {
                userName.setText(str);
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                Glide.with(this).load(file).into(ivAvator);
                // Add into database
            } else if (requestCode == REQUEST_PICKER) {
                try {
                    Uri data1 = data.getData();
                    ContentResolver contentResolver = getContext().getContentResolver();
                    String[] column = {MediaStore.Images.ImageColumns.DATA};
                    Cursor query = contentResolver.query(data1, column, null, null, null);
                    query.moveToNext();
                    int columnIndex = query.getColumnIndex(column[0]);
                    String string = query.getString(columnIndex);

                    Glide.with(this).load(string).into(ivAvator);
                    System.out.println("--------------------------------");
                    System.out.println(string);
                    System.out.println("--------------------------------");
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}