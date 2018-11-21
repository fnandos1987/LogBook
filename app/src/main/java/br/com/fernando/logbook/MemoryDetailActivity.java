package br.com.fernando.logbook;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import br.com.fernando.dao.MemoryViewModel;
import br.com.fernando.model.Memory;

public class MemoryDetailActivity extends AppCompatActivity {

    private Memory memory;
    private MemoryViewModel memoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_detail);

        memoryViewModel = ViewModelProviders.of(this)
                .get(MemoryViewModel.class);
        memoryViewModel.memoryLiveData.observe(this, memory -> {
            if(memory != null){
                this.memory = memory;
                setTitle(memory.getTitle());
            }
        });

        memoryViewModel.sucess.observe(this, success -> {
            if (Boolean.TRUE.equals(success)) {
                finish();
            }
        });

        int id = getIntent().getIntExtra("id", 0);
        memoryViewModel.findMemoryById(id);

        Button buttonDelete = findViewById(R.id.button_delete);
        buttonDelete.setOnClickListener(view -> {
            memoryViewModel.delete(this.memory);
        });

        Button buttonDone = findViewById(R.id.button_done);
        buttonDone.setOnClickListener(view -> {
            memoryViewModel.update(this.memory);
        });
    }
}
