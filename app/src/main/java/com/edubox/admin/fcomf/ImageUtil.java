package com.edubox.admin.fcomf;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

public class ImageUtil {


    public static boolean hasAlpha(Bitmap bitmap) {
        return bitmap.hasAlpha();
    }

    public static Bitmap resizeImage(Bitmap image, int width, int height) {
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public static Bitmap makeRoundedCorner(Bitmap image, int cornerRadius) {
        Bitmap roundedBitmap = Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundedBitmap);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(image, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

        RectF rect = new RectF(0, 0, image.getWidth(), image.getHeight());
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint);

        return roundedBitmap;
    }
}

   
    
  