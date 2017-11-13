package dragsetapp.liulangdemao.com.dragsetapporder.bean;


import org.litepal.crud.DataSupport;

/**
 * Created by klx on 2017/11/2.
 * 应用实体bean
 */

public class ItemBean extends DataSupport {
    private String itemId;  // 唯一标识
    private int orderPosition;
    private String name;
    private String icon;
    private String url;
    private int state;
    private int itemType;  // 是否是标题  0--否   1--是
    private boolean isTitle;
    private int defaultIcon; // 默认icon

    public ItemBean(String name, String icon, String url, int itemType, boolean isTitle) {
        this.name = name;
        this.icon = icon;
        this.url = url;
        this.itemType = itemType;
        this.isTitle = isTitle;
    }

    public ItemBean(String name, int defaultIcon) {
        this.name = name;
        this.defaultIcon = defaultIcon;
    }

    public ItemBean(String itemId, int orderPosition, String name, String icon, String url, int state, int itemType,
                    boolean isTitle, int defaultIcon) {
        this.itemId = itemId;
        this.orderPosition = orderPosition;
        this.name = name;
        this.icon = icon;
        this.url = url;
        this.state = state;
        this.itemType = itemType;
        this.isTitle = isTitle;
        this.defaultIcon = defaultIcon;
    }

    public int getDefaultIcon() {
        return defaultIcon;
    }

    public void setDefaultIcon(int defaultIcon) {
        this.defaultIcon = defaultIcon;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }

    public int getItemType() {
        return itemType;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public int getOrderPosition() {
        return orderPosition;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setOrderPosition(int orderPosition) {
        this.orderPosition = orderPosition;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "ItemBean{" +
                "itemId='" + itemId + '\'' +
                ", orderPosition=" + orderPosition +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", url='" + url + '\'' +
                ", state=" + state +
                ", itemType=" + itemType +
                ", isTitle=" + isTitle +
                '}';
    }

    public interface ItemState {
        int state_normal = 1;  // 未选中
        int state_selected = 2;  // 选中
    }

    public static ItemBean copyListBean(ItemBean itemBean) {
        return new ItemBean(itemBean.getItemId(), itemBean.getOrderPosition(),
                itemBean.getName(), itemBean.getIcon(), itemBean.getUrl(),
                itemBean.getState(), itemBean.getItemType(), itemBean.isTitle(), itemBean.getDefaultIcon());
    }
}
