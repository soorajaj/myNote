package com.example.ubuntu.myapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.ubuntu.myapplication.Model.Note;
import com.example.ubuntu.myapplication.R;
import com.example.ubuntu.myapplication.Utils.Utils;
import com.example.ubuntu.myapplication.listner.MyClickListener;
import com.example.ubuntu.myapplication.listner.SwipeDeleteListener;

import java.util.ArrayList;

/**
 * Created by ubuntu on 3/10/17.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.CustomViewHolder> {
    private Context mContext;
    private static MyClickListener myClickListener;
    private LayoutInflater inflater;
    private SwipeDeleteListener swipe_deleteListener;
    private ArrayList<Note> noteDatalist = new ArrayList<Note>();
    int pos;
    private final ViewBinderHelper binderHelper = new ViewBinderHelper();

    public NoteAdapter(Context mContext, ArrayList<Note> noteDatalist,SwipeDeleteListener swipe_deleteListener,MyClickListener myClickListener) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.noteDatalist = noteDatalist;
        this.swipe_deleteListener=swipe_deleteListener;
        this.myClickListener=myClickListener;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.noteadapter, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        final Note current = noteDatalist.get(position);
        if (noteDatalist != null && 0 <= position && position < noteDatalist.size()) {
            String ProductName = current.toString() + position;

            // Use ViewBindHelper to restore and save the open/close state of the SwipeRevealView
            // put an unique string id as value, can be any string which uniquely define the data
            binderHelper.bind(holder.swipeLayout, ProductName);

            // Bind your data here
            holder.bind(holder.getAdapterPosition(), current);
        }
        holder.textview_username.setText(noteDatalist.get(position).getNewNote());
        holder.textview_datetime.setText(Utils.dateMonthFormaterforNotification(noteDatalist.get(position).getDateTime(),mContext));
        holder.layout_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               myClickListener.onItemClick(position,view);
            }
        });
    }
    @Override
    public int getItemCount() {
        return noteDatalist.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        protected TextView textview_username,textview_datetime;
        private SwipeRevealLayout swipeLayout;
        FrameLayout layout_action;
        private View deleteLayout;
        public CustomViewHolder(View itemView) {
            super(itemView);
            textview_username= (TextView) itemView.findViewById(R.id.text_view_msgtext);
            textview_datetime= (TextView) itemView.findViewById(R.id.textview_datetime);
            itemView.setOnClickListener(this);
            swipeLayout = (SwipeRevealLayout) itemView.findViewById(R.id.swipe_layout);
            deleteLayout = itemView.findViewById(R.id.delete_layout);
            layout_action = (FrameLayout) itemView.findViewById(R.id.layout_action);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getPosition(), v);
        }
        public void bind(final int position, final Note current) {
            deleteLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Log.e("VSSAadapter",""+"---"+current.getProductname()+"---"+current.getQty()+"---"+current.getItemPrice()+"---"+current.getRecommendation());
                    //swipe_deleteListener.onItemDelete(current);
                    swipe_deleteListener.onItemDelete(current, position + "");
                }
            });

        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }
}
