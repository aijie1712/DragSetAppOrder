package dragsetapp.liulangdemao.com.dragsetapporder.adapter.delegate;

import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import dragsetapp.liulangdemao.com.dragsetapporder.R;
import dragsetapp.liulangdemao.com.dragsetapporder.bean.ItemBean;


/**
 * 作者：Android_AJ on 2017/4/18.
 * 邮箱：ai15116811712@163.com
 * 版本：v1.0
 */
public class ItemTitleDelegate implements ItemViewDelegate<ItemBean> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_title;
    }

    @Override
    public boolean isForViewType(ItemBean app, int i) {
        return app.isTitle();
    }

    @Override
    public void convert(ViewHolder viewHolder, ItemBean app, final int position) {
        viewHolder.setText(R.id.tv_item_title, app.getName());
    }
}
