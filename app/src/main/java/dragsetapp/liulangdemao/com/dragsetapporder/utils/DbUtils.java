package dragsetapp.liulangdemao.com.dragsetapporder.utils;

import android.util.Log;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.SaveCallback;
import org.litepal.crud.callback.UpdateOrDeleteCallback;

import java.util.ArrayList;
import java.util.List;

import dragsetapp.liulangdemao.com.dragsetapporder.bean.ItemBean;

/**
 * Created by klx on 2017/11/13.
 * 数据库操作相关
 */

public class DbUtils {
    private static final String TAG = "DbUtils";

    public static List<ItemBean> getMyApp() {
        return DataSupport
                .order("orderPosition asc").find(ItemBean.class);
    }

    /**
     * 本地保存应用数据
     */
    public static void saveMyApp(final List<ItemBean> itemBeanList) {
        final List<ItemBean> saveList = new ArrayList<>();
        for (int i = 0; i < itemBeanList.size(); i++) {
            ItemBean itemBean = itemBeanList.get(i);
            itemBean.setOrderPosition(i);
            saveList.add(ItemBean.copyListBean(itemBean));
        }
        DataSupport.deleteAllAsync(ItemBean.class).listen(new UpdateOrDeleteCallback() {
            @Override
            public void onFinish(int rowsAffected) {
                DataSupport.saveAllAsync(saveList).listen(new SaveCallback() {
                    @Override
                    public void onFinish(boolean success) {
                        Log.i(TAG, "保存结果：" + success);
                    }
                });
            }
        });
    }
}
