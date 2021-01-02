package com.dev.jeevanyohan.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fabAddNote;

    private RecyclerView notesRV;
    private NoteDatabase noteDatabase;
    private List<Note> notes;
    private NotesRecyclerAdapter notesAdapter;
    private int pos;
     TextView tvNoNotes;
     GetSet getSet;
     androidx.appcompat.widget.SearchView svNotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabAddNote=(FloatingActionButton)findViewById(R.id.fab_add_note);
        noteDatabase = NoteDatabase.getInstance(MainActivity.this);
        notesRV=(RecyclerView)findViewById(R.id.rv_notes);
        //notesRV.setLayoutManager(new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL,false));
        //notesRV.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
        notesRV.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        tvNoNotes=(TextView)findViewById(R.id.tv_no_notes);

        svNotes=(androidx.appcompat.widget.SearchView)findViewById(R.id.sv_notes);
        getSet=new GetSet();


        new RetrieveTask(this).execute();
        fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addNoteIntent=new Intent(MainActivity.this,AddNoteActivity.class);
                addNoteIntent.putExtra("note_type","new");
                startActivity(addNoteIntent);
                finish();
            }
        });


    }




    private static class RetrieveTask extends AsyncTask<Void,Void, List<Note>> {

        private WeakReference<MainActivity> activityReference;

        // only retain a weak reference to the activity
        RetrieveTask(MainActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<Note> doInBackground(Void... voids) {
            if (activityReference.get()!=null)
                return activityReference.get().noteDatabase.getNoteDao().getAll();
            else
                return null;
        }

        @Override
        protected void onPostExecute(List<Note> notes) {
            if (notes!=null && notes.size()>0 ){
                activityReference.get().notes = notes;


                activityReference.get().tvNoNotes.setVisibility(View.GONE);
                // create and set the adapter on RecyclerView instance to display list
                activityReference.get().notesAdapter = new NotesRecyclerAdapter(activityReference.get(),notes);
                activityReference.get().notesRV.setAdapter(activityReference.get().notesAdapter);
                activityReference.get().notesRV.addOnItemTouchListener(new RecyclerTouchListener(activityReference.get(), activityReference.get().notesRV, new ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Intent editNoteIntent=new Intent(activityReference.get(),AddNoteActivity.class);
                        editNoteIntent.putExtra("note_type","update");
                        editNoteIntent.putExtra("note", (Serializable) activityReference.get().notes.get(position));
                        activityReference.get().startActivity(editNoteIntent);
                        activityReference.get().finish();
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));

                activityReference.get().svNotes.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (newText.trim().equals("")){
                            new RetrieveTask(activityReference.get()).execute();
                            return true;
                        }else{
                            activityReference.get().filter(newText);
                            return true;

                        }

                    }
                });
            }
        }

    }

    public void filter(String searchText){
        List<Note> temp = new ArrayList();
        for(Note d: notes){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getNote_title().toUpperCase().contains(searchText.toUpperCase())){
                temp.add(d);
            }
        }
        //update recyclerview
        notes=temp;
        notesAdapter.updateList(temp);
    }
}