package br.com.fernando.logbook;

import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;

import br.com.fernando.dao.MemoryViewModel;
import br.com.fernando.model.Memory;
import br.com.fernando.utils.PhotoUtils;

public class MemoryDetailActivity extends AppCompatActivity {

    private Memory memory;
    private MemoryViewModel memoryViewModel;
    private EditText textArea;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_detail);

        textArea = findViewById(R.id.input_memory_description);
        imageView = findViewById(R.id.img_memory);

        memoryViewModel = ViewModelProviders.of(this)
                .get(MemoryViewModel.class);
        memoryViewModel.memoryLiveData.observe(this, memory -> {
            if(memory != null){
                this.memory = memory;
                setTitle(memory.getTitle());
                textArea.setText(memory.getDescription());

                try {
                    File file = new File(memory.getPhoto());

                    int width = imageView.getWidth();
                    int height = imageView.getHeight();

                    imageView.setImageBitmap(PhotoUtils.loadPhoto(file.getPath(), width, height));
                } catch (Exception ex) { }
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
