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

public class DiabetesActivity extends AppCompatActivity {

    private Button resetButton,submitButton;
    private EditText preg,glucose,bp,st,insulin,bmi,dpf,age;
    private TextView result;
    Interpreter interpreter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diabetes);

        resetButton=findViewById(R.id.resetButton);
        submitButton=findViewById(R.id.submitButton);
        preg=findViewById(R.id.pregnancies);
        glucose=findViewById(R.id.glucose);
        bp=findViewById(R.id.bloodpressure);
        st=findViewById(R.id.skinthickness);
        insulin=findViewById(R.id.insulin);
        bmi=findViewById(R.id.bmi);
        dpf=findViewById(R.id.dpf);
        age=findViewById(R.id.age);
        result=findViewById(R.id.result);


        try{
            interpreter=new Interpreter(loadModelfile());

        }catch (Exception e){
            e.printStackTrace();
        }




        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //Reset button
                preg.getText().clear();
                glucose.getText().clear();
                bp.getText().clear();
                st.getText().clear();
                insulin.getText().clear();
                bmi.getText().clear();
                dpf.getText().clear();
                age.getText().clear();
                result.setText("Result will appear here!");
            }
        });


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //Submit button

                if (preg.getText().toString().matches("")||glucose.getText().toString().matches("")||bp.getText().toString().matches("")||st.getText().toString().matches("")||insulin.getText().toString().matches("")||bmi.getText().toString().matches("")||dpf.getText().toString().matches("")||age.getText().toString().matches("")) {
                    Toast.makeText(DiabetesActivity.this,"Please fill in required fields !",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {

                    float f = inferance(preg.getText().toString(), glucose.getText().toString(), bp.getText().toString(), st.getText().toString(), insulin.getText().toString(), bmi.getText().toString(), dpf.getText().toString(), age.getText().toString());
                    result.setText("Probability that you are diabetic is: "+ f + "%");

                    if (f > 0.5) {
                        result.setTextColor(getResources().getColor(R.color.design_default_color_error));

                    } else {

                        result.setTextColor(getResources().getColor(R.color.purple_700));
                    }
                }

            }
        });

    }
    public float inferance(String preg,String glucose,String bp,String st,String insulin,String bmi,String dpf,String age ){
        float[][] input = new float[1][8];
        input[0][0] = Float.parseFloat(preg);
        input[0][1] = Float.parseFloat(glucose);
        input[0][2] = Float.parseFloat(bp);
        input[0][3] = Float.parseFloat(st);
        input[0][4] = Float.parseFloat(insulin);
        input[0][5] = Float.parseFloat(bmi);
        input[0][6] = Float.parseFloat(dpf);
        input[0][7] = Float.parseFloat(age);

        float[][] output = new float[1][1];
        interpreter.run(input,output);
        return output[0][0];
    }

    private MappedByteBuffer loadModelfile() throws IOException{
        AssetFileDescriptor assetFileDescriptor =  this.getAssets().openFd("diabetes.tflite");
        FileInputStream fileInputStream = new FileInputStream(assetFileDescriptor.getFileDescriptor());
        FileChannel fileChannel = fileInputStream.getChannel();
        long startOffset = assetFileDescriptor.getStartOffset();
        long length = assetFileDescriptor.getLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,length);

    }
}