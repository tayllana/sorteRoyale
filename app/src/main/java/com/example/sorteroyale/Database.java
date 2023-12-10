package com.example.sorteroyale;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
    private static final String PREF_NAME = "database-usuarios";
    private Context context;

    public Database(Context context) {
        this.context = context;
    }

    public Map<String, String> save(Map<String, String> dados) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Map<String, String> response = new HashMap<>();

        if (prefs.getString(dados.get("id"), null) != null) {
            response.put("type", "error");
            response.put("message", "Este e-mail já está registrado!");
            return response;
        }
        Gson gson = new Gson();
        editor.putString(dados.get("id"),  gson.toJson(dados));
        editor.commit();
        response.put("type", "success");
        response.put("message", "Registrado com sucesso!");
        return response;
    }
    public Map<String, String> checkData(Map<String, String> dados) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Map<String, String> response = new HashMap<>();

        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>() {}.getType();
        Map<String, String> result = gson.fromJson(prefs.getString(dados.get("id"), null), type);

        if (result == null || !(result.get("email").equals(dados.get("email")) && result.get("senha").equals(dados.get("senha")))) {
            response.put("type", "error");
            response.put("message", "E-mail ou senha não conferem!");
            return response;
        }
        response.put("type", "success");
        response.put("message", "Sucesso!");
        return response;
    }
    public List<Map<String, String>> getAll() {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        List<Map<String, String>> allData = new ArrayList<>();

        Map<String, ?> allEntries = prefs.getAll();
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>() {}.getType();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String key = entry.getKey();
            String jsonValue = entry.getValue().toString();
            Map<String, String> data = gson.fromJson(jsonValue, type);
            allData.add(data);
        }

        return allData;
    }
    public Map<String, String> getUser(Map<String, String> dados) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Map<String, String> response = new HashMap<>();

        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>() {}.getType();
        return gson.fromJson(prefs.getString(dados.get("id"), null), type);
    }
    public Map<String, String> update(Map<String, String> dados) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Map<String, String> response = new HashMap<>();

        Gson gson = new Gson();
        editor.putString(dados.get("id"), gson.toJson(dados));
        editor.commit();

        response.put("type", "success");
        response.put("message", "Atualizado com sucesso!");
        getAll();
        return response;
    }
    public Map<String, String> delete(String id) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Map<String, String> response = new HashMap<>();

        editor.remove(id);
        editor.apply();

        response.put("type", "success");
        response.put("message", "Registro excluído com sucesso!");
        getAll();
        return response;
    }
    public Map<String, String> callDataBase(String action, Map<String, String> dados){
        switch (action){
            case "save":
                return this.save(dados);
            case "checkData":
                return this.checkData(dados);
            case "getUser":
                return this.getUser(dados);
            case "update":
                return this.update(dados);
            case "getAll":
                break;
            case "delete":
                break;
        }
        return null;
    }
}
