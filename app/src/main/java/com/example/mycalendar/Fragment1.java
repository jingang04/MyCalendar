package com.example.mycalendar;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class Fragment1 extends Fragment {
    //변수 선언
    String fileName;
    Button btn_write;
    EditText editDiary;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_1, container, false);
        DatePicker dp = view.findViewById(R.id.datePicker);
        editDiary = view.findViewById(R.id.deitDiary);
        btn_write = view.findViewById(R.id.btn_write);


        int Year = dp.getYear();
        int Month = dp.getMonth();
        int day = dp.getDayOfMonth();

        Log.d("DATE", Year + Month + day + "");

        fileName = Year + "_" + (Month + 1) + "_" + day + ".txt";

        String str = readDiary(fileName);
        editDiary.setText(str);

        dp.init(Year, Month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Log.d("CLICK:", year + "_" + (monthOfYear+1) + "_" + dayOfMonth);
                fileName = year + "_" + (monthOfYear+1) + "_" + dayOfMonth + ".txt";

                String str = readDiary(fileName);

            }
        });

        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileOutputStream outfS = getContext().openFileOutput(fileName, Context.MODE_PRIVATE);
                    String str = editDiary.getText().toString();
                    outfS.write(str.getBytes());
                    outfS.close();
                    Toast.makeText(getContext(), fileName + "파일을 저장 했습니다.", Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


        return view;
    }

    private String readDiary(String fileName) {
        String diaryStr = null;
        //파일처리
        try {
            FileInputStream infS = getContext().openFileInput(fileName);
            byte[] txt = new byte[500];
            infS.read(txt);
            infS.close();

            diaryStr = new String(txt);
        } catch (IOException e) {
            editDiary.setText("일기 없음");
        }


        return diaryStr;
    }
}