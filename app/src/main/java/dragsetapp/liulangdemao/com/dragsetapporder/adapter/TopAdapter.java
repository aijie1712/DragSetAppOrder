package dragsetapp.liulangdemao.com.dragsetapporder.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.Collections;
import java.util.List;

import dragsetapp.liulangdemao.com.dragsetapporder.R;
import dragsetapp.liulangdemao.com.dragsetapporder.bean.ItemBean;
import dragsetapp.liulangdemao.com.dragsetapporder.widget.OnItemChangeListener;


/**
 * Created by klx on 2017/11/2.
 */

public class TopAdapter extends CommonAdapter<ItemBean> implements OnItemChangeListener {
    public TopAdapter(Context context, List<ItemBean> datas) {
        super(context, R.layout.item_grid, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ItemBean itemBean, final int position) {
        RelativeLayout rl_root = holder.getView(R.id.rl_root);
        ImageView iv_icon = holder.getView(R.id.iv_icon);
        TextView tv_name = holder.getView(R.id.tv_name);
        ImageView iv_flag = holder.getView(R.id.iv_flag);

        rl_root.setBackgroundColor(Color.parseColor("#F9F9F9"));
        iv_icon.setVisibility(View.VISIBLE);
        tv_name.setVisibility(View.VISIBLE);
        iv_flag.setVisibility(View.VISIBLE);

        iv_icon.setImageResource(itemBean.getDefaultIcon());


        iv_flag.setImageResource(R.drawable.icon_app_delete);
        tv_name.setText(itemBean.getName());
    }

    @Override
    public boolean onItemDrag(int position) {
        return true;
    }

    @Override
    public void onItemMoved(int form, int target) {
        if (form < target) {
            // after
            for (int i = form; i < target; i++) {
                Collections.swap(mDatas, i, i + 1);
            }
        } else {
            // before
            for (int i = form; i > target; i--) {
                Collections.swap(mDatas, i, i - 1);
            }
        }
        notifyItemMoved(form, target);
    }

    @Override
    public boolean onItemDrop(int position) {
        return true;
    }
}