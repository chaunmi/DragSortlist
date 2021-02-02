package com.jasonhzx.dragsortlist.sample;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jasonhzx.dragsortlist.component.DragSortItemLayout;
import com.jasonhzx.dragsortlist.component.DragSortListAdapter;
import com.jasonhzx.dragsortlist.component.DragSortListLayout;
import com.jasonhzx.dragsortlist.component.ItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by JasonHzx on 2018/4/17
 * 主页面
 */

public class MainActivity extends AppCompatActivity implements DragSortItemLayout.OnItemSortListener{

    private MainListAdapter mAdapter;
    private DragListAdapter dragListAdapter;
    private TextView tvEdit;
    private boolean isEdit;

    private ItemTouchHelperCallback dragSortCallback;
    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTitle();
    //    initRecyclerView();
        initMyRecyclerView();
    }

    private void initTitle() {
        tvEdit = findViewById(R.id.tv_edit);
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEdit) {
                    tvEdit.setText("编辑");
                    tvEdit.setTextColor(getMyColor(R.color.white));
                } else {
                    tvEdit.setText("完成");
                    tvEdit.setTextColor(getMyColor(R.color.top_bar_text_pressed));
                }
                isEdit = !isEdit;
                mAdapter.setEdit(isEdit);
                if (mAdapter.isHasSorted()) {
                    Toast.makeText(MainActivity.this, "列表有过排序操作", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public int getMyColor(int colorId) {
        return ContextCompat.getColor(getApplication(), colorId);
    }


    private void initRecyclerView() {
        DragSortListLayout mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        String[] names = getResources().getStringArray(R.array.query_suggestions);
        List<String> mList = new ArrayList<>();
        Collections.addAll(mList, names);
        mAdapter = new MainListAdapter(this, mList);
        mAdapter.setOnEditItemListener(new MainListAdapter.OnEditItemListener() {
            @Override
            public void onItemRemove(String item) {
                Toast.makeText(MainActivity.this, "删除了：" + item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemClick(String item) {
                Toast.makeText(MainActivity.this, "普通状态下点击了：" + item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemClickEdit(String item) {
                Toast.makeText(MainActivity.this, "编辑状态下点击了：" + item, Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    private void initMyRecyclerView() {
        RecyclerView mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        String[] names = getResources().getStringArray(R.array.query_suggestions);
        List<String> mList = new ArrayList<>();
        Collections.addAll(mList, names);
        dragListAdapter = new DragListAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(dragListAdapter);
        dragListAdapter.updateList(mList);
        dragListAdapter.setOnItemSortListener(this);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        dragSortCallback = new ItemTouchHelperCallback(this);
        mItemTouchHelper = new ItemTouchHelper(dragSortCallback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    @Override
    public void onStartDrags(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        List list = dragListAdapter.getList();
        Collections.swap(list, fromPosition, toPosition);
        dragListAdapter.notifyItemMoved(fromPosition, toPosition);
    }
}
