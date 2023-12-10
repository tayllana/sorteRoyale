package com.example.sorteroyale;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sorteroyale.databinding.ActivitySorteioBinding;

public class sorteio extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivitySorteioBinding binding;
    private TextView userName, userEmail;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySorteioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarSorteio.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        userName = navigationView.getHeaderView(0).findViewById(R.id.userName);
        userEmail = navigationView.getHeaderView(0).findViewById(R.id.userEmail);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_conta, R.id.sair)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_sorteio);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        userName.setText("Olá, " + SessionManager.getInstance().getSession().get("nome"));
        userEmail.setText(SessionManager.getInstance().getSession().get("email"));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.sair) {
                    sairClick(navigationView);
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                } else {
                    boolean handled = NavigationUI.onNavDestinationSelected(item, Navigation.findNavController(sorteio.this, R.id.nav_host_fragment_content_sorteio));
                    if (handled) {
                        drawer.closeDrawer(GravityCompat.START);
                    }
                    return handled;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sorteio, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_sorteio);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void sairClick(View view){
        Intent i = new Intent(this, MainActivity.class);
        SessionManager.getInstance().clearSession();
        startActivity(i);
    }

}
