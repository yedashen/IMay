package shen.da.ye.imay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import shen.da.ye.imay.Album.AlbumsActivity;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.take_photo_layout)
    TextView mPhotoLayout;

    @BindView(R.id.browse_photo_layout)
    TextView mBrosePhotoLayout;

    @BindView(R.id.take_voice_layout)
    TextView mVoiceLayout;

    @BindView(R.id.browse_voice_layout)
    TextView mBroseVoiceLayout;

    @BindView(R.id.take_record_layout)
    TextView mRecordLayout;

    @BindView(R.id.browse_record_layout)
    TextView mBroseRecordLayout;

    @BindView(R.id.take_diary_layout)
    TextView mDiaryLayout;

    @BindView(R.id.browse_diary_layout)
    TextView mBroseDiaryLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupClickListener();
    }

    private void setupClickListener() {
        RxView.clicks(mPhotoLayout).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {
                });

        RxView.clicks(mBrosePhotoLayout).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {
                    startActivity(new Intent(this, AlbumsActivity.class));
                });

        RxView.clicks(mVoiceLayout).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {
                });

        RxView.clicks(mBroseVoiceLayout).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {
                });

        RxView.clicks(mRecordLayout).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {
                });

        RxView.clicks(mBroseRecordLayout).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {
                });

        RxView.clicks(mDiaryLayout).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {
                });

        RxView.clicks(mBroseDiaryLayout).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {
                });
    }
}
