package com.example.truongquocdat_2123110209;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class WishlistManager {
    private static final String PREF_NAME = "wishlist_prefs";
    private static final String KEY_LIST = "wishlist_items";
    private static WishlistManager instance;
    private SharedPreferences prefs;

    private WishlistManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static WishlistManager getInstance(Context context) {
        if (instance == null) {
            instance = new WishlistManager(context.getApplicationContext());
        }
        return instance;
    }

    public void add(Product product) {
        List<Product> currentList = getList();
        if (!currentList.contains(product)) {
            currentList.add(product);
            saveList(currentList);
        }
    }

    public void remove(Product product) {
        List<Product> currentList = getList();
        currentList.remove(product);
        saveList(currentList);
    }

    public List<Product> getList() {
        String json = prefs.getString(KEY_LIST, "[]");
        Type type = new TypeToken<List<Product>>(){}.getType();
        return new Gson().fromJson(json, type);
    }

    private void saveList(List<Product> list) {
        String json = new Gson().toJson(list);
        prefs.edit().putString(KEY_LIST, json).apply();
    }
}

