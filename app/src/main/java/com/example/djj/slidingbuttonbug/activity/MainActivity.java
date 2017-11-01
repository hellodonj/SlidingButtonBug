package com.example.djj.slidingbuttonbug.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.djj.slidingbuttonbug.R;
import com.example.djj.slidingbuttonbug.adapter.RecycleAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private RecycleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<String> list;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private static final int REFRESH_STATUS = 0;
    private int j = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDate();
        initView();

    }

    private void initDate() {
        list = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            list.add("西红柿炒鸡蛋");
        }
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new RecycleAdapter(MainActivity.this, list);
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);

        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
        onRefresh();
        mAdapter.setOnSlidingViewClickListener(new RecycleAdapter.IonSlidingViewClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, "单击" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteBtnClick(View view, int position) {
                Toast.makeText(MainActivity.this, "删除" + position, Toast.LENGTH_SHORT).show();
                mAdapter.removeItem(position);
            }
        });
    }

    @Override
    public void onRefresh() {
        refreshHandler.sendEmptyMessageDelayed(REFRESH_STATUS, 2000);
    }

    private Handler refreshHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_STATUS:
                    //下拉刷新执行的操作，刷新数据
                    mSwipeRefreshLayout.setRefreshing(false);
                    j++;
                    list.add("测试" + j);
                    mAdapter.updateData(list);
                    break;
            }
        }
    };
}