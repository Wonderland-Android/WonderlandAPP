package com.example.wonderland_can301cw1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.List;

public class CardAdapter extends ArrayAdapter<Card> {
    private int resourceId;
    private Context context;

    public CardAdapter(Context context, int textViewResourceId, List<Card> objects){
        super(context,textViewResourceId,objects);
        this.context = context;
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Card card = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.cardImage = (ImageView) view.findViewById(R.id.profile);
            viewHolder.cardUsername = (TextView) view.findViewById(R.id.username);
            viewHolder.cardTitle = (TextView) view.findViewById(R.id.title);
            viewHolder.cardLikeNum = (TextView) view.findViewById(R.id.likeNum);
            viewHolder.cardTime = (TextView) view.findViewById(R.id.time);
            view.setTag(viewHolder);

        } else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
//        Byte[] cardByte=card.getProfile();
//        byte[] bytes=new byte[cardByte.length];
//        for(int i=0;i<bytes.length;i++)
//        {
//
//            bytes[i]= cardByte[i];
//        }
//        if(bytes!=null && !bytes.equals("")){
//
//            Bitmap map = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//            BitmapDrawable bd=new BitmapDrawable(map);
//            viewHolder.cardImage.setImageDrawable(bd);
//        }else{
//
//        }

        viewHolder.cardImage.setImageResource(card.getProfile());

        int idInfo = card.getId();
        viewHolder.cardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MakeNewPostActivity.class);
                intent.putExtra("profile_position",idInfo+"");
                context.startActivity(intent);
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostDetailActivity.actionStart(context,idInfo);
            }
        });
        viewHolder.cardUsername.setText(card.getUsername());
        viewHolder.cardTitle.setText(card.getTitle());
        viewHolder.cardLikeNum.setText(card.getLikeNum()+"人赞过");
        viewHolder.cardTime.setText(card.getTime().toString());
        return view;
    }

    class ViewHolder{
        ImageView cardImage;
        TextView cardUsername;
        TextView cardTitle;
        TextView cardLikeNum;
        TextView cardTime;
    }
}