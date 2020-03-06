package com.example.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.app.ViewModel.CharacterDetailsModel;
import com.example.app.db.dbClasses.Inventory;
import com.example.app.db.dbClasses.Skill;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.example.app.Utilities.Constants.CHARACTER_ID_KEY;

public class characterDetails extends AppCompatActivity {
    private final Context context = this;
    private CharacterDetailsModel viewModel;
    private int characterId = 0;
    private List<Skill> starredSkills;
    private List<Inventory> starredInventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(CharacterDetailsModel.class);
        setContentView(R.layout.character_details);
        Bundle intent = getIntent().getExtras();
        characterId = intent.getInt(CHARACTER_ID_KEY);
        if(characterId == -1){
            viewModel.loadData(characterId);
        }else{
            viewModel.loadData(characterId);
            EditText strength = findViewById(R.id.strength);
            EditText agility = findViewById(R.id.agility);
            EditText dexterity = findViewById(R.id.dexterity);
            EditText constitution = findViewById(R.id.constitution);
            EditText intelligence = findViewById(R.id.intelligence);
            EditText will = findViewById(R.id.will);
            EditText charisma = findViewById(R.id.charisma);

            EditText gold = findViewById(R.id.gold);

            EditText headHealth = findViewById(R.id.headHealth);
            EditText torsoHealth = findViewById(R.id.torsoHealth);
            EditText leftArmHealth = findViewById(R.id.leftArmHealth);
            EditText rightArmHealth = findViewById(R.id.rightArmHealth);
            EditText leftLegHealth = findViewById(R.id.leftLegHealth);
            EditText rightLegHealth = findViewById(R.id.rightLegHealth);

            EditText headArmor = findViewById(R.id.headArmor);
            EditText torsoArmor = findViewById(R.id.torsoArmor);
            EditText leftArmArmor = findViewById(R.id.leftArmArmor);
            EditText rightArmArmor = findViewById(R.id.rightArmArmor);
            EditText leftLegArmor = findViewById(R.id.leftLegArmor);
            EditText rightLegArmor = findViewById(R.id.rightLegArmor);

            strength.setText(Double.toString(viewModel.character.getValue().getStrength()));
            agility.setText(Double.toString(viewModel.character.getValue().getAgility()));
            dexterity.setText(Double.toString(viewModel.character.getValue().getDexterity()));
            constitution.setText(Double.toString(viewModel.character.getValue().getConstitution()));
            intelligence.setText(Double.toString(viewModel.character.getValue().getIntelligence()));
            will.setText(Double.toString(viewModel.character.getValue().getWill()));
            charisma.setText(Double.toString(viewModel.character.getValue().getCharisma()));
            gold.setText(Double.toString(viewModel.character.getValue().getGold()));

            headHealth.setText(Integer.toString(viewModel.character.getValue().getHeadDamage()));
            torsoHealth.setText(Integer.toString(viewModel.character.getValue().getTorsoDamage()));
            leftArmHealth.setText(Integer.toString(viewModel.character.getValue().getLeftArmDamage()));
            rightArmHealth.setText(Integer.toString(viewModel.character.getValue().getRightArmDamage()));
            leftLegHealth.setText(Integer.toString(viewModel.character.getValue().getLeftLegDamage()));
            rightLegHealth.setText(Integer.toString(viewModel.character.getValue().getRightLegDamage()));

            headArmor.setText(Integer.toString(viewModel.character.getValue().getHeadArmor()));
            torsoArmor.setText(Integer.toString(viewModel.character.getValue().getTorsoArmor()));
            leftArmArmor.setText(Integer.toString(viewModel.character.getValue().getLeftArmArmor()));
            rightArmArmor.setText(Integer.toString(viewModel.character.getValue().getRightArmArmor()));
            leftLegArmor.setText(Integer.toString(viewModel.character.getValue().getLeftLegArmor()));
            rightLegArmor.setText(Integer.toString(viewModel.character.getValue().getRightLegArmor()));
        }
        initViewModel();
        FloatingActionButton addSkill = findViewById(R.id.addSkill);
        addSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, skillList.class);
                try{
                    context.startActivity(intent);
                }
                catch(Exception e){
                    Log.d("Except", e.toString());
                }
            }
        });
        FloatingActionButton addInventory = findViewById(R.id.addInventory);
        addInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, inventoryList.class);
                try{
                    context.startActivity(intent);
                }
                catch(Exception e){
                    Log.d("Except", e.toString());
                }
            }
        });
        FloatingActionButton save = findViewById(R.id.addInventory);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCharacter();
                Intent intent = new Intent(context, MainActivity.class);
                try{
                    context.startActivity(intent);
                }
                catch(Exception e){
                    Log.d("Except", e.toString());
                }
            }
        });
    }

    public boolean saveCharacter(){

        return true;
    }

    public void initViewModel(){
        final Observer<List<Skill>> skillObserver = new Observer<List<Skill>>() {
            @Override
            public void onChanged(@Nullable List<Skill> skills){
                starredSkills.clear();
                starredSkills.addAll(skills);
                if(starredInventory != null){
                    for (int i = 0; i<starredInventory.size(); i++){
                        insertInventoryRow(starredInventory.get(i));
                    }
                }
                if(starredSkills != null){
                    for(int i=0; i<starredSkills.size(); i++){
                        insertSkillRow(starredSkills.get(i));
                    }
                }
            }
        };
        final Observer<List<Inventory>> inventoryObserver = new Observer<List<Inventory>>(){
            @Override
            public void onChanged(@Nullable List<Inventory> inventory) {
                starredInventory.clear();
                starredInventory.addAll(inventory);
                if (starredInventory != null) {
                    for (int i = 0; i < starredInventory.size(); i++) {
                        insertInventoryRow(starredInventory.get(i));
                    }
                }
                if (starredSkills != null) {
                    for (int i = 0; i < starredSkills.size(); i++) {
                        insertSkillRow(starredSkills.get(i));
                    }
                }
            }
        };
    }

    public void insertInventoryRow(Inventory inventory){
        LinearLayout contain = findViewById(R.id.rowContainer);
    }

    public void insertSkillRow(Skill skill){

    }
}
