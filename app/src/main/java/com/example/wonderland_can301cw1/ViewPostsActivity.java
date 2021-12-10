package com.example.wonderland_can301cw1;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;


public class ViewPostsActivity extends AppCompatActivity {
    private static final int LAUNCH_NEWPOST_ACTIVITY = 0;
    private List<Card> cardList = new ArrayList<>();
    private SearchView searchView;
    private boolean sortMethod = true;
    private List<String> searchContent = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_posts);

        initCards();
        initSearchView();
        initSort();
        initListView();
        initNewPostButton();
    }



    private void initCards() {
        cardList.clear();
        List<Post> postList;
        if (sortMethod) {
            postList = LitePal.order("create_time").find(Post.class);
        } else {
            postList = LitePal.order("likes").find(Post.class);
        }
        System.out.println("--------");
        if (postList.size() != 0) {
            System.out.println(postList.get(0).getId());
            for (int i = 0; i < postList.size(); i++) {
                cardList.add(new Card(postList.get(i).getId(), postList.get(i).getUser().getImage(), postList.get(i).getUser().getName(), postList.get(i).getTitle(), postList.get(i).getLikes(), postList.get(i).getCreate_time()));
            }
        }else{
            TextView noContent;
            noContent = (TextView) findViewById(R.id.no_Content);
            noContent.setVisibility(View.VISIBLE);
        }
    }{
        cardList.clear();
        List<Post> postList;
        if (sortMethod) {
            postList = LitePal.order("create_time").find(Post.class);
        }else{
            postList = LitePal.order("likes").find(Post.class);
        }
        System.out.println("--------");
        System.out.println(postList.get(0).getId());
        for (int i=0;i<postList.size();i++){
            cardList.add(new Card(postList.get(i).getId(),postList.get(i).getUser().getImage(), postList.get(i).getUser().getName(),postList.get(i).getTitle(), postList.get(i).getLikes(),postList.get(i).getCreate_time()));
        }
    }


    private void initSearchView() {
        searchView = (SearchView) findViewById(R.id.sv);
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Enter content");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchContent.clear();
                String[] queryKey = query.trim().split(" ");
                for(int i=0;i<queryKey.length;i++){
                    searchContent.add(queryKey[i]);
                }
                initCards();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }


    @SuppressLint("ClickableViewAccessibility")
    private void initSort() {
        final ImageView imageVie = findViewById(R.id.sort);
        imageVie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(imageVie);
            }
        });
        imageVie.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideSoftKeyboard(ViewPostsActivity.this);
                return false;
            }
        });
    }


    @SuppressLint("ClickableViewAccessibility")
    private CardAdapter initListView() {
        CardAdapter adapter = new CardAdapter(ViewPostsActivity.this, R.layout.activity_view_posts_card, cardList);
        final CustomListView listView = (CustomListView) findViewById(R.id.list_card);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Card card = cardList.get(position);
                System.out.println(view.getId());
                Toast.makeText(ViewPostsActivity.this, card.getUsername(), Toast.LENGTH_SHORT).show();
                hideSoftKeyboard(ViewPostsActivity.this);
            }
        });
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftKeyboard(ViewPostsActivity.this);
                return false;
            }
        });
        listView.setonRefreshListener(new CustomListView.OnRefreshListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onRefresh() {
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        try{
                            Thread.sleep(1000);
                        } catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        refreshListView();
                        return null;
                    }
                    protected void onPostExecute(Void result) {
                        adapter.notifyDataSetChanged();
                        listView.onRefreshComplete();
                    }
                }.execute();
            }
        });
        return adapter;
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == LAUNCH_NEWPOST_ACTIVITY && resultCode == Activity.RESULT_OK)
        {
            Post newPost = new Post();
            String[] returnIntent = intent.getStringArrayExtra("Post_Return");

            CurrentUser currentUser = LitePal.findFirst(CurrentUser.class);
            newPost.setUser(LitePal.find(User.class,currentUser.getUser_id()));
            newPost.setTitle(returnIntent[0]);
            newPost.setContent(returnIntent[1]);
            newPost.save();
        }
    }



    private void initNewPostButton() {
        ImageButton makeNewPost = findViewById(R.id.new_post);
        makeNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewPostsActivity.this, MakeNewPostActivity.class);
                startActivity(intent);
            }
        });
    }


    private static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }


    @SuppressLint("ResourceType")
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.layout.activity_view_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch ((String)item.getTitle()) {
                    case "按时间排序":
                        sortMethod = true;
                        break;
                    case "按点赞排序":
                        sortMethod = false;
                        break;
                    default:
                        break;
                }

                Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                refreshListView();
                return false;
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
            }
        });
        popupMenu.show();
    }



    public void refreshListView(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(0);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initCards();
                        CardAdapter adapter = initListView();
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();


    }
}