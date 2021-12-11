package com.example.wonderland_can301cw1;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SettingsFragment extends Fragment {

    private AppCompatImageButton manualBtn, permissionBtn, logoutBtn;
    private View root;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (root == null) {
            root = inflater.inflate(R.layout.fragment_settings, container, false);
        }

        init();

        // Inflate the layout for this fragment
        return root;
    }

    public void init(){
        logoutBtn = root.findViewById(R.id.logout);
        manualBtn = root.findViewById(R.id.manual);
        permissionBtn = root.findViewById(R.id.authority);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        permissionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
                ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");  // According to test mobile device
                intent.setComponent(comp);
                startActivity(intent);
            }
        });
    }
}