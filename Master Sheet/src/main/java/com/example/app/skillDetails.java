package com.example.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.app.ViewModel.SkillDetailViewModel;
import com.example.app.ViewModel.SkillListViewModel;
import com.example.app.db.dbClasses.Skill;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.app.Utilities.Constants.CHARACTER_ID_KEY;
import static com.example.app.Utilities.Constants.SKILL_ID_KEY;

public class skillDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private final Context context = this;
    private int characterId = 0;
    private String skillId = "";
    private SkillDetailViewModel viewModel;
    private String attribute = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skill_details);
        initViewModel();
        Bundle intent = getIntent().getExtras();
        characterId = intent.getInt(CHARACTER_ID_KEY);
        skillId = intent.getString(SKILL_ID_KEY);
    }

    public void initViewModel(){
        viewModel = ViewModelProviders.of(this).get(SkillDetailViewModel.class);
        Spinner attribute = (Spinner) findViewById(R.id.governingAttribute);
        attribute.setOnItemSelectedListener(this);
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
        attribute.setAdapter(attributeAdapter);
        FloatingActionButton save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSkill();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        attribute = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void saveSkill(){
        CheckBox favorited = findViewById(R.id.favorite);
        boolean favorite;
        favorite = favorited.isChecked();
        EditText name = (EditText) findViewById(R.id.skillName);
        EditText rank = (EditText) findViewById(R.id.rank);
        EditText description = (EditText) findViewById(R.id.description);

        Log.d("Governing Attribute", attribute);

        Skill newSkill = new Skill(characterId, name.getText().toString(),
                Double.parseDouble(rank.getText().toString()), description.getText().toString(),
                attribute.toString(), favorite);

        Log.d("Skill Name", newSkill.getName());
        viewModel.saveSkill(newSkill);
    }
}
