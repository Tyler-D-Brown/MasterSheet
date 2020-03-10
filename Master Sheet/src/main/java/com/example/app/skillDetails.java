package com.example.app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class skillDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchable_list);
        initViewModel();
    }

    public void initViewModel(){
        Spinner attribute = (Spinner) findViewById(R.id.governingAttribute);

        List<String> attributesList = new ArrayList<String>();
        attributesList.add("Strength");
        attributesList.add("Dexterity");
        attributesList.add("Agility");
        attributesList.add("Constitution");
        attributesList.add("Intelligence");
        attributesList.add("Will");
        attributesList.add("Charisma");
        ArrayAdapter<String> attributeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, attributesList);
        attributeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Log.d("TAG", (String) attributeAdapter[0]);
        attribute.setAdapter(attributeAdapter);
    }

    public void onItemSelectedListener(AdapterView<?> parent, View view, int position, long id){
        String item = parent.getItemAtPosition(position).toString();
    }
}
