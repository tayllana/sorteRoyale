package com.example.sorteroyale.ui.conta;

        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Base64;
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

        import com.example.sorteroyale.Database;
        import com.example.sorteroyale.MainActivity;
        import com.example.sorteroyale.R;
        import com.example.sorteroyale.SessionManager;
        import com.example.sorteroyale.databinding.FragmentContaBinding;
        import com.example.sorteroyale.sorteio;

        import java.util.HashMap;
        import java.util.Map;

public class ContaFragment extends Fragment {

    private FragmentContaBinding binding;
    private Database database;
    private TextView nome, email;
    private Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ContaViewModel contaViewModel =
                new ViewModelProvider(this).get(ContaViewModel.class);

        binding = FragmentContaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        nome = binding.nomeUpdate;
        email = binding.emailUpdate;
        context = getContext();

        nome.setText(SessionManager.getInstance().getSession().get("nome"));
        email.setText(SessionManager.getInstance().getSession().get("email"));

        email.setTextIsSelectable(false);
        email.setFocusable(false);
        email.setClickable(false);
        email.setFocusableInTouchMode(false);

        Button updateButton = root.findViewById(R.id.update);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateClick(view);
            }
        });
        return root;
    }
    public void updateClick(View view){
        if(nome.getText().toString().isEmpty()){
            Toast toast = Toast.makeText(this.context, "Nada para salvar...", Toast.LENGTH_SHORT);
            toast.show();
        }else {
            Map<String, String> dados = new HashMap<>();
            database = new Database(this.context);

            dados.put("nome", nome.getText().toString());
            dados.put("id", SessionManager.getInstance().getSession().get("id"));
            dados.put("email", SessionManager.getInstance().getSession().get("email"));
            dados.put("senha", SessionManager.getInstance().getSession().get("senha"));

            Map<String, String> result = database.callDataBase("update", dados);
            if(result.get("type").equals("success")){
                Intent i = new Intent(this.context, sorteio.class);
                SessionManager.getInstance().setSession( new HashMap<>(database.callDataBase("getUser", dados)));
                i.putExtra("SESSION", SessionManager.getInstance().getSession());
                startActivity(i);
            }else{
                Toast toast = Toast.makeText(this.context, result.get("message"), Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
