package com.leshchyshyn.mobileapp.utils;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JSONParser {

    private JSONParser() {
        //default empty constructor for Singleton class with no attributes
    }

    public static <T> List<T> getFromJSONtoArrayList(String jsonString, Type type) {
        if (!isValid(jsonString))
            return new ArrayList<>();
        return new Gson().fromJson(jsonString, type);
    }

    private static boolean isValid(String jsonString) {
        try {
            new JsonParser().parse(jsonString);
            return true;
        } catch (JsonSyntaxException e) {
            return false;
        }
    }
}
