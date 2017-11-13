package dragsetapp.liulangdemao.com.dragsetapporder.adapter.delegate;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import dragsetapp.liulangdemao.com.dragsetapporder.R;
import dragsetapp.liulangdemao.com.dragsetapporder.bean.ItemBean;
import dragsetapp.liulangdemao.com.dragsetapporder.listener.OnItemTopRightClickListener;

/**
 * 作者：Android_AJ on 2017/4/18.
 * 邮箱：ai15116811712@163.com
 * 版本：v1.0
 */
public class ItemContentDelegate implements ItemViewDelegate<ItemBean> {
    private List<ItemBean> selectItemList;
    private boolean isEditState = false;  // 是否是编辑状态

    public ItemContentDelegate(List<ItemBean> selectItemList) {
        this.selectItemList = selectItemList;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_grid;
    }

    @Override
    public boolean isForViewType(ItemBean app, int i) {
        return !app.isTitle();
    }

    @Override
    public void convert(ViewHolder holder, final ItemBean itemBean, final int position) {
        RelativeLayout rl_root = holder.getView(R.id.rl_root);
        holder.setText(R.id.tv_name, itemBean.getName());
        ImageView iv_icon = holder.getView(R.id.iv_icon);
        ImageView iv_flag = holder.getView(R.id.iv_flag);

        // 设置图标
        iv_icon.setImageResource(itemBean.getDefaultIcon());

        iv_flag.setImageResource(R.drawable.icon_app_add);
        itemBean.setState(ItemBean.ItemState.state_normal);
        if (isEditState) {
            rl_root.setBackgroundColor(Color.parseColor("#F9F9F9"));
            iv_flag.setVisibility(View.VISIBLE);
            for (ItemBean selectItem : selectItemList) {
                if (TextUtils.equals(selectItem.getName(), itemBean.getName())) {
                    iv_flag.setImageResource(R.drawable.icon_app_added);
                    itemBean.setState(ItemBean.ItemState.state_selected);
                }
            }
        } else {
            rl_root.setBackgroundColor(Color.TRANSPARENT);
            iv_flag.setVisibility(View.GONE);
        }

        iv_flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemTopRightClickListener != null && itemBean.getState() == ItemBean.ItemState.state_normal) {
                    onItemTopRightClickListener.onItemTopRightClick(position);
                }
            }
        });
    }

    public void setSelectItemList(List<ItemBean> selectItemList) {
        this.selectItemList = selectItemList;
    }

    public void setEditState(boolean editState) {
        isEditState = editState;
    }

    private OnItemTopRightClickListener onItemTopRightClickListener;

    public void setOnItemAddClickListener(OnItemTopRightClickListener onItemTopRightClickListener) {
        this.onItemTopRightClickListener = onItemTopRightClickListener;
    }
}
