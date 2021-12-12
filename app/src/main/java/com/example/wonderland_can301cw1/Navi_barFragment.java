package com.example.wonderland_can301cw1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import org.litepal.LitePal;


public class Navi_barFragment extends Fragment {

    private View root;

    private ImageButton Btn1;

    private RelativeLayout rl1;

    private RelativeLayout rl2;

    private RelativeLayout rl3;

    private RelativeLayout rl4;

    public static void actionStart(Context context, int cat_id){
        cat_id=cat_id;
        Intent intent = new Intent(context, ViewPostsActivity.class);
        intent.putExtra("cat_id", cat_id);
        context.startActivity(intent);
    }

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
        rl2 = root.findViewById(R.id.sms_relay_layout);
        rl3 = root.findViewById(R.id.email_relay_layout);
        rl4 = root.findViewById(R.id.rule_layout1);
        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionStart(getContext(), LitePal.where("name = ?", "Postgraduate Studies").find(Category.class).get(0).getId());
            }
        });
        rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionStart(getContext(),LitePal.where("name = ?", "In-class Sources").find(Category.class).get(0).getId());
            }
        });
        rl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionStart(getContext(),LitePal.where("name = ?", "Language Tests").find(Category.class).get(0).getId());
            }
        });
        rl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionStart(getContext(),LitePal.where("name = ?", "Research and Internship").find(Category.class).get(0).getId());
            }
        });

        // Inflate the layout for this fragment
        return root;
    }
}