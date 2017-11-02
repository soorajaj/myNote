package com.example.ubuntu.myapplication.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.example.ubuntu.myapplication.Model.Note;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by ubuntu on 30/10/17.
 */

public class DesignationsManager {
    public static final String BROADCAST_DESIGNATION_LIST_CHANGED = "ACTION_DESIGNATION_LIST_CHANGED";

    private final Context context;
    private final ArrayList<Note> designations = new ArrayList<>();


    public DesignationsManager(Context context) {
        this.context = context.getApplicationContext();
        observeCollectionNode();
    }

    private void observeCollectionNode() {
        getDatabaseReference().addListenerForSingleValueEvent(valueEventListener);
    }
    private Query getDatabaseReference() {
        return FirebaseDatabase.getInstance().getReference(Constents.NOTE);
    }
    private DatabaseReference getDatabaseReferences() {
        return FirebaseDatabase.getInstance().getReference(Constents.NOTE);
    }

    private final ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            designations.clear();
            for (DataSnapshot desSnapshot : dataSnapshot.getChildren()) {
                Note designationsItem = desSnapshot.getValue(Note.class);
                designationsItem.setNotId(desSnapshot.getKey());
                designations.add(designationsItem);
            }
            sendBroadcast();
            getDatabaseReference().addChildEventListener(childEventListener);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            sendBroadcast();
        }
    };
    private final ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Note locDesignations = dataSnapshot.getValue(Note.class);
            locDesignations.setNotId(dataSnapshot.getKey());
            if (!designations.contains(locDesignations)) {
                designations.add(locDesignations);
                sendBroadcast();
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            Note locDesignations = dataSnapshot.getValue(Note.class);
            locDesignations.setNotId(dataSnapshot.getKey());
            if (!designations.isEmpty()) {
                int index = designations.indexOf(locDesignations);

                if (index != -1) {
                    designations.set(index, locDesignations);
                    sendBroadcast();
                }
            }
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            Note locDesignations = dataSnapshot.getValue(Note.class);
            locDesignations.setNotId(dataSnapshot.getKey());
            if (designations.remove(locDesignations)) {
                sendBroadcast();
            }
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            sendBroadcast();
        }
    };

    private void sendBroadcast() {
        Intent intent = new Intent(BROADCAST_DESIGNATION_LIST_CHANGED);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    //get the complete collections
    public ArrayList<Note> getDesignations() {
        return designations;
    }

    public void saveData(String data, String id,String noteDate, Context context){
        if(id.equalsIgnoreCase("")){
            noteDate=Utils.getCurrentDateTime(context);
            id = getDatabaseReferences().push().getKey();
        }
        Note user = new Note(data,noteDate,id);
        getDatabaseReferences().child(id).setValue(user);
        sendBroadcast();
    }
    public void removeData(Note note){
        getDatabaseReferences().child(note.getNotId()).removeValue();
        sendBroadcast();
    }

 /*  public void logout() {
       removeListener();
       designations.clear();

   }

   private void removeListener() {
       getDatabaseReference().removeEventListener(childEventListener);
       getDatabaseReference().removeEventListener(valueEventListener);
   }*/
}