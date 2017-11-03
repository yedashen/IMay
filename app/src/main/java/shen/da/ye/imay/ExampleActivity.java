package shen.da.ye.imay;

/**
 * Created by cy on 2017/8/28 0028 17:37
 */

public class ExampleActivity extends ProxyActivity {

    @Override
    public IMayDelegate setFragment() {
        return new ExampleDelegate();
    }

}
