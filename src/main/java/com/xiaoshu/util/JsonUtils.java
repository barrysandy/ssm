package com.xiaoshu.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import java.util.List;
import java.util.Map;

/**
 * Created by Kun on 2017/04/25 0025.
 */
public class JsonUtils {

    /**
     * 转json
     *
     * @param state
     * @param msg
     * @return
     */
    public static String turnJson(Boolean state, String msg, Object o) {
        JSONObject obj = new JSONObject();
        obj.put("state", state);
        obj.put("msg", msg);
        if (o instanceof List) {
            o = toJsonArray((List) o);
        } else if (o instanceof Map) {
            o = toJsonObject((Map) o);
        } else if (o == null) {
            o = "";
        } else {
            o = o.toString();
        }
        obj.put("result", o);
        String result = obj.toString();
        return result;
    }



    public static JSONObject toJsonObject(Map map) {
        JsonConfig config = new JsonConfig();
        JSONObject resJson = JSONObject.fromObject(map, config);
        return resJson;
    }

    public static JSONArray toJsonArray(List list) {
        JsonConfig config = new JsonConfig();
        JSONArray resJson = JSONArray.fromObject(list, config);
        return resJson;
    }


    /**
     * 转json
     *
     * @param state
     * @param msg
     * @return
     */
    public static String turnJsonString(Object o) {
        JSONObject obj = new JSONObject();
        obj.put("json", o);
        String result = obj.toString();
        return result;
    }

}
