package shen.da.ye.imay.Album.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import shen.da.ye.imay.Entities.Image;
import shen.da.ye.imay.R;
import shen.da.ye.imay.utils.YeBaseAdapter;

/**
 * Created by Administrator on 2017/8/11 0011.
 */

public class ImagesAdapter extends YeBaseAdapter {

    public ImagesAdapter(ArrayList datas, Context mContext) {
        super(datas, mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_album_2, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.getLayoutParams().width = size;
        viewHolder.imageView.getLayoutParams().height = size;
        viewHolder.updateView(position);
        return convertView;
    }

    private class ViewHolder {
        public ImageView imageView;
        public TextView textView;

        public ViewHolder(View view) {
            imageView = view.findViewById(R.id.image_view_album_image);
            textView = view.findViewById(R.id.text_view_album_name);
        }

        public void updateView(int position) {
            Image image = (Image) datas.get(position);
            textView.setVisibility(View.GONE);
            Glide.with(mContext)
                    .load(image.path)
                    .placeholder(R.drawable.image_placeholder)
                    .into(imageView);
        }
    }
}
