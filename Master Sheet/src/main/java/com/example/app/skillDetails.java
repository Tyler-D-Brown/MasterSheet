package com.example.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
        Bundle intent = getIntent().getExtras();
        characterId = intent.getInt(CHARACTER_ID_KEY);
        skillId = intent.getString(SKILL_ID_KEY);
        initViewModel();
    }

    public void initViewModel(){
        viewModel = ViewModelProviders.of(this).get(SkillDetailViewModel.class);
        final Spinner attribute = (Spinner) findViewById(R.id.governingAttribute);
        attribute.setOnItemSelectedListener(this);
        final List<String> attributesList = new ArrayList<String>();
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
                if(saveSkill()){
                    finish();
                }
            }
        });
        FloatingActionButton delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.deleteSkill(skillId);
                finish();
            }
        });
        if(!skillId.equals("")){
            viewModel.getSkill(skillId, characterId);
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (attributesList.indexOf(viewModel.skill.getValue().getAttribute()) != -1) {
                        attribute.setSelection(attributesList.indexOf(viewModel.skill.getValue().getAttribute()));
                    }
                    EditText name = (EditText) findViewById(R.id.skillName);
                    EditText rank = (EditText) findViewById(R.id.rank);
                    EditText description = (EditText) findViewById(R.id.description);
                    CheckBox favorited = (CheckBox) findViewById(R.id.favorite);
                    name.setText(viewModel.skill.getValue().getName());
                    rank.setText(Double.toString(viewModel.skill.getValue().getRank()));
                    description.setText(viewModel.skill.getValue().getDescription());
                    if (viewModel.skill.getValue().isStarred()) {
                        favorited.setChecked(true);
                    }
                }
            }, 50);

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        attribute = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public boolean saveSkill(){
        CheckBox favorited = findViewById(R.id.favorite);
        boolean favorite;
        favorite = favorited.isChecked();
        EditText name = (EditText) findViewById(R.id.skillName);
        EditText rank = (EditText) findViewById(R.id.rank);
        EditText description = (EditText) findViewById(R.id.description);

        Skill newSkill = null;
        try{
            newSkill = new Skill(characterId, name.getText().toString(),
                    Double.parseDouble(rank.getText().toString()), description.getText().toString(),
                    attribute.toString(), favorite);
        }catch (Exception e){
            Log.e("Invalid input", "saveSkill: ", e);
            Toast.makeText(context, "Please make sure every field is filled.", Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            viewModel.saveSkill(newSkill);
        }catch (Exception e){
            Log.e("Invalid input", "saveSkill: ", e);
            Toast.makeText(context, "Please make sure all fields are filled out.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
