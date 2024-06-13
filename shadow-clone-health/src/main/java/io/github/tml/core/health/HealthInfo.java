package io.github.tml.core.health;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;

public class HealthInfo {

    private final JSONObject healthInfo;

    public HealthInfo() {
        this.healthInfo = new JSONObject(new ConcurrentHashMap<>());
    }

    public HealthInfo(Map<String,Object> data) {
        this.healthInfo = new JSONObject(new ConcurrentHashMap<>(data));
    }

    public Object getInfo(String name){
        return healthInfo.get(name);
    }

    public <T> T getInfo(String name,Class<T> type){
        return healthInfo.getObject(name,type);
    }

    public Object getInfo(String ...args){
        StringJoiner path = new StringJoiner(".");
        path.add("$");
        for (String arg : args) {
            path.add(arg);
        }
        return JSONPath.eval(healthInfo, path.toString());
    }

    public <T> boolean putInfo(String key,T value){

        if (healthInfo.containsKey(key)) {
            healthInfo.put(key,value);
            return true;
        }
        return false;
    }

    public <T> boolean putInfo(Map<String,T> map){
        healthInfo.putAll(map);
        return false;
    }

    @Override
    public String toString() {
        return healthInfo.toString();
    }
}
