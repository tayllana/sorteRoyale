package com.example.sorteroyale;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class resultado extends AppCompatActivity {
    private TextView numeros;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        this.numeros = findViewById(R.id.numeros);
        numeros.setText(getIntent().getStringExtra("resultado"));
    }
    public void voltarClick(View view){
        Intent i = new Intent(this, sorteio.class);
        startActivity(i);
    }
}