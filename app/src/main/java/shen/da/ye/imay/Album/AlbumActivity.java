package shen.da.ye.imay.Album;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import shen.da.ye.imay.Album.Adapter.ImagesAdapter;
import shen.da.ye.imay.Entities.Image;
import shen.da.ye.imay.R;
import shen.da.ye.imay.utils.Logger;
import shen.da.ye.imay.utils.ToastUtils;

/**
 * Created by Administrator on 2017/8/16 0016.
 * 相册activity
 */

public class AlbumActivity extends Activity {
    private static final String TAG = "cy=====AlbumActivity";
    private final String[] projection = new String[]{MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATA};
    private ImagesAdapter adapter;
    private Context mContext;

    @BindView(R.id.gv)
    GridView mGridView;

    @BindView(R.id.iv_gzrw_back)
    ImageView mBackIv;

    @BindView(R.id.title_tv)
    TextView titleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);
        ButterKnife.bind(this);
        mContext = this;
        String albumName = getIntent().getStringExtra("albumName");
        titleTv.setText(albumName);
        Observable.just(albumName)
                .filter(s -> s != null && !s.equals(""))
                .flatMap(s -> Observable.just(setupImages(s)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(images -> setupGridView(images), e -> Logger.i(TAG, "读取相册出现异常了"));
        RxView.clicks(mBackIv).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> finish());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adapter != null)
            adapter.release();
    }

    private void setupGridView(ArrayList<Image> images) {
        if (images.size() != 0) {
            Logger.i(TAG, "当前相册有" + images.size() + "张图片");
            adapter = new ImagesAdapter(images, mContext);
            mGridView.setAdapter(adapter);
            WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics metrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(metrics);
            int size = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? metrics.widthPixels / 2 : metrics.widthPixels / 4;
            adapter.setLayoutParams(size);
        } else {
            ToastUtils.showToast("当前相册没有图片");
        }
    }

    private ArrayList<Image> setupImages(String album) {
        Logger.i(TAG, "进入了查询图片");
        ArrayList<Image> images = new ArrayList<>();
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " =?",
                new String[]{album}, MediaStore.Images.Media.DATE_ADDED);
        if (cursor != null) {
            Logger.i(TAG, "cursor不为null");
            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndex(projection[0]));
                String name = cursor.getString(cursor.getColumnIndex(projection[1]));
                String path = cursor.getString(cursor.getColumnIndex(projection[2]));
                images.add(new Image(id, name, path));
            }
            cursor.close();
        } else {
            Logger.i(TAG, "cursor为null");
        }
        return images;
    }


    public static void actionStart(AlbumsActivity activity, String albumName) {
        Intent intent = new Intent(activity, AlbumActivity.class);
        intent.putExtra("albumName", albumName);
        activity.startActivity(intent);
    }
}
