package com.dev.jeevanyohan.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.ref.WeakReference;

public class AddNoteActivity extends AppCompatActivity {

    TextInputLayout etlayTitle,etlayContent;
    TextInputEditText etTitle,etContent;
    Button btnSave;
    Note note,updateNote;
    NoteDatabase noteDatabase;
    Intent noteIntent;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        etlayTitle=(TextInputLayout)findViewById(R.id.et_title_layout);
        etlayContent=(TextInputLayout)findViewById(R.id.et_content_layout);
        etTitle=(TextInputEditText) findViewById(R.id.et_title);
        etContent=(TextInputEditText) findViewById(R.id.et_content);

        noteDatabase=NoteDatabase.getInstance(AddNoteActivity.this);
        btnSave=(Button)findViewById(R.id.btn_save_note);

         noteIntent=getIntent();


         toolbar=(Toolbar)findViewById(R.id.tb_add_note);
         setSupportActionBar(toolbar);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

         toolbar.setNavigationOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent backButtonIntent=new Intent(AddNoteActivity.this,MainActivity.class);
                 startActivity(backButtonIntent);
                 finish();
             }
         });

        if (noteIntent.getStringExtra("note_type").equals("update")){
            updateNote=(Note)noteIntent.getSerializableExtra("note");
            etTitle.setText(updateNote.getNote_title());
            etContent.setText(updateNote.getNote_body());
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateNote.setNote_body(etContent.getText().toString());
                    updateNote.setNote_title(etTitle.getText().toString());
                    noteDatabase.getNoteDao().update(updateNote);
                }
            });
        }else if (noteIntent.getStringExtra("note_type").equals("new")){
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // fetch data and create note object
                    note = new Note(etContent.getText().toString(),
                            etTitle.getText().toString());

                    // create worker thread to insert data into database
                    new InsertTask(AddNoteActivity.this,note).execute();
                }
            });
        }





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.edit_notes_menu,menu);
        if (noteIntent.getStringExtra("note_type").equals("update")){
            menu.getItem(0).setVisible(true);
        }else{
            menu.getItem(0).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_del_note:
                noteDatabase.getNoteDao().delete((Note)noteIntent.getSerializableExtra("note"));
                Intent delIntent=new Intent(AddNoteActivity.this,MainActivity.class);
                startActivity(delIntent);
                finish();
                break;
        }
        return true;
    }

    /* private void setResult(Note note, int flag){

        setResult(flag,new Intent().putExtra("note",note.toString()));
        finish();
    }

    */


    private static class InsertTask extends AsyncTask<Void,Void,Boolean> {

        private WeakReference<AddNoteActivity> activityReference;
        private Note note;


        // only retain a weak reference to the activity
        InsertTask(AddNoteActivity context, Note note) {
            activityReference = new WeakReference<>(context);
            this.note = note;
        }

        // doInBackground methods runs on a worker thread
        @Override
        protected Boolean doInBackground(Void... objs) {
            activityReference.get().noteDatabase.getNoteDao().insert(note);
            return true;
        }

        // onPostExecute runs on main thread
        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool){
                /*activityReference.get().setResult(note,1);
                activityReference.get().finish();*/

                Toast.makeText(activityReference.get(),"Saved",Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onBackPressed() {
        Intent backToMainIntent=new Intent(AddNoteActivity.this,MainActivity.class);
        startActivity(backToMainIntent);
        finish();
    }
}