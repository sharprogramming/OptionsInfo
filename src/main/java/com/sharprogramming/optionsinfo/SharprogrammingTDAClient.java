package com.sharprogramming.optionsinfo;

import com.studerw.tda.client.HttpTdaClient;
import com.studerw.tda.http.LoggingInterceptor;
import com.studerw.tda.http.cookie.CookieJarImpl;
import com.studerw.tda.http.cookie.store.MemoryCookieStore;
import com.studerw.tda.model.option.OptionChain;
import com.studerw.tda.parse.TdaJsonParser;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class SharprogrammingTDAClient extends HttpTdaClient {

    public Properties tdaProps;
    final TdaJsonParser tdaJsonParser = new TdaJsonParser();
    public SharprogrammingTDAClient(Properties properties){
        super(properties);
        this.tdaProps = properties;
        httpClient = new OkHttpClient.Builder().
                cookieJar(new CookieJarImpl(new MemoryCookieStore())).
                addInterceptor(new OauthInterceptor(this, tdaProps)).
                addInterceptor(new LoggingInterceptor("TDA_HTTP",
                        Integer.parseInt(tdaProps.getProperty("tda.debug.bytes.length")))).
                build();
    }
    public OptionChain getOptionChain(String symbol){

        if (StringUtils.isBlank(symbol)) {
            throw new IllegalArgumentException("Symbol cannot be blank.");
        }

        HttpUrl.Builder urlBuilder = baseUrl("marketdata", "chains")
                .addQueryParameter("strategy", "SINGLE")
                .addQueryParameter("includeQuotes", "TRUE")
                .addQueryParameter("symbol", symbol.toUpperCase());

        Request request = new Request.Builder().url(urlBuilder.build()).headers(defaultHeaders())
                .build();

        try (Response response = this.httpClient.newCall(request).execute()) {
            checkResponse(response, false);
            return tdaJsonParser.parseOptionChain(response.body().byteStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private Headers defaultHeaders() {
        Map<String, String> defaultHeaders = new HashMap<>();
        defaultHeaders.put("Accept", "application/json");
//    defaultHeaders.put("Accept-Encoding", "gzip");
//    defaultHeaders.put("Accept-Language", "en-US");
        return Headers.of(defaultHeaders);
    }

    public HttpUrl.Builder getBaseURL(String... pathSegments){
        return super.baseUrl(pathSegments);
    }


    /**
     * @param response the tda response
     * @param emptyJsonOk is an empty JSON object or array actually OK (e.g. fetchMovers)?
     */
    private void checkResponse(Response response, boolean emptyJsonOk) {
        if (!response.isSuccessful()) {
            String errorMsg = response.message();
            if (StringUtils.isBlank(errorMsg)) {
                try {
                    errorMsg = response.body().string();
                } catch (Exception e) {
                    errorMsg = "UNKNOWN";
                }
            }
            String msg = String
                    .format("Non 200 response:  [%d - %s] - %s", response.code(), errorMsg,
                            response.request().url());
            throw new RuntimeException(msg);
        }
        if (!emptyJsonOk) {
            try {
                String json = response.peekBody(100).string();
                if ("{}".equals(json) || "[]".equals(json)) {
                    String msg = String
                            .format("Empty json body:  [%d - %s] - %s", response.code(), response.message(),
                                    response.request().url());
                    throw new RuntimeException(msg);
                }
            } catch (IOException e) {
                throw new RuntimeException("Error checking for JSON empty body on response");
            }
        }
    }

    OkHttpClient httpClient;
}
