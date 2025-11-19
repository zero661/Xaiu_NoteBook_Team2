package com.xaiu.team2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    TextView btnAddNote;
    private List<NoteBook> list;
    private ActivityResultLauncher launcher;

    private DBUtils dbUtils;
    private NoteAdapter adapter;
    private RecyclerView rv_list;
    private TextView tTips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        btnAddNote = findViewById(R.id.team2_addNote);
        tTips = findViewById(R.id.team2_tips);
        btnAddNote.setOnClickListener(v -> {
            Intent intent = new Intent(this, NoteActivity.class);
            launcher.launch(intent);
        });
        init();
        initLauncher();
    }


    private void initLauncher() {
        Log.d(TAG, ">>>>>>>>>>>>>>>diaoyong initLauncher");
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        int resultCode = result.getResultCode();
                        Log.d(TAG, "传回数据");
                        if (resultCode == 2) {
                            showQueryData();
                            Log.d(TAG, "----------------------------showQueryData");
                        }
                    }
                });
    }


    private void init() {
        dbUtils = new DBUtils(this);
        rv_list = findViewById(R.id.team2_list);
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteAdapter(this, new NoteAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                NoteBook bean = list.get(position);
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                intent.putExtra("id", bean.getId());
                intent.putExtra("title", bean.getTitle());
                intent.putExtra("content", bean.getContent());
                intent.putExtra("time", bean.getTime());
                launcher.launch(intent);
            }

            @Override
            public void onItemLongClick(View v, int position) {
                Team2Dialog.showConfirmDialog(
                        MainActivity.this,
                        "提示",
                        "确定删除此记录？",
                        confirmView -> {
                            NoteBook noteBean = list.get(position);
                            if (dbUtils.deleteNote(noteBean.getId())) {
                                list.remove(position);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(MainActivity.this, "删除成功",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }, null
                );
            }
        });
        showQueryData();
    }

    private void showQueryData() {
        Log.d(TAG, "----------------showQueryData__++++++");
        if (list != null) {
            list.clear();
        }
        //从数据库中查询数据(记录的事件)
        list = dbUtils.queryNote();
        Log.d(TAG, "----------记事本记录--------------");
        Log.d(TAG, list.toString());
        if (list == null || list.isEmpty()) {
            System.out.println("list null");
            return;
        }
        for (NoteBook notebook : list) {
            System.out.println(notebook);
        }
        Log.d(TAG, "----------------------------------");
        if (!list.isEmpty()) {
            tTips.setVisibility(View.GONE);
            rv_list.setVisibility(View.VISIBLE);
        } else {
            tTips.setVisibility(View.VISIBLE);
            rv_list.setVisibility(View.GONE);
        }
        adapter.setData(list);
        rv_list.setAdapter(adapter);
    }

}