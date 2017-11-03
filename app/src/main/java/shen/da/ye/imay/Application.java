package shen.da.ye.imay;

/**
 * Created by cy on 2017/3/30.
 */

public class Application extends android.app.Application {
    private static final String TAG = "cy=====Application";
    public static Application sharedInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedInstance = this;
    }
}
