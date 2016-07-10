package org.liujiaxin.jaweb.mvc.route;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.liujiaxin.jaweb.mvc.handler.Interceptor;

public class URI {

    private String uri;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public URI(String uri) {
        super();
        this.uri = uri;
    }

    public List<Interceptor> getMatchedInterceptor(Map<String, Interceptor> interceptor_map) {
        List<Interceptor> list = new ArrayList<Interceptor>();
        for (String interceptorUri : interceptor_map.keySet()) {
            String returnInterceptor = matcher(this.uri, interceptorUri);
            if (returnInterceptor != null) {
                list.add(interceptor_map.get(returnInterceptor));
            }
        }
        return list;
    }

    public String matcher(String url, String interceptors) {

        if (url.equals(interceptors))
            return interceptors;
        if (interceptors.endsWith("/"))
            return null;
        String[] urlsArray = url.split("/");
        String[] interceptorsArray = interceptors.split("/");
        if (interceptorsArray.length < urlsArray.length) {
            boolean isMatched = true;
            if (interceptorsArray[interceptorsArray.length - 1].equals("*")) {
                for (int i = 0; i < interceptorsArray.length; i++) {
                    if (!isMatched(urlsArray[i], interceptorsArray[i])) {
                        isMatched = false;
                        break;
                    }
                }
                if (isMatched)
                    return interceptors;
            } else {
                return null;
            }
        }

        if (interceptorsArray.length == urlsArray.length) {
            // 如果长度相等
            boolean isMatched = true;
            for (int i = 0; i < interceptorsArray.length; i++) {// 依次比较
                if (!isMatched(urlsArray[i], interceptorsArray[i])) {
                    isMatched = false;
                    break;
                }
            }
            if (isMatched) {
                return interceptors;
            }
        }
        return null;

    }

    private boolean isMatched(String urlPart, String interceptorPart) {
        return urlPart.equals(interceptorPart) || interceptorPart.equals("*");
    }

    @Override
    public int hashCode() {
        return uri.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof URI) {
            return ((URI) obj).uri.equals(this.uri);

        }
        return false;
    }
}
