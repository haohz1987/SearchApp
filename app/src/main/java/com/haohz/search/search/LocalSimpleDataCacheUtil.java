package com.haohz.search.search;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by DoubleQ on 2018/7/5.
 */

public class LocalSimpleDataCacheUtil {

    public static void insert(Context context, String key, String cacheData, int maxLength) {
        LinkedHashSet<String> cacheSet = new LinkedHashSet<>();
        String cacheJson = (String) SharePreferenceUtil.get(context, key, "");
        LinkedHashSet<String> set = new Gson().fromJson(cacheJson, new TypeToken<LinkedHashSet<String>>() {
        }.getType());
        if (set != null) {
            cacheSet.addAll(set);
        }
        if (cacheSet.size() <= maxLength) {
            for (String s : cacheSet) {
                if (cacheData.equals(s)) {
                    cacheSet.remove(s);
                    break;
                }
            }
            cacheSet.add(cacheData);
        }
        if (cacheSet.size() == maxLength + 1) {
            List<String> list = new ArrayList<>();
            list.addAll(cacheSet);
            Collections.reverse(list);
            cacheSet.remove(list.get(list.size() - 1));
        }
        SharePreferenceUtil.put(context, key, new Gson().toJson(cacheSet));
    }

    public static List<String> query(Context context, String key) {
        String cacheJson = (String) SharePreferenceUtil.get(context, key, "");
        LinkedHashSet<String> set = new Gson().fromJson(cacheJson, new TypeToken<LinkedHashSet<String>>() {}.getType());
        LinkedHashSet<String> historySet =  new LinkedHashSet<>();
        if (set !=null) {
            historySet.addAll(set);
        }
        List<String> cacheList = new ArrayList<>();
        cacheList.addAll(historySet);
        Collections.reverse(cacheList);
        return cacheList;
    }

    public static boolean deleteAll(Context context, String key) {
        try {
            SharePreferenceUtil.put(context, key, "");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
