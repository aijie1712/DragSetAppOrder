package dragsetapp.liulangdemao.com.dragsetapporder.adapter;

import android.content.Context;

import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

import dragsetapp.liulangdemao.com.dragsetapporder.adapter.delegate.ItemContentDelegate;
import dragsetapp.liulangdemao.com.dragsetapporder.adapter.delegate.ItemTitleDelegate;
import dragsetapp.liulangdemao.com.dragsetapporder.bean.ItemBean;
import dragsetapp.liulangdemao.com.dragsetapporder.listener.OnItemTopRightClickListener;

/**
 * Created by klx on 2017/11/2.
 * myAdapter
 */

public class NormalAppAdapter extends MultiItemTypeAdapter<ItemBean> {

    private List<ItemBean> selectItemList;
    ItemContentDelegate contentDelegate;

    public NormalAppAdapter(Context context, List<ItemBean> app, List<ItemBean> selectItemList) {
        super(context, app);
        this.selectItemList = selectItemList;
        ItemTitleDelegate titleDelegate = new ItemTitleDelegate();
        contentDelegate = new ItemContentDelegate(selectItemList);
        contentDelegate.setOnItemAddClickListener(new OnItemTopRightClickListener() {
            @Override
            public void onItemTopRightClick(int position) {
                if (onItemTopRightClickListener != null) {
                    onItemTopRightClickListener.onItemTopRightClick(position);
                }
            }
        });
        addItemViewDelegate(titleDelegate);
        addItemViewDelegate(contentDelegate);
    }

    public void setSelectItemList(List<ItemBean> selectItemList) {
        this.selectItemList = selectItemList;
        contentDelegate.setSelectItemList(this.selectItemList);
        notifyDataSetChanged();
    }

    public void setEditState(boolean editState) {
        contentDelegate.setEditState(editState);
        notifyDataSetChanged();
    }

    private OnItemTopRightClickListener onItemTopRightClickListener;

    public void setOnItemAddClickListener(OnItemTopRightClickListener onItemTopRightClickListener) {
        this.onItemTopRightClickListener = onItemTopRightClickListener;
    }
}
