package com.example.wonderland_can301cw1;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;


public class Navi_barFragment extends Fragment {

    private View root;

    private ImageButton Btn1;

    private RelativeLayout rl1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (root == null){
            root = inflater.inflate(R.layout.fragment_navi_bar, container, false);
        }

        rl1 = root.findViewById(R.id.rule_layout);
        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),ViewPostsActivity.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return root;
    }
}