package com.ddl.hat.level;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.renderscript.Sampler;
import android.util.JsonReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Level {
    private int m_levelNumber = 0;
    private Integer[] mThumbIds = new Integer[0];

    public static Level getLevel(Context p_context, int p_levelNumber) {
        Level l_levelToreturn = new Level();
        l_levelToreturn.m_levelNumber = p_levelNumber;

        try {
            SharedPreferences mPrefs = p_context.getSharedPreferences(p_context.getApplicationInfo().name, Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String l_jsObject = mPrefs.getString("level" + p_levelNumber, null);
            l_levelToreturn.mThumbIds = gson.fromJson(l_jsObject, Integer[].class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return l_levelToreturn;
    }

    public Integer[] getmThumbIds() {
        return mThumbIds;
    }

    public int getLevelNumber() {
        return m_levelNumber;
    }
}
