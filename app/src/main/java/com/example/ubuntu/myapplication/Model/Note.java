package com.example.ubuntu.myapplication.Model;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ubuntu on 6/10/17.
 */

public class Note implements Parcelable {
    String newNote;
    String dateTime;
    String notId;

    public Note() {
    }

    public Note(String newNote, String dateTime, String notId) {
        this.newNote = newNote;
        this.dateTime = dateTime;
        this.notId = notId;
    }

    protected Note(Parcel in) {
        newNote = in.readString();
        dateTime = in.readString();
        notId = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            Note note=new Note();
            note.newNote=in.readString();
            note.dateTime=in.readString();
            note.notId=in.readString();
            return note;
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getNewNote() {
        return newNote;
    }

    public void setNewNote(String newNote) {
        this.newNote = newNote;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getNotId() {
        return notId;
    }

    public void setNotId(String notId) {
        this.notId = notId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(newNote);
        parcel.writeString(dateTime);
        parcel.writeString(notId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Note tag = (Note) o;

        return notId.equals(tag.notId);
    }
}
