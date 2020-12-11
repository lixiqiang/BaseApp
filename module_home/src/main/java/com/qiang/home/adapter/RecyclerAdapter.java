package com.qiang.home.adapter;


import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.qiang.home.R;

import java.util.ArrayList;

/**
 * @author lixiqiang
 * @date：2019/10/21 0021
 */
public class RecyclerAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public RecyclerAdapter() {
        super(R.layout.item_recycler_view, new ArrayList<String>());
    }

    @Override
    protected void convert(@NonNull final BaseViewHolder helper, String item) {

        if (item != null) {
            helper.setText(R.id.tv_text, item);
        }
    }

}
