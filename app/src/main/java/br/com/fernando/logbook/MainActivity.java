package br.com.fernando.logbook;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import br.com.fernando.dao.MemoryViewModel;

public class MainActivity extends AppCompatActivity {

    private MemoryViewModel memoryViewModel;
    private MemoryAdapter adapter = new MemoryAdapter(memory -> {
        Intent intent = new Intent(getApplicationContext(), MemoryDetailActivity.class);
        intent.putExtra("id", memory.getId());
        startActivity(intent);
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermission();

        memoryViewModel = ViewModelProviders.of(this)
                .get(MemoryViewModel.class);
        memoryViewModel.memorys.observe(this, memorys -> {
            if(memorys != null){
                adapter.setup(memorys);
            }
        });

        RecyclerView taskList = findViewById(R.id.memory_list);
        taskList.setLayoutManager(new LinearLayoutManager(this));
        taskList.setAdapter(adapter);

        FloatingActionButton buttonCreate = findViewById(R.id.fab_new_memory);
        buttonCreate.setOnClickListener(v -> startActivity(
                new Intent(getApplicationContext(), MemoryNewActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        memoryViewModel.fetchMemorys();
    }

    public void requestPermission() {
        if (hasLocationPermission()) {
            return;
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)) {
            new AlertDialog.Builder(MainActivity.this)
                    .setMessage("Se não deseja compartilhar sua localização marque a opção 'Não perguntar novamente'")
                    .setPositiveButton("OK", null)
                    .show();
        }

        ActivityCompat.requestPermissions(
                MainActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                1000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    boolean hasLocationPermission() {
        return ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }
}
