package br.com.fernando.logbook;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;

import br.com.fernando.dao.MemoryViewModel;
import br.com.fernando.model.Memory;

public class MemoryNewActivity extends AppCompatActivity {

    private MemoryViewModel memoryViewModel;
    private EditText textArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_new);

        textArea = findViewById(R.id.input_memory_description);
        textArea.setOnTouchListener((v, event) -> {
            v.getParent().requestDisallowInterceptTouchEvent(true);
            switch (event.getAction() & MotionEvent.ACTION_MASK){
                case MotionEvent.ACTION_UP:
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
            return false;
        });

        memoryViewModel = ViewModelProviders.of(this)
                .get(MemoryViewModel.class);

        memoryViewModel.sucess.observe(this, success -> {
            if (Boolean.TRUE.equals(success)) {
                finish();
            }
        });

        Button buttonSave = findViewById(R.id.button_save);
        buttonSave.setOnClickListener(view -> {
            EditText inputNewTask = findViewById(R.id.input_memory_title);
            String value = inputNewTask.getText().toString();
            if (!value.isEmpty()){
                memoryViewModel.insert(new Memory(value, "teste", "photo"));
            }
        });
    }
}
