package com.xaiu.team2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class NoteActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout btnBack;
    private final String TAG = "NoteActivity";
    private DBUtils dbUtils;
    private int id;
    EditText edTitle;
    EditText edContent;
    TextView btnSave, edTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_app);
        edTitle = findViewById(R.id.team2_edTitle);
        btnBack = findViewById(R.id.team2_back);
        btnSave = findViewById(R.id.team2_Save);
        edTime = findViewById(R.id.team2_edTime);
        edContent = findViewById(R.id.tv_content);
        btnBack.setOnClickListener(v -> {
            this.finish();
        });
        btnSave.setOnClickListener(v -> {
            Log.d(TAG, "点击");
            String title = edTitle.getText().toString().trim();
            String content = edContent.getText().toString().trim();
            if (id != 0) {//修改界面
                if (!content.isEmpty()) {
                    if (dbUtils.updateNote(id, title, content, DBUtils.getTime())) {
                        showToast("修改成功");
                        setResult(2);
                        finish();
                    } else {
                        showToast("修改失败");
                    }
                } else {
                    showToast("修改内容不能为空!");
                }
            } else { //添加记录界面
                if (!content.isEmpty()) {
                    if (dbUtils.saveNote(title, content, DBUtils.getTime())) {
                        showToast("保存成功");
                        setResult(2);
                        finish();
                    } else {
                        showToast("保存失败");
                    }
                } else {
                    showToast("添加的内容不能为空!");
                }
            }
        });
        initData();
    }

    private void initData() {
        dbUtils = new DBUtils(this);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        edContent.setText(intent.getStringExtra("content"));
        edTitle.setText(intent.getStringExtra("title"));
        String eTime = intent.getStringExtra("time");
        if (eTime == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
                String time1 = now.format(formatter1);
                edTime.setText(time1);
            }
        }else{
            edTime.setText(eTime);
        }
    }

    @Override
    public void onClick(View v) {
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}