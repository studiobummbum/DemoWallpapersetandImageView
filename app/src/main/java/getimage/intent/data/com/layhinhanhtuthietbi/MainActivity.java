package getimage.intent.data.com.layhinhanhtuthietbi;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button btnGetImage;
    ImageView imgImage;
    Button btnSetWallpaper;

    Uri uriPickImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControl();
        addEvent();
    }

    private void addEvent() {
        btnGetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xulylayhinh();
            }
        });
        btnSetWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWallpaper();
                Toast.makeText(MainActivity.this,"Set Succesfully",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setWallpaper() {
        WallpaperManager wallpaper = WallpaperManager.getInstance(getApplicationContext());

        try {
            wallpaper.setBitmap(getBitmapFromUri(uriPickImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void xulylayhinh() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK){
            try {
                //Uri uriPickImage = data.getData(); bien cuc bo
                uriPickImage = data.getData();//bien toan cuc
                Bitmap hinh = getBitmapFromUri(uriPickImage);
                //Bitmap hinh = MediaStore.Image.Media.getBitmap(getContentResolver(),uriPickImage);
                imgImage.setImageBitmap(hinh);

            }catch (Exception ex){
                Log.e("loi",ex.toString());
            }
        }
    }
// tao 1 cai bitmap de chuyen thanh toan cuc de de set wallpaper hon
    Bitmap getBitmapFromUri(Uri uri) {
        try {
            return MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
        } catch (IOException e) {
            return null;
        }
    }

    private void addControl() {
        btnGetImage = findViewById(R.id.btnGetImage);
        imgImage = findViewById(R.id.imgImage);
        btnSetWallpaper = findViewById(R.id.btnSetWallpaper);

    }
}
