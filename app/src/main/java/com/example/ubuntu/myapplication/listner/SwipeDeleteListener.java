package com.example.ubuntu.myapplication.listner;

import com.example.ubuntu.myapplication.Model.Note;

/**
 * Created by ubuntu on 27/7/17.
 */

public interface SwipeDeleteListener {

    public void onItemDelete(Note current, String position);
}
