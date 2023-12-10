package com.example.sorteroyale;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {
    private Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void registerClick(View view){
        Intent i = new Intent(this, register.class);
        startActivity(i);
    }
    public void loginClick(View view){
        Map<String, String> dados = new HashMap<>();

        dados.put("email", ((EditText) findViewById(R.id.nome)).getText().toString());
        dados.put("id", Base64.encodeToString((dados.get("email")).getBytes(), Base64.DEFAULT));
        dados.put("senha",  Base64.encodeToString(((((EditText) findViewById(R.id.senha)).getText().toString())).getBytes(), Base64.DEFAULT));

        if(dados.get("email").isEmpty() || dados.get("senha").isEmpty()){
            Toast toast = Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT);
            toast.show();
        }else{
            database = new Database(this);
            Map<String, String> result = database.callDataBase("checkData", dados);
            if(result.get("type").equals("success")){
                Intent i = new Intent(this, sorteio.class);
                SessionManager.getInstance().setSession( new HashMap<>(database.callDataBase("getUser", dados)));
                i.putExtra("SESSION", SessionManager.getInstance().getSession());
                startActivity(i);
            }else{
                Toast toast = Toast.makeText(this, result.get("message"), Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}