package com.example.sorteroyale.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sorteroyale.R;
import com.example.sorteroyale.databinding.FragmentContaBinding;
import com.example.sorteroyale.databinding.FragmentHomeBinding;
import com.example.sorteroyale.resultado;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Context context;
    private TextView inicio, fim, qtd;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        inicio = binding.inicial;
        fim = binding.ate;
        qtd = binding.qtdNumeros;
        context = getContext();

        Button sortear = root.findViewById(R.id.sortear);
        sortear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortear(view);
            }
        });
        return root;
    }
    public boolean validarValores() {

        if (inicio.getText().toString().isEmpty() || fim.getText().toString().isEmpty() || qtd.getText().toString().isEmpty() || inicio.getText().toString().equals("0") || fim.getText().toString().equals("0") || qtd.getText().toString().equals("0")) {
            Toast toast = Toast.makeText(this.context, "Os valores não podem ser vazios ou igual a 0!", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }else{
            if(Integer.parseInt(fim.getText().toString()) < Integer.parseInt(inicio.getText().toString())){
                Toast toast = Toast.makeText(this.context, "O valor final não pode ser menor que o inicial!", Toast.LENGTH_SHORT);
                toast.show();
                return false;
            }else{
                int sum = 0;
                for (int i = Integer.parseInt(inicio.getText().toString()); i <= Integer.parseInt(fim.getText().toString()); i++) {
                    sum ++;
                }
                if(Integer.parseInt(qtd.getText().toString()) > sum){
                    Toast toast = Toast.makeText(this.context, "Quantidade inválida!", Toast.LENGTH_SHORT);
                    toast.show();
                    return false;
                }
                return true;
            }
        }
    }

    public void sortear(View view) {
        if(validarValores()){
            Random random = new Random();

            Set<Integer> nSorteados = new HashSet<>();
            while (nSorteados.size() < Integer.parseInt(qtd.getText().toString())) {
                nSorteados.add(random.nextInt(Integer.parseInt(fim.getText().toString()) - Integer.parseInt(inicio.getText().toString()) + 1) + Integer.parseInt(inicio.getText().toString()));
            }
            StringBuilder result = new StringBuilder();
            for (int numero : nSorteados) {
                result.append(numero).append(", ");
            }
            if (result.length() > 0) {
                result.setLength(result.length() - 2);
            }
            Intent i = new Intent(this.context, resultado.class);
            i.putExtra("resultado", result.toString());
            startActivity(i);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}