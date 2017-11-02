package com.example.ubuntu.myapplication;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ubuntu.myapplication.SingletonManager.ObjectFactory;
import com.example.ubuntu.myapplication.Utils.Constents;

public class NoteCreationActivity extends AppCompatActivity {
    EditText editText_New_Note;
    private Toolbar mToolbar=null;
    String textNote="",noteDate="",noteId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(R.anim.slide_in, R.anim.nothing);
        setActionBar();
        editText_New_Note= (EditText) findViewById(R.id.editText_New_Note);
        if (getIntent().getExtras() != null) {
            textNote = getIntent().getExtras().getString(Constents.INTENT_DATA_NOTE);
            noteDate=getIntent().getExtras().getString(Constents.INTENT_DATA_NOTE_DATE);
            noteId=getIntent().getExtras().getString(Constents.INTENT_DATA_NOTE_ID);
            editText_New_Note.setText(textNote);
        }
    }

    private void setActionBar() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        Button menu_left = (Button)mToolbar.findViewById(R.id.menu_left);
        TextView text_view_title = (TextView) mToolbar.findViewById(R.id.text_view_title);
        text_view_title.setText("Create Notes");
        text_view_title.setVisibility(View.VISIBLE);
        menu_left.setVisibility(View.GONE);
//        mToolbar.setBackgroundColor(getResources().getColor(R.color.grey_new));
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        TextView btn_allsettings=(TextView) mToolbar.findViewById(R.id.btn_allsettings);
        final Drawable save = getResources().getDrawable(R.drawable.ic_check_24dp);
        save.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        btn_allsettings.setBackground(save);
        this.setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            // actionBar.setTitle(getResources().getString(R.string.action_settings));
            actionBar.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);

        }
        btn_allsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               createUser(editText_New_Note.getText().toString());
            }
        });
    }
    private void createUser(String name){
        ObjectFactory.getInstance().getOrderManager(NoteCreationActivity.this).saveData(name,noteId,noteDate,NoteCreationActivity.this);
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                backAction();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.nothing, R.anim.right_to_left);
    }
    private void backAction() {
        finish();
        overridePendingTransition(R.anim.nothing, R.anim.right_to_left);
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
