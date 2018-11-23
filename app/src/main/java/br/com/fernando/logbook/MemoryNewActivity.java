package br.com.fernando.logbook;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;

import br.com.fernando.dao.MemoryViewModel;
import br.com.fernando.model.Memory;

public class MemoryNewActivity extends AppCompatActivity {

    private MemoryViewModel memoryViewModel;
    private EditText textArea;
    private EditText title, imgSrc;
    private ImageView imageView;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_new);

        title = findViewById(R.id.input_memory_title);
        textArea = findViewById(R.id.input_memory_description);
        imageView = findViewById(R.id.img_memory);
        imgSrc = findViewById(R.id.input_memory_src);

        textArea.setOnTouchListener((v, event) -> {
            v.getParent().requestDisallowInterceptTouchEvent(true);
            switch (event.getAction() & MotionEvent.ACTION_MASK){
                case MotionEvent.ACTION_UP:
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
            return false;
        });

        imageView.setOnClickListener(v -> {
            file = new File(
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES), "photo.jpg");
            Uri outputDir = FileProvider.getUriForFile(
                    MemoryNewActivity.this, BuildConfig.APPLICATION_ID, file);


            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputDir);

            startActivityForResult(intent, 1_000);
        });

        imageView.getDrawable();

        memoryViewModel = ViewModelProviders.of(this)
                .get(MemoryViewModel.class);

        memoryViewModel.sucess.observe(this, success -> {
            if (Boolean.TRUE.equals(success)) {
                finish();
            }
        });

        Button buttonSave = findViewById(R.id.button_save);
        buttonSave.setOnClickListener(view -> {
            String titleText = title.getText().toString();
            String description = textArea.getText().toString();
            String photo = imgSrc.getText().toString();
            if (!titleText.isEmpty() && !description.isEmpty()){
                memoryViewModel.insert(new Memory(titleText, description, photo));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1_000){
            if(data != null && data.hasExtra("data")){
                Bitmap thumbnail = data.getParcelableExtra("data");
                imageView.setImageBitmap(thumbnail);
            }else{
                int width = imageView.getWidth();
                int height = imageView.getHeight();

                BitmapFactory.Options facOptions = new BitmapFactory.Options();
                facOptions.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(file.getPath(), facOptions);

                int imageWidth = facOptions.outWidth;
                int imageHeight = facOptions.outHeight;

                // Verificar o quanto precisamos scalar a imagem
                int scaleFactor = Math.min(imageWidth / width, imageHeight / height);

                facOptions.inJustDecodeBounds = false;
                facOptions.inSampleSize = scaleFactor;

                Bitmap image = BitmapFactory.decodeFile(file.getPath(), facOptions);
                imageView.setImageBitmap(image);
                imgSrc.setText(file.getPath());
            }
        }
    }
}
