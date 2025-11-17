package com.xaiu.team2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView btnAddNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        btnAddNote = findViewById(R.id.team2_addNote);
        btnAddNote.setOnClickListener(v -> {
            Team2Dialog.showConfirmDialog(
                    this,
                    "提示",
                    "请输入标题？",
                    confirmView -> {
                        Intent intent = new Intent(this, NoteActivity.class);
                        startActivity(intent);
                    }, null
            );


        });
    }

}