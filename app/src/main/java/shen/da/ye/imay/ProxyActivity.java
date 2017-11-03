package shen.da.ye.imay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by cy on 2017/8/28 0028 17:20
 * 尽量类多一些，每个类代码少一些，这样结构才会更加清晰
 */

public abstract class ProxyActivity extends SupportActivity {

    public abstract IMayDelegate setFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupContainer(savedInstanceState);
    }

    private void setupContainer(@Nullable Bundle savedInstanceState) {
        final ContentFrameLayout container = new ContentFrameLayout(this);
        container.setId(R.id.delegate_container);//给FrameLatyout设置id，随意设
        setContentView(container);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.delegate_container, setFragment());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }
}
