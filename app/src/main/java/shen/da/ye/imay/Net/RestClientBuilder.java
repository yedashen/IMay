package shen.da.ye.imay.Net;

import java.util.WeakHashMap;

import shen.da.ye.imay.Net.Callback.IError;
import shen.da.ye.imay.Net.Callback.IFailure;
import shen.da.ye.imay.Net.Callback.IRequest;
import shen.da.ye.imay.Net.Callback.ISuccess;

/**
 * Created by cy on 2017/9/1 0001 09:24
 */

public class RestClientBuilder {

    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private String mUrl = null;
    private IRequest mIRequest = null;
    private ISuccess mISuccess = null;
    private IFailure mIFailure = null;
    private IError mIError = null;

    RestClientBuilder() {
    }

    public final RestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder params(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RestClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final RestClientBuilder success(ISuccess iSuccess) {
        this.mISuccess = iSuccess;
        return this;
    }

    public final RestClientBuilder failure(IFailure iFailure) {
        this.mIFailure = iFailure;
        return this;
    }

    public final RestClientBuilder error(IError iError) {
        this.mIError = iError;
        return this;
    }

    public final RestClient build() {
        return new RestClient(mUrl, PARAMS,
                mIRequest, mISuccess, mIFailure,
                mIError);
    }
}
