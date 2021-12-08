package com.example.wonderland_can301cw1;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

public class CommentDialog extends Dialog {
    private EditText etContent;
    private LinearLayout llBtnReply;
    private Context mContext;

    public CommentDialog(Context context){
        super(context, R.style.MyNoFrame_Dialog);
        mContext = context;
        init();
    }
    private CommentDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
        init();
    }

    private void init() {
        setContentView(R.layout.dialog_replyform);

        // 设置宽度
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);

        etContent = (EditText) findViewById(R.id.dialog_reply_etContent);
        llBtnReply = (LinearLayout) findViewById(R.id.dialog_reply_llBtnReply);


        // 弹出键盘
        etContent.setFocusable(true);
        etContent.setFocusableInTouchMode(true);
        etContent.requestFocus();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) mContext
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(etContent, 0);
            }
        }, 200);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public CommentDialog setContent(String content) {
        etContent.setText(content);
        return this;
    }

    public CommentDialog setHintText(String hint) {
        etContent.setHint(hint);
        return this;
    }

    public String getContent() {
        return etContent.getText().toString();
    }

    public CommentDialog setOnBtnCommitClickListener(
            android.view.View.OnClickListener onClickListener) {
        llBtnReply.setOnClickListener(onClickListener);
        return this;
    }



}
