package com.example.externalstorageapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    Button save, retrieve;
    EditText input;
    TextView output;

    private String filename = "SampleFile.txt";
    private String filepath = "MyFileStorage";
    File myExternalFile;
    String myData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        save = findViewById(R.id.save);
        retrieve = findViewById(R.id.retrieve);
        input = findViewById(R.id.inputString);
        output = findViewById(R.id.result);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileOutputStream fos = new FileOutputStream(myExternalFile);
                    fos.write(input.getText().toString().getBytes());
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                input.setText("");
                output.setText("SampleFile.txt saved to External Storage...");
            }
        });
        retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileInputStream fis = new FileInputStream(myExternalFile);
                    DataInputStream in = new DataInputStream(fis);
                    BufferedReader br =
                            new BufferedReader(new InputStreamReader(in));
                    String strLine;
                    while ((strLine = br.readLine()) != null) {
                        myData = myData + strLine;
                    }
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String lowerCaseResult = myData.toLowerCase();
                char[] dataArray = lowerCaseResult.toCharArray();
                int vowel = 0;
                int consonant = 0;
                for(int i = 0; i < dataArray.length; i++) {
                    if (dataArray[i] == 'a' || dataArray[i] == 'e' || dataArray[i] == 'i' || dataArray[i] == 'o' || dataArray[i] == 'u') {
                        vowel = vowel + 1;
                    } else {
                        consonant = consonant + 1;
                    }
                }
                output.setText("Vowels = " + vowel + " and " + "consonant = " + consonant);
            }
        });
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            save.setEnabled(false);
        }
        else {
            myExternalFile = new File(getExternalFilesDir(filepath), filename);
        }
    }
    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

}