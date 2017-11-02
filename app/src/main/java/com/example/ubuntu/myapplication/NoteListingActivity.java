package com.example.ubuntu.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.ubuntu.myapplication.Adapter.DividerItemDecorator;
import com.example.ubuntu.myapplication.Adapter.NoteAdapter;
import com.example.ubuntu.myapplication.Model.Note;
import com.example.ubuntu.myapplication.SingletonManager.ObjectFactory;
import com.example.ubuntu.myapplication.Utils.Constents;
import com.example.ubuntu.myapplication.listner.MyClickListener;
import com.example.ubuntu.myapplication.listner.SwipeDeleteListener;

import java.util.ArrayList;

public class NoteListingActivity extends AppCompatActivity {
    RecyclerView recycler_note;
    private ArrayList<Note> raffleItemList;
    public static final String BROADCAST_DESIGNATION_LIST_CHANGED = "ACTION_DESIGNATION_LIST_CHANGED";
    NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        mFirebaseDatabase = firbaseClass.Databseinstannce();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoteListingActivity.this, NoteCreationActivity.class);
                startActivity(intent);
            }
        });
        recycler_note = (RecyclerView) findViewById(R.id.recycler_note);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_note.setLayoutManager(mLayoutManager);
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecorator(this, LinearLayoutManager.VERTICAL);
        recycler_note.addItemDecoration(itemDecoration);
        LocalBroadcastManager.getInstance(this).registerReceiver(myBroadcastreciver,
                new IntentFilter(BROADCAST_DESIGNATION_LIST_CHANGED));
        ObjectFactory.getInstance().getOrderManager(NoteListingActivity.this);
    }

    public void populateListview(final ArrayList<Note> noteArrayList) {
            noteAdapter = new NoteAdapter(NoteListingActivity.this, noteArrayList, new SwipeDeleteListener() {
                @Override
                public void onItemDelete(Note current, String position) {
                    ObjectFactory.getInstance().getOrderManager(NoteListingActivity.this).removeData(current);
                    noteArrayList.remove(position);
                    noteAdapter.notifyDataSetChanged();
                }
            }, new MyClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    Intent intent = new Intent(NoteListingActivity.this, NoteCreationActivity.class);
                    intent.putExtra(Constents.INTENT_DATA_NOTE, noteArrayList.get(position).getNewNote());
                    intent.putExtra(Constents.INTENT_DATA_NOTE_ID, noteArrayList.get(position).getNotId());
                    intent.putExtra(Constents.INTENT_DATA_NOTE_DATE, noteArrayList.get(position).getDateTime());
                    startActivity(intent);
                }
            });
            recycler_note.setAdapter(noteAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myBroadcastreciver);
    }
private BroadcastReceiver myBroadcastreciver=new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equalsIgnoreCase(BROADCAST_DESIGNATION_LIST_CHANGED)){
            raffleItemList=ObjectFactory.getInstance().getOrderManager(NoteListingActivity.this).getDesignations();
            populateListview(raffleItemList);
        }
    }
};
    @Override
    protected void onResume() {
        super.onResume();
    }
}
