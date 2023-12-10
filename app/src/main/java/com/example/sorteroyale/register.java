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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class register extends AppCompatActivity {
    private Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
    public void registerClick(View view){
        Map<String, String> dados = new HashMap<>();
        // Preencher o mapa com as informações dos EditTexts
        dados.put("email", ((EditText) findViewById(R.id.email)).getText().toString());
        dados.put("nome", ((EditText) findViewById(R.id.nome)).getText().toString());
        dados.put("id", Base64.encodeToString((dados.get("email")).getBytes(), Base64.DEFAULT));
        dados.put("senha",  Base64.encodeToString(((((EditText) findViewById(R.id.senha)).getText().toString())).getBytes(), Base64.DEFAULT));


        if(dados.get("nome").isEmpty() || dados.get("email").isEmpty() || dados.get("senha").isEmpty()){
            Toast toast = Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT);
            toast.show();
        }else{
            Pattern pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$");
            Matcher matcher = pattern.matcher(dados.get("email"));
            if(!matcher.matches()){
                Toast toast = Toast.makeText(this, "E-mail inválido!", Toast.LENGTH_SHORT);
                toast.show();
            }else{
                database = new Database(this);
                Map<String, String> result = database.callDataBase("save", dados);
                if(result.get("type").equals("success")){
                    Intent i = new Intent(this, login.class);
                    startActivity(i);
                }else{
                    Toast toast = Toast.makeText(this, result.get("message"), Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }
    }
    public void voltarClick(View view){
        Intent i = new Intent(this, login.class);
        startActivity(i);
    }
}