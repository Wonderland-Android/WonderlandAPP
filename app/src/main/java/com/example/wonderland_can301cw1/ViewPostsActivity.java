package com.example.wonderland_can301cw1;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
import androidx.fragment.app.Fragment;

import com.google.android.material.imageview.ShapeableImageView;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;


public class ViewPostsActivity extends AppCompatActivity {
    private static final int LAUNCH_NEWPOST_ACTIVITY = 0;
    private List<Card> cardList = new ArrayList<>();
    private SearchView searchView;
    private boolean sortMethod = true;
    private String searchContentCopy = "";
    private int categoryCopy = -1;


    public static void actionStart1(Context context, int cat_id){
        cat_id=cat_id;
        Intent intent = new Intent(context,MakeNewPostActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        intent.putExtra("cat_id", cat_id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_posts);
        Intent intent = getIntent();

        int data = intent.getIntExtra("cat_id",-1);
        categoryCopy = data;

        initCards("",data);
        initSearchView();
        initSort();
        initListView();
        initNewPostButton(data);
    }



    @Override
    public void onStart(){
        super.onStart();
        refreshListView();
    }

    @Override
    public void onResume(){
        super.onResume();
        refreshListView();
    }



    private void initCards(String searchContent, int category) {
        if (category>-1) {
            cardList.clear();
            List<Post> postList;
            TextView noContent;
            noContent = (TextView) findViewById(R.id.no_Content);
            if (sortMethod) {
                postList = LitePal.where("category_id = ? and title like ?", String.valueOf(category), "%" + searchContent + "%").order("create_time").find(Post.class);
            } else {
                postList = LitePal.where("category_id = ? and title like ?", String.valueOf(category), "%" + searchContent + "%").order("likes desc").find(Post.class);
            }
            System.out.println("--------");
            if (postList.size() != 0) {
                noContent.setVisibility(View.GONE);
                System.out.println(postList.get(0).getId());
                for (int i = 0; i < postList.size(); i++) {
                    cardList.add(new Card(postList.get(i).getId(), postList.get(i).getUser().getImage(), postList.get(i).getUser().getName(), postList.get(i).getTitle(), postList.get(i).getLikes(), postList.get(i).getCreate_time()));
                }
            } else {
                noContent.setVisibility(View.VISIBLE);
            }
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
                searchContentCopy = query;
                initCards(query,categoryCopy);
                CardAdapter adapter = initListView();
                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (TextUtils.isEmpty(newText)){
                    searchContentCopy = "";
                    initCards("",categoryCopy);
                    CardAdapter adapter = initListView();
                    adapter.notifyDataSetChanged();
                    return false;
                }
                return false;
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




    private void initNewPostButton(int data) {
        ImageButton makeNewPost = findViewById(R.id.new_post);
        makeNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionStart1(ViewPostsActivity.this,data);
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
                        initCards(searchContentCopy,categoryCopy);
                        CardAdapter adapter = initListView();
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();


    }
}
