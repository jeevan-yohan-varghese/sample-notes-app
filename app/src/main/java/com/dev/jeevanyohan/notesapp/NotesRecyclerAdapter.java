package com.dev.jeevanyohan.notesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesRecyclerAdapter extends RecyclerView.Adapter<NotesRecyclerAdapter.MyViewHolder> {

    Context context;
    List<Note> notesList;



    public NotesRecyclerAdapter(Context context, List<Note> notesList){
        this.context=context;
        this.notesList=notesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.list_item_note, parent, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        final Note currentMyNote = notesList.get(position);

        // Set the data to the views here



        holder.setNoteTitle(currentMyNote.getNote_title());
        holder.setNoteContent(currentMyNote.getNote_body());
        if (position==notesList.size()-1){
            holder.hideDivider();
        }

        
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    // This is your ViewHolder class that helps to populate data to the view
    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNoteTitle;
        private TextView tvNoteContent;
        private View divider;



        public MyViewHolder(View itemView) {
            super(itemView);

            tvNoteTitle=itemView.findViewById(R.id.tv_list_item_note_title);
            tvNoteContent=itemView.findViewById(R.id.tv_list_item_note_content);
            divider=itemView.findViewById(R.id.list_item_note_divider);

        }
        public void setNoteTitle(String title){

            tvNoteTitle.setText(title);

        }
        public void setNoteContent(String content){

            tvNoteContent.setText(content);

        }

        public void hideDivider(){
            divider.setVisibility(View.GONE);
        }




    }
    public void updateList(List<Note>newList){
        notesList=newList;
        notifyDataSetChanged();
    }


}
