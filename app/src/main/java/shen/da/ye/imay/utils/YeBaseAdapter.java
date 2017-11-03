package shen.da.ye.imay.utils;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/16 0016.
 */

public abstract class YeBaseAdapter<T> extends BaseAdapter {
    protected ArrayList<T> datas;
    protected int size;
    protected Context mContext;

    public YeBaseAdapter(ArrayList<T> datas, Context mContext) {
        this.datas = datas;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public T getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setLayoutParams(int size) {
        this.size = size;
    }

    public void release() {
        datas = null;
        mContext = null;
    }
}
