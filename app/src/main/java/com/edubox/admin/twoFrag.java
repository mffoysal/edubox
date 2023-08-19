package com.edubox.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class twoFrag extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, SeekBar.OnSeekBarChangeListener {

    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4;
    private RadioButton radioButton1, radioButton2, radioButton3;
    private RadioGroup radioGroup1;
    private Button btn1, btn2;
    private TextView t1, t2;
    private Switch aSwitch1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two, container, false);

        checkBox1 = view.findViewById(R.id.ch1);
        checkBox2 = view.findViewById(R.id.ch2);
        checkBox3 = view.findViewById(R.id.ch3);
        checkBox4 = view.findViewById(R.id.ch4);
        radioGroup1 = view.findViewById(R.id.radioGroup1);
        radioButton1 = view.findViewById(R.id.maleRadioId);
        radioButton2 = view.findViewById(R.id.femaleRadioId);
        btn1 = view.findViewById(R.id.showBtn);
        btn2 = view.findViewById(R.id.showBtn2);
        t1 = view.findViewById(R.id.textCheckId);
        aSwitch1 = view.findViewById(R.id.swith1Id);

        aSwitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    Intent intent = new Intent(getContext(),ScannerActivity.class);
                    startActivity(intent);
                    Toast.makeText(getContext(),"ON",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(),"OFF",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.showBtn) {
            StringBuilder stringBuilder = new StringBuilder();
            if (checkBox1.isChecked()) {
                stringBuilder.append("checked : " + checkBox1.getText() + " \n");
            }
            if (checkBox2.isChecked()) {
                stringBuilder.append("checked : " + checkBox2.getText() + " \n");
            }
            if (checkBox3.isChecked()) {
                stringBuilder.append("checked : " + checkBox3.getText() + " \n");
            }
            if (checkBox4.isChecked()) {
                stringBuilder.append("checked : " + checkBox4.getText() + " \n");
            }
            t1.setText("" + stringBuilder);
        } else if (view.getId()==R.id.showBtn2) {

            Intent intent = new Intent(getContext(),ScannerActivity.class);
            startActivity(intent);
            try {

                int selectedId = radioGroup1.getCheckedRadioButtonId();
                radioButton3 = (RadioButton) view.findViewById(selectedId);

                String value = radioButton3.getText().toString();
                t1.setText("You have selected : "+value);
            }catch(Exception e){
                Toast.makeText(getContext(),"Please Select any Radio",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}