package com.example.andre.aced;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class more_note_info extends AppCompatActivity {

    private TextView invite;
    private TextView files;
    private TextView noteName;
    private ImageView mimageView;
    private ImageView back;
    private ImageView trash;
    private int position;
    private TextView noNotesView;

    private static final int IMAGE_PICK_CODE= 1000;
    private static final int PERMISSION_CODE= 1001;

    private int counter = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_info_notes);

        //Find Corresponding XML
        invite = (TextView)findViewById(R.id.more_info_invite);
        files = (TextView)findViewById(R.id.files);
        mimageView = (ImageView)findViewById(R.id.imagedisplayer);
        noteName = (TextView)findViewById(R.id.more_info_note);
        back = (ImageView)findViewById(R.id.more_info_back);
        trash = (ImageView)findViewById(R.id.more_info_trash);
        noNotesView = findViewById(R.id.empty_notes_view);

        //Create Note Object
        final Notes notes_object = new Notes();
        notes_object.notesList.addAll(notes_object.db.getAllNotes());

        Intent i = getIntent();
        String note = i.getStringExtra("Note");
        position = i.getIntExtra("position", 0);
        noteName.setText(note);

        files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {
                        //Permission is not granted, must request permission
                        String[] permissions= {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                }
                pickFromGallery();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(more_note_info.this, Notes.class);
                more_note_info.this.startActivity(intent);
            }
        });

        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notes_object.deleteNote(position);
                Intent intent = new Intent(more_note_info.this, Notes.class);
                more_note_info.this.startActivity(intent);
                notes_object.mAdapter.notifyDataSetChanged();
            }
        });


    }


    private void pickFromGallery() {
        Intent intent= new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickFromGallery();
                }
                else {
                    Toast.makeText(this, "Aced must be able to access photo album", Toast.LENGTH_SHORT);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE ) {
           mimageView.setImageURI(data.getData());
        }

    }
}
