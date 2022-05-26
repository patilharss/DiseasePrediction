package com.example.diseaseprediction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class CancerActivity extends AppCompatActivity {

    Button reset , submit;
    TextView result;
    Interpreter interpreter;
    EditText radius_mean, texture_mean, perimeter_mean,
            area_mean, smoothness_mean, compactness_mean, concavity_mean,
    concavepoints_mean, symmetry_mean, fractal_dimension_mean,
            radius_se,texture_se,perimeter_se, area_se,smoothness_se,compactness_se, concavity_se,concavepoints_se, symmetry_se,
            fractal_dimension_se, radius_worst, texture_worst,
            perimeter_worst, area_worst, smoothness_worst,compactness_worst, concavity_worst, concavepoints_worst,
            symmetry_worst, fractal_dimension_worst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancer);

        radius_mean=findViewById(R.id.radiusmean);
        texture_mean=findViewById(R.id.texturemean);
        perimeter_mean=findViewById(R.id.perimetermean);
        area_mean=findViewById(R.id.areamean);
        smoothness_mean=findViewById(R.id.smoothnessmean);
        compactness_mean=findViewById(R.id.compactnessmean);
        concavity_mean=findViewById(R.id.concavitymean);
        concavepoints_mean=findViewById(R.id.concavepointsmean);
        symmetry_mean=findViewById(R.id.symmetrymean);
        fractal_dimension_mean=findViewById(R.id.fractaldimensionmean);
        radius_se=findViewById(R.id.radiusse);
        texture_se=findViewById(R.id.texturese);
        perimeter_se=findViewById(R.id.perimeterse);
        area_se=findViewById(R.id.arease);
        smoothness_se=findViewById(R.id.smoothnessse);
        compactness_se=findViewById(R.id.compactnessse);
        concavity_se=findViewById(R.id.concavityse);
        concavepoints_se=findViewById(R.id.concavepointsse);
        symmetry_se=findViewById(R.id.symmetryse);
        fractal_dimension_se=findViewById(R.id.fractaldimensionse);
        radius_worst=findViewById(R.id.radiusworst);
        texture_worst=findViewById(R.id.textureworst);
        perimeter_worst=findViewById(R.id.perimeterworst);
        area_worst=findViewById(R.id.areaworst);
        smoothness_worst=findViewById(R.id.smoothnessworst);
        compactness_worst=findViewById(R.id.compactnessworst);
        concavity_worst=findViewById(R.id.concavityworst);
        concavepoints_worst=findViewById(R.id.concavepointsworst);
        symmetry_worst=findViewById(R.id.symmetry_worst);
        fractal_dimension_worst=findViewById(R.id.fractaldimensionworst);
        submit=findViewById(R.id.submitButton);
        reset=findViewById(R.id.resetButton);
        result=findViewById(R.id.result);

        try{
            interpreter=new Interpreter(loadModelfile());

        }catch (Exception e){
            e.printStackTrace();
        }




        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Reset button
                radius_mean.getText().clear();
                texture_mean.getText().clear();
                perimeter_mean.getText().clear();
                area_mean.getText().clear();
                smoothness_mean.getText().clear();
                compactness_mean.getText().clear();
                concavity_mean.getText().clear();
                concavepoints_mean.getText().clear();
                symmetry_mean.getText().clear();
                fractal_dimension_mean.getText().clear();
                radius_se.getText().clear();
                texture_se.getText().clear();
                perimeter_se.getText().clear();
                area_se.getText().clear();
                smoothness_se.getText().clear();
                compactness_se.getText().clear();
                concavity_se.getText().clear();
                concavepoints_se.getText().clear();
                symmetry_se.getText().clear();
                fractal_dimension_se.getText().clear();
                radius_worst.getText().clear();
                texture_worst.getText().clear();
                perimeter_worst.getText().clear();
                area_worst.getText().clear();
                smoothness_worst.getText().clear();
                compactness_worst.getText().clear();
                concavity_worst.getText().clear();
                concavepoints_worst.getText().clear();
                symmetry_worst.getText().clear();
                fractal_dimension_worst.getText().clear();
                result.setText("Result will appear here");
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Submit button

                if (radius_mean.getText().toString().matches("")||texture_mean.getText().toString().matches("")||perimeter_mean.getText().toString().matches("")||area_mean.getText().toString().matches("")||smoothness_mean.getText().toString().matches("")||compactness_mean.getText().toString().matches("")||concavity_mean.getText().toString().matches("")||concavepoints_mean.getText().toString().matches("")||symmetry_mean.getText().toString().matches("")||fractal_dimension_mean.getText().toString().matches("")||radius_se.getText().toString().matches("")||texture_se.getText().toString().matches("")||perimeter_se.getText().toString().matches("")||area_se.getText().toString().matches("")||smoothness_se.getText().toString().matches("")||compactness_se.getText().toString().matches("")||concavity_se.getText().toString().matches("")||concavepoints_se.getText().toString().matches("")||symmetry_se.getText().toString().matches("")||fractal_dimension_se.getText().toString().matches("")||radius_worst.getText().toString().matches("")||texture_worst.getText().toString().matches("")||perimeter_worst.getText().toString().matches("")||area_worst.getText().toString().matches("")||smoothness_worst.getText().toString().matches("")||compactness_worst.getText().toString().matches("")||concavity_worst.getText().toString().matches("")||concavepoints_worst.getText().toString().matches("")||symmetry_worst.getText().toString().matches("")||fractal_dimension_worst.getText().toString().matches("")) {
                    Toast.makeText(CancerActivity.this,"Please fill in required fields !",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {

                    float f = inferance(radius_mean.getText().toString(),texture_mean.getText().toString(),perimeter_mean.getText().toString(),area_mean.getText().toString(),smoothness_mean.getText().toString(),compactness_mean.getText().toString(),concavity_mean.getText().toString(),concavepoints_mean.getText().toString(),symmetry_mean.getText().toString(),fractal_dimension_mean.getText().toString(),radius_se.getText().toString(),texture_se.getText().toString(),perimeter_se.getText().toString(),area_se.getText().toString(),smoothness_se.getText().toString(),compactness_se.getText().toString(),concavity_se.getText().toString(),concavepoints_se.getText().toString(),symmetry_se.getText().toString(),fractal_dimension_se.getText().toString(),radius_worst.getText().toString(),texture_worst.getText().toString(),perimeter_worst.getText().toString(),area_worst.getText().toString(),smoothness_worst.getText().toString(),compactness_worst.getText().toString(),concavity_worst.getText().toString(),concavepoints_worst.getText().toString(),symmetry_worst.getText().toString(),fractal_dimension_worst.getText().toString());
                    f= (float) (Math.round(f*100.00)/100.00);
                    if (f>=1){
                       f=(float) 89.49;
                    }
                    result.setText("Probability that you are diabetic is:"+ f + "%");

                    if (f > 0.5) {
                        result.setTextColor(getResources().getColor(R.color.design_default_color_error));

                    } else {

                        result.setTextColor(getResources().getColor(R.color.purple_700));
                    }
                }

            }
        });

    }
    public float inferance(String radius_mean,String texture_mean,String perimeter_mean,String area_mean,String smoothness_mean,String compactness_mean,String concavity_mean,String concavepoints_mean,String symmetry_mean,String fractal_dimension_mean,String radius_se,String texture_se,String perimeter_se,String area_se,String smoothness_se,String compactness_se,String concavity_se,String concavepoints_se,String symmetry_se,String fractal_dimension_se,String radius_worst,String texture_worst,String perimeter_worst,String area_worst,String smoothness_worst,String compactness_worst,String concavity_worst,String concavepoints_worst,String symmetry_worst,String fractal_dimension_worst ){
        float[][] input = new float[1][30];
        input[0][0]=Float.parseFloat(radius_mean);
        input[0][1]=Float.parseFloat(texture_mean);
        input[0][2]=Float.parseFloat(perimeter_mean);
        input[0][3]=Float.parseFloat(area_mean);
        input[0][4]=Float.parseFloat(smoothness_mean);
        input[0][5]=Float.parseFloat(compactness_mean);
        input[0][6]=Float.parseFloat(concavity_mean);
        input[0][7]=Float.parseFloat(concavepoints_mean);
        input[0][8]=Float.parseFloat(symmetry_mean);
        input[0][9]=Float.parseFloat(fractal_dimension_mean);
        input[0][10]=Float.parseFloat(radius_se);
        input[0][11]=Float.parseFloat(texture_se);
        input[0][12]=Float.parseFloat(perimeter_se);
        input[0][13]=Float.parseFloat(area_se);
        input[0][14]=Float.parseFloat(smoothness_se);
        input[0][15]=Float.parseFloat(compactness_se);
        input[0][16]=Float.parseFloat(concavity_se);
        input[0][17]=Float.parseFloat(concavepoints_se);
        input[0][18]=Float.parseFloat(symmetry_se);
        input[0][19]=Float.parseFloat(fractal_dimension_se);
        input[0][20]=Float.parseFloat(radius_worst);
        input[0][21]=Float.parseFloat(texture_worst);
        input[0][22]=Float.parseFloat(perimeter_worst);
        input[0][23]=Float.parseFloat(area_worst);
        input[0][24]=Float.parseFloat(smoothness_worst);
        input[0][25]=Float.parseFloat(compactness_worst);
        input[0][26]=Float.parseFloat(concavity_worst);
        input[0][27]=Float.parseFloat(concavepoints_worst);
        input[0][28]=Float.parseFloat(symmetry_worst);
        input[0][29]=Float.parseFloat(fractal_dimension_worst);

        float[][] output = new float[1][1];
        interpreter.run(input,output);
        return output[0][0];
    }

    private MappedByteBuffer loadModelfile() throws IOException {
        AssetFileDescriptor assetFileDescriptor =  this.getAssets().openFd("cancer.tflite");
        FileInputStream fileInputStream = new FileInputStream(assetFileDescriptor.getFileDescriptor());
        FileChannel fileChannel = fileInputStream.getChannel();
        long startOffset = assetFileDescriptor.getStartOffset();
        long length = assetFileDescriptor.getLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,length);

    }
}