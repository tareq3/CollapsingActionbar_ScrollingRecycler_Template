/*
 * Created by Tareq Islam on 8/10/18 1:44 AM
 *
 *  Last modified 8/10/18 1:37 AM
 */

package com.mti.collapsingactionbar_scrollingactivity_template;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mti.collapsingactionbar_scrollingactivity_template.Adapter.ListItemAdapter;
import com.mti.collapsingactionbar_scrollingactivity_template.model.ToDo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Primary List of Data
    List<ToDo> mToDoList=new ArrayList<ToDo>(
            Arrays.asList(
                    new ToDo("1","Eat","eating should be controlled"),
                    new ToDo("2","Walk","walking is good for health"),
                    new ToDo("3", "Salat", "Is the best meditation in this world")
            )
    );



    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManagerRecyler;

    ListItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_col);

       final Toolbar toolbar  =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);


        AppBarLayout appBarLayout ;
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {


            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= -appBarLayout.getTotalScrollRange() + toolbar.getHeight()) {
                    //Toolbar Expanded
                    toolbar.setTitle(R.string.app_name);
                  if(mMenu!=null) {
                      mMenu.findItem(R.id.action_settings).setVisible(true);
                      mMenu.findItem(R.id.menu_search).setVisible(true);
                  }

                } else {

                    toolbar.setTitle("");
                    if(mMenu!=null) {
                        mMenu.findItem(R.id.action_settings).setVisible(false);
                        mMenu.findItem(R.id.menu_search).setVisible(false);
                    }

                }
            }
        });



        initRecyclerView();

        loadDataIntoRecycler();

    }
    //Customize recyclerView
    public void initRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerview_playlist);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManagerRecyler = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManagerRecyler);

    }

    //Load data into Recycler Important
    private void loadDataIntoRecycler() {

        //Todo: Prepare your DataList


        //Then when data ArrayList Has the data then set the Adapter with Data
        mAdapter= new ListItemAdapter(MainActivity.this,mToDoList);
        mRecyclerView.setAdapter(mAdapter);
    }

















/*************************************** Menu Items **********************************************/



private Menu mMenu; //for show hide of menu Item
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

       mMenu= menu;
       getMenuInflater().inflate(R.menu.menu_main, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch(item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(),
                        "Setting...",
                        Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }







    /****************************************** Recycler Item OnClick Item Context Menu******************************************/

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals("DELETE"))
            deleteItem(item.getOrder());
        return super.onContextItemSelected(item);
    }

    private void deleteItem(int order) {
        Toast.makeText(this, "Delete Item at "+ order + " and id: ", Toast.LENGTH_SHORT).show();
        mToDoList.remove(order);

        loadDataIntoRecycler();//after deleting data reload data
    }
}
