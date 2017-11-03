package shen.da.ye.imay.Album;

import android.Manifest;
import android.app.Activity;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxAdapterView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;
import shen.da.ye.imay.Album.Adapter.AlbumAdapter2;
import shen.da.ye.imay.Entities.LocalAlbumEntity;
import shen.da.ye.imay.R;
import shen.da.ye.imay.utils.Logger;
import shen.da.ye.imay.utils.ToastUtils;


/**
 * @author cy
 *  相册集activity
 */
public class AlbumsActivity extends Activity implements EasyPermissions.PermissionCallbacks {

    private static final String TAG = "cy===albumActivity";
    private ArrayList<LocalAlbumEntity> albums;
    private Activity mActivity;
    private AlbumAdapter2 albumAdapter;

    @BindView(R.id.iv_gzrw_back)
    ImageView backIv;

//    @BindView(R.id.gridview)
//    GridView mGridView;

    @BindView(R.id.album_iv)
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        ButterKnife.bind(this);
        mActivity = AlbumsActivity.this;
        setupClick();
        setupPermission();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (albumAdapter != null)
            albumAdapter.release();
    }

    private void setupPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (EasyPermissions.hasPermissions(AlbumsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                setupAlbum();
            } else {
                EasyPermissions.requestPermissions(mActivity, "需要访问sd卡权限", 800, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        } else {
            setupAlbum();
        }
    }

    private void setupAlbum() {
        getAlbum();
        if (albums != null && albums.size() != 0) {
            albumAdapter = new AlbumAdapter2(albums, this);
//            mGridView.setAdapter(albumAdapter);
            mListView.setAdapter(albumAdapter);
//            WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
//            DisplayMetrics metrics = new DisplayMetrics();
//            windowManager.getDefaultDisplay().getMetrics(metrics);
//            int size = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? metrics.widthPixels / 2 : metrics.widthPixels / 4;
//            albumAdapter.setLayoutParams(size);
        }
    }

    private final String[] projection = new String[]{MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATA};

    private void getAlbum() {
        Cursor cursor = getApplicationContext().getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                        null, null, MediaStore.Images.Media.DATE_ADDED);

        if (cursor == null) {
            Toast.makeText(this, "读取相册失败", Toast.LENGTH_SHORT).show();
        } else {
            ArrayList<LocalAlbumEntity> temp = new ArrayList<>(cursor.getCount());
            HashSet<String> albumSet = new HashSet<>();
            File file;
            if (cursor.moveToLast()) {
                do {
                    if (Thread.interrupted()) {
                        return;
                    }
                    String album = cursor.getString(cursor.getColumnIndex(projection[0]));
                    String image = cursor.getString(cursor.getColumnIndex(projection[1]));
                    file = new File(image);
                    if (file.exists() && !albumSet.contains(album)) {
                        String[] projection = new String[]{MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATA};
                        Cursor cursor2 = getContentResolver().query(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                projection,
                                MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " =?",
                                new String[]{album}, MediaStore.Images.Media.DATE_ADDED);
                        Logger.i(TAG, album + "相册总共有" + cursor2.getCount() + "张图片");
                        temp.add(new LocalAlbumEntity(album, image, cursor2 == null ? 0 : cursor2.getCount()));
                        albumSet.add(album);
                    }

                } while (cursor.moveToPrevious());
            }
            cursor.close();

            if (albums == null) {
                albums = new ArrayList<>();
            }
            albums.clear();
            albums.addAll(temp);
            Log.i("MainActivity", albums.size() + "");
        }
    }

    private void setupClick() {
//        RxAdapterView.itemClicks(mGridView).throttleFirst(500, TimeUnit.MILLISECONDS)
//                .subscribe(integer -> AlbumActivity.actionStart(AlbumsActivity.this, albums.get(integer).name));
        RxAdapterView.itemClicks(mListView).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(integer -> AlbumActivity.actionStart(AlbumsActivity.this, albums.get(integer).albumName));
        RxView.clicks(backIv).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> finish());
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (requestCode == 800) {
            if (perms.contains(Manifest.permission.READ_EXTERNAL_STORAGE)) {

            }
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == 800) {
            if (perms.contains(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ToastUtils.showToast("没有读取sd卡权限，无法获取相册信息");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
