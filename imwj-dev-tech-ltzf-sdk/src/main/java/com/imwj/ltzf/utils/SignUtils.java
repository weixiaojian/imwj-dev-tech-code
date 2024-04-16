package com.imwj.ltzf.utils;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author wj
 * @create 2024-04-16 16:37
 * @description 签名计算工具类
 */
public class SignUtils {

    /**
     * 生成签名
     * @param params
     * @param partnerKey
     * @return
     */
    public static String createSign(Map<String, String> params, String partnerKey){
        // 生成签名前先去除sign
        params.remove("sign");
        String stringA = packageSign(params, false);
        String stringSignTemp = stringA + "&key=" + partnerKey;
        return DigestUtils.md5Hex(stringSignTemp).toUpperCase();
    }

    /**
     * 参数打包
     * @param params
     * @param urlEncoder
     * @return
     */
    public static String packageSign(Map < String, String > params, boolean urlEncoder) {
        // 先将参数以其参数名的字典序升序进行排序
        TreeMap < String, String > sortedParams = new TreeMap< String, String >(params);
        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Map.Entry< String, String > param: sortedParams.entrySet()) {
            String value = param.getValue();
            if (StringUtils.isBlank(value)) {
                continue;
            }
            if (first) {
                first = false;
            } else {
                sb.append("&");
            }
            sb.append(param.getKey()).append("=");
            if (urlEncoder) {
                try {
                    value = urlEncode(value);
                } catch (UnsupportedEncodingException e) {}
            }
            sb.append(value);
        }
        return sb.toString();
    }

    public static String urlEncode(String src) throws UnsupportedEncodingException {
        return URLEncoder.encode(src, Charsets.UTF_8.name()).replace("+", "%20");
    }


}
