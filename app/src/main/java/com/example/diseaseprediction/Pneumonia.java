package com.example.diseaseprediction;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.diseaseprediction.ml.Model;
import com.example.diseaseprediction.ml.Model;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;

public class Pneumonia extends AppCompatActivity {
    private ImageView imageView;
    private Button select,predict;
    private TextView tv;
    private Bitmap img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pneumonia);
        imageView=findViewById(R.id.imgv);
        predict=findViewById(R.id.predictbtn);
        select=findViewById(R.id.selectbtn);
        tv=findViewById(R.id.tv);

        select.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,100);
            }

        });

        predict.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                img =Bitmap.createScaledBitmap(img,224,224,true);
                try {
                    Model model = Model.newInstance(getApplicationContext());

                    // Creates inputs for reference.
                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.UINT8);

                    TensorImage tensorImage=new TensorImage(DataType.UINT8);
                    tensorImage.load(img);
                    ByteBuffer byteBuffer=tensorImage.getBuffer();
                    inputFeature0.loadBuffer(byteBuffer);

                    // Runs model inference and gets result.
                    Model.Outputs outputs = model.process(inputFeature0);
                    TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                    // Releases model resources if no longer used.
                    model.close();
                    tv.setText(outputFeature0.getFloatArray()[0]+"\n"+outputFeature0.getFloatArray()[1]);
                } catch (IOException e) {
                    // TODO Handle the exception
                }




            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==100)
        {
            imageView.setImageURI(data.getData());
            Uri uri =data.getData();
            try {
                img= MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}