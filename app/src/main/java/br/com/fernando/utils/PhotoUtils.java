package br.com.fernando.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class PhotoUtils {

    public static Bitmap loadPhoto(String path, int width, int height) {
        BitmapFactory.Options facOptions = new BitmapFactory.Options();
        facOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, facOptions);

        int imageWidth = facOptions.outWidth;
        int imageHeight = facOptions.outHeight;

        // Verificar o quanto precisamos scalar a imagem
        int scaleFactor = Math.min(imageWidth / width, imageHeight / height);

        facOptions.inJustDecodeBounds = false;
        facOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeFile(path, facOptions);
    }

}
