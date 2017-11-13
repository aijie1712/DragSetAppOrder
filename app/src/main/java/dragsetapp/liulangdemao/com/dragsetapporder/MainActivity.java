package dragsetapp.liulangdemao.com.dragsetapporder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import dragsetapp.liulangdemao.com.dragsetapporder.activity.AllAppActivity;
import dragsetapp.liulangdemao.com.dragsetapporder.adapter.MyAppAdapter;
import dragsetapp.liulangdemao.com.dragsetapporder.bean.ItemBean;
import dragsetapp.liulangdemao.com.dragsetapporder.utils.DbUtils;
import dragsetapp.liulangdemao.com.dragsetapporder.widget.DividerGridItemDecoration;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<ItemBean> itemBeanList;
    private MyAppAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemBeanList = new ArrayList<>();

        adapter = new MyAppAdapter(this, itemBeanList);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.addItemDecoration(new DividerGridItemDecoration());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                ItemBean itemBean = itemBeanList.get(position);
                if (itemBean.getItemType() == ItemBean.ItemType.type_more) {
                    startActivity(new Intent(MainActivity.this, AllAppActivity.class));
                } else {
                    Toast.makeText(MainActivity.this, itemBean.getName(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<ItemBean> saveBeanList = DbUtils.getMyApp();
        if (saveBeanList != null && saveBeanList.size() > 0) {
            itemBeanList.clear();
            itemBeanList.addAll(saveBeanList);
        }
        ItemBean moreItemBean = new ItemBean("更多", R.mipmap.ic_launcher);
        moreItemBean.setItemType(ItemBean.ItemType.type_more);
        itemBeanList.add(moreItemBean);
        adapter.notifyDataSetChanged();
    }
}
