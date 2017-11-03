package shen.da.ye.imay.Album.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import shen.da.ye.imay.Entities.LocalAlbumEntity;
import shen.da.ye.imay.R;
import shen.da.ye.imay.utils.YeBaseAdapter;

/**
 * Created by cy on 2017/9/4 0004 16:42
 * 这是lv的Adapter
 */

public class AlbumAdapter2 extends YeBaseAdapter {

    public AlbumAdapter2(ArrayList datas, Context mContext) {
        super(datas, mContext);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AlbumAdapter2ViewHolder holder;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_album2, null);
            holder = new AlbumAdapter2ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (AlbumAdapter2ViewHolder) view.getTag();
        }
        holder.updateView(i);
        return view;
    }

    class AlbumAdapter2ViewHolder {

        @BindView(R.id.item_ablum2_album_name_tv)
        TextView ablumNameTv;

        @BindView(R.id.item_ablum2_album_num_tv)
        TextView albumNumTv;

        @BindView(R.id.item_album2_iv)
        ImageView albumIv;

        public AlbumAdapter2ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }

        public void updateView(int position) {
            LocalAlbumEntity album = (LocalAlbumEntity) datas.get(position);
            ablumNameTv.setText(album.albumName);
            albumNumTv.setText(album.imageNums + "");
            Glide.with(mContext)
                    .load(album.displayUrl)
                    .placeholder(R.drawable.image_placeholder)
                    .centerCrop()
                    .into(albumIv);
        }
    }
}
