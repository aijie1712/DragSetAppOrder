package dragsetapp.liulangdemao.com.dragsetapporder.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import dragsetapp.liulangdemao.com.dragsetapporder.R;
import dragsetapp.liulangdemao.com.dragsetapporder.bean.ItemBean;

/**
 * Created by klx on 2017/11/13.
 */

public class MyAppAdapter extends CommonAdapter<ItemBean> {
    public MyAppAdapter(Context context, List<ItemBean> datas) {
        super(context, R.layout.item_grid, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ItemBean itemBean, int position) {
        ImageView iv_flag = holder.getView(R.id.iv_flag);
        iv_flag.setVisibility(View.GONE);
        holder.setText(R.id.tv_name, itemBean.getName());
        holder.setImageResource(R.id.iv_icon, itemBean.getDefaultIcon());
    }
}
