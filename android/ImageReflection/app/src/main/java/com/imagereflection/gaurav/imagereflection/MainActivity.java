package com.imagereflection.gaurav.imagereflection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {
    private ImageView mainIV, reflectedIV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainIV = (ImageView) findViewById(R.id.mainIV);
        mainIV.setImageResource(R.drawable.example_image);
        reflectedIV = (ImageView) findViewById(R.id.reflectedIV);
        // converting drawable to bitmap
        final Bitmap bitmapIMG = BitmapFactory.decodeResource(getResources(), R.drawable.example_image);
        reflectedIV.setImageBitmap(createImageReflection(
                bitmapIMG, 150, 0));
    }

    /**
     * method to create reflected image
     * @param originalImage
     * @param reflectionHeight
     * @param reflectionGap
     * @return
     */
    public Bitmap createImageReflection(Bitmap originalImage,
                                       int reflectionHeight, int reflectionGap) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        Matrix matrix = new Matrix();
        // Realize image flip 90 degrees
        matrix.preScale(1, -1);
        if (reflectionHeight > height)
            reflectionHeight = height;

        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height
                - reflectionHeight, width, reflectionHeight, matrix, false);
        // Create reflection
        Bitmap finalReflection = Bitmap.createBitmap(width, reflectionHeight,
                Bitmap.Config.ARGB_8888);

        // Create canvas
        Canvas canvas = new Canvas(finalReflection);

        // The reflection image onto a canvas
        canvas.drawBitmap(reflectionImage, 0, reflectionGap, null);
        Paint shaderPaint = new Paint();
        // Create a linear gradient LinearGradient object
        LinearGradient shader = new LinearGradient(0, 0, 0,
                finalReflection.getHeight(), 0x77ffffff, 0x00ffffff,
                Shader.TileMode.MIRROR);
        shaderPaint.setShader(shader);
        shaderPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // Canvas painting gradient effect to the picture, appeared the reflection effect.
        canvas.drawRect(0, 0, width, finalReflection.getHeight()
                + reflectionGap, shaderPaint);
        return finalReflection;
    }
}
