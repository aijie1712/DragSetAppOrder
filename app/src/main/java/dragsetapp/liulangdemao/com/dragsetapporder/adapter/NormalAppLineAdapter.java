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
 * Created by klx on 2017/11/2.
 */

public class NormalAppLineAdapter extends CommonAdapter<ItemBean> {
    public NormalAppLineAdapter(Context context, List<ItemBean> datas) {
        super(context, R.layout.item_line_grid, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ItemBean itemBean, final int position) {
        ImageView iv_icon = holder.getView(R.id.iv_icon);
        if (position >= 6) {
            iv_icon.setVisibility(View.GONE);
        } else {
            iv_icon.setVisibility(View.VISIBLE);
        }
        holder.setImageResource(R.id.iv_icon, itemBean.getDefaultIcon());
    }
}