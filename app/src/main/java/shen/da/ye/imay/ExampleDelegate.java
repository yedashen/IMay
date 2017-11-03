package shen.da.ye.imay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import shen.da.ye.imay.Net.RestClient;
import shen.da.ye.imay.utils.Logger;

/**
 * Created by cy on 2017/8/28 0028 17:37
 */

public class ExampleDelegate extends IMayDelegate {

    private static final String TAG = "cy===ExampleDelegate";
    @BindView(R.id.test_iv)
    TextView testTv;

    @Override
    public Object setLayout() {
        return R.layout.test_layout;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        Logger.i(TAG, "bindView");
        testTv.setText("测试");
        testRestClient();
    }

    private void testRestClient() {
        RestClient
                .builder()
                .url("http://news.baidu.com/")
                .success(response -> testTv.setText(response))
                .failure(() -> testTv.setText("请求失败"))
                .error(((code, msg) -> testTv.setText("请求出错")))
                .build()
                .get();
    }
}
