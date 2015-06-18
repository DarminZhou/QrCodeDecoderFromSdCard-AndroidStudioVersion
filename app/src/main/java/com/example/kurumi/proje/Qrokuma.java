package com.example.kurumi.proje;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

/**
 * Created by Kurumi on 18.6.2015.
 */
public class Qrokuma extends Activity implements View.OnClickListener {
    public static class Global
    {
        public static String text=null;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_oku);

        String dosyayolum = getIntent().getStringExtra("dosyayolu");//intentten gelen konum
        //Bitmap pt2 = BitmapFactory.decodeFile("/Download/45.png");
        //String pt = Environment.getExternalStorageDirectory() + "/WhatsApp/Media/WhatsApp Images/resim.png"

        String pt2 = dosyayolum;
        //Toast.makeText(this,""+Environment.getExternalStorageDirectory(), Toast.LENGTH_LONG).show();
        //Environment bize standart olarak dosya yolunun baþlangýcýný veriyor iki kere kullanmaya gerek yok

        Bitmap bMap = BitmapFactory.decodeFile(pt2);

        TextView textv = (TextView) findViewById(R.id.mytext);
        View webbutton=findViewById(R.id.webbutton);

        int width = bMap.getWidth();
        int height = bMap.getHeight();
        int[] pixels = new int[width * height];
        bMap.getPixels(pixels, 0, width, 0, 0, width, height);


        RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Reader reader = new MultiFormatReader();
        try {
            Result result = reader.decode(bitmap);
            Global.text = result.getText();

            textv.setText(Global.text);

            webbutton.setOnClickListener(this);
        } catch (NotFoundException e) {
           Log.i("MainAct", e.getMessage().toString());
            e.printStackTrace();
        } catch (ChecksumException e) {
           Log.i("MainAct", e.getMessage().toString());
            e.printStackTrace();
        } catch (FormatException e) {
            Log.i("MainAct", e.getMessage().toString());
            e.printStackTrace();


        }
    }

    @Override
    public void onClick(View v) {
        Uri uri = Uri.parse(Global.text);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);

    }


}
