package io.code.framework.core.trace.interceptor;

import io.code.framework.core.trace.Constants;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.MDC;

import java.io.IOException;

/**
 * OKHttp 添加traceId 拦截器
 */
public class OkHttpTraceIdInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        String traceId = MDC.get(Constants.TRACE_ID);
        Request request = null;
        if (traceId != null) {
            //添加请求体
            request = chain.request().newBuilder().addHeader(Constants.TRACE_ID, traceId).build();
        }
        Response originResponse = chain.proceed(request);

        return originResponse;
    }
}
