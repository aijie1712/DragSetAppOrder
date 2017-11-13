package dragsetapp.liulangdemao.com.dragsetapporder.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import dragsetapp.liulangdemao.com.dragsetapporder.R;
import dragsetapp.liulangdemao.com.dragsetapporder.adapter.NormalAppAdapter;
import dragsetapp.liulangdemao.com.dragsetapporder.adapter.NormalAppLineAdapter;
import dragsetapp.liulangdemao.com.dragsetapporder.adapter.TopAdapter;
import dragsetapp.liulangdemao.com.dragsetapporder.bean.ItemBean;
import dragsetapp.liulangdemao.com.dragsetapporder.listener.OnItemTopRightClickListener;
import dragsetapp.liulangdemao.com.dragsetapporder.utils.DbUtils;
import dragsetapp.liulangdemao.com.dragsetapporder.widget.DragRecyclerView;
import dragsetapp.liulangdemao.com.dragsetapporder.widget.FullyGridLayoutManager;
import dragsetapp.liulangdemao.com.dragsetapporder.widget.FullyLinearLayoutManager;
import dragsetapp.liulangdemao.com.dragsetapporder.widget.HoldTouchHelper;

/**
 * 所有app页面，设置页面
 */
public class AllAppActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_done;

    private LinearLayout ll_top_apps;
    private RecyclerView topLineRecyclerView;
    private ImageView iv_more;
    private TextView tv_edit;

    private LinearLayout ll_top_edit_app;
    private DragRecyclerView topRecyclerView;

    private RecyclerView allRecyclerView;

    private List<ItemBean> topDatas;  // 顶部编辑app数据，包含空item
    private List<ItemBean> allAppList;  // 所有应用数据

    private NormalAppLineAdapter normalAppLineAdapter;
    private TopAdapter topAdapter;
    private NormalAppAdapter normalAppAdapter;

    private boolean isEditState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_app);

        initView();
        initData();
    }

    private void initView() {
        allAppList = new ArrayList<>();
        topDatas = new ArrayList<>();

        findViewById(R.id.iv_back).setOnClickListener(this);
        tv_done = (TextView) findViewById(R.id.tv_done);
        tv_done.setOnClickListener(this);

        ll_top_apps = (LinearLayout) findViewById(R.id.ll_top_apps);
        topLineRecyclerView = (RecyclerView) findViewById(R.id.topLineRecyclerView);
        iv_more = (ImageView) findViewById(R.id.iv_more);
        tv_edit = (TextView) findViewById(R.id.tv_edit);

        iv_more.setOnClickListener(this);
        tv_edit.setOnClickListener(this);

        ll_top_edit_app = (LinearLayout) findViewById(R.id.ll_top_edit_app);
        topRecyclerView = (DragRecyclerView) findViewById(R.id.topRecyclerView);

        allRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        topLineRecyclerView.setLayoutManager(new FullyLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        topRecyclerView.setLayoutManager(new FullyGridLayoutManager(this, 4));

        // 所有app的列表
        FullyGridLayoutManager gridLayoutManager = new FullyGridLayoutManager(this, 4);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int count = 4;
                if (!allAppList.isEmpty()) {
                    ItemBean itemBean = allAppList.get(position);
                    if (!itemBean.isTitle()) {
                        count = 1;
                    }
                }
                return count;
            }
        });
        allRecyclerView.setLayoutManager(gridLayoutManager);
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            ItemBean titleItemBean = new ItemBean("类型" + i, 0);
            titleItemBean.setTitle(true);
            allAppList.add(titleItemBean);
            for (int j = 0; j < 10; j++) {
                ItemBean itemBean = new ItemBean(i + "应用" + j, R.mipmap.ic_launcher);
                allAppList.add(itemBean);
            }
        }

        normalAppLineAdapter = new NormalAppLineAdapter(this, topDatas); // 顶部正常adapter
        topAdapter = new TopAdapter(this, topDatas);
        normalAppAdapter = new NormalAppAdapter(this, allAppList, topDatas);

        normalAppLineAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                toEditState(true);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        normalAppAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                String url = allAppList.get(position).getUrl();
                if (!TextUtils.isEmpty(url)) {

                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                toEditState(true);
                return true;
            }
        });

        normalAppAdapter.setOnItemAddClickListener(new OnItemTopRightClickListener() {
            @Override
            public void onItemTopRightClick(int position) {
                if (topDatas.size() > 12) {
                    Toast.makeText(AllAppActivity.this, "最多选中13个", Toast.LENGTH_SHORT).show();
                    return;
                }
                topDatas.add(allAppList.get(position));
                topAdapter.notifyDataSetChanged();
                normalAppAdapter.setSelectItemList(topDatas);
            }
        });

        topRecyclerView.setHasFixedSize(false);
        topRecyclerView
                .setDragAdapter(topAdapter)
                .bindEvent(onItemTouchEvent);

        topLineRecyclerView.setAdapter(normalAppLineAdapter);
        topRecyclerView.setAdapter(topAdapter);
        allRecyclerView.setAdapter(normalAppAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<ItemBean> saveBeanList = DbUtils.getMyApp();
        if (saveBeanList != null && saveBeanList.size() > 0) {
            topDatas.clear();
            topDatas.addAll(saveBeanList);
        }
        topAdapter.notifyDataSetChanged();
        normalAppLineAdapter.notifyDataSetChanged();
        normalAppAdapter.setSelectItemList(topDatas);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_done:
                toEditState(false);
                break;
            case R.id.iv_more:
            case R.id.tv_edit:
                toEditState(true);
                break;
        }
    }

    HoldTouchHelper.OnItemTouchEvent onItemTouchEvent = new HoldTouchHelper.OnItemTouchEvent() {
        @Override
        public void onLongPress(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int position) {
            if (((TopAdapter) recyclerView.getAdapter()).onItemDrag(position)) {
                ((DragRecyclerView) recyclerView).startDrag(position);
            }
        }

        @Override
        public void onItemClick(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int position) {
            String url = topDatas.get(position).getUrl();
            if (!TextUtils.isEmpty(url)) {

            }
        }

        @Override
        public void onItemTopRightClick(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int position) {
            topDatas.remove(position);
            topAdapter.notifyDataSetChanged();
            normalAppAdapter.setSelectItemList(topDatas);
        }
    };

    private void toEditState(boolean flag) {
        isEditState = flag;
        if (flag) {
            gone(ll_top_apps, topLineRecyclerView);
            visible(ll_top_edit_app, tv_done);
            normalAppAdapter.setEditState(true);
        } else {
            visible(ll_top_apps, topLineRecyclerView);
            gone(ll_top_edit_app, tv_done);
            normalAppAdapter.setEditState(false);
            DbUtils.saveMyApp(topDatas);
            normalAppLineAdapter.notifyDataSetChanged();
        }
        if (topDatas.size() > 6) {
            visible(iv_more);
        } else {
            gone(iv_more);
        }
    }

    protected void gone(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }

    protected void visible(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (isEditState) {
            toEditState(false);
        } else {
            super.onBackPressed();
        }
    }
}
