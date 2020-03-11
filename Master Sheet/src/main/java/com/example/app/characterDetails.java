package com.example.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.app.ViewModel.CharacterDetailsModel;
import com.example.app.db.dbClasses.Characters;
import com.example.app.db.dbClasses.Inventory;
import com.example.app.db.dbClasses.Skill;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.app.Utilities.Constants.CHARACTER_ID_KEY;

public class characterDetails extends AppCompatActivity {
    private final Context context = this;
    private CharacterDetailsModel viewModel;
    private int characterId = 0;
    private List<Skill> starredSkills = new ArrayList<Skill>();
    private List<Inventory> starredInventory = new ArrayList<Inventory>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(CharacterDetailsModel.class);
        setContentView(R.layout.character_details);
        Bundle intent = getIntent().getExtras();
        characterId = intent.getInt(CHARACTER_ID_KEY);
        Log.d("TAG", Integer.toString(characterId));
        if(characterId == -1){
            viewModel.loadData(characterId);
            Log.d("TAG", Integer.toString(characterId));
        }else{
            viewModel.loadData(characterId);
            final EditText characterName = findViewById(R.id.characterName);
            final EditText race = findViewById(R.id.race);
            final EditText build = findViewById(R.id.build);

            final EditText strength = findViewById(R.id.strength);
            final EditText agility = findViewById(R.id.agility);
            final EditText dexterity = findViewById(R.id.dexterity);
            final EditText constitution = findViewById(R.id.constitution);
            final EditText intelligence = findViewById(R.id.intelligence);
            final EditText will = findViewById(R.id.will);
            final EditText charisma = findViewById(R.id.charisma);

            final EditText gold = findViewById(R.id.gold);

            final EditText headHealth = findViewById(R.id.headHealth);
            final EditText torsoHealth = findViewById(R.id.torsoHealth);
            final EditText leftArmHealth = findViewById(R.id.leftArmHealth);
            final EditText rightArmHealth = findViewById(R.id.rightArmHealth);
            final EditText leftLegHealth = findViewById(R.id.leftLegHealth);
            final EditText rightLegHealth = findViewById(R.id.rightLegHealth);

            final EditText headArmor = findViewById(R.id.headArmor);
            final EditText torsoArmor = findViewById(R.id.torsoArmor);
            final EditText leftArmArmor = findViewById(R.id.leftArmArmor);
            final EditText rightArmArmor = findViewById(R.id.rightArmArmor);
            final EditText leftLegArmor = findViewById(R.id.leftLegArmor);
            final EditText rightLegArmor = findViewById(R.id.rightLegArmor);

            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    characterName.setText(viewModel.character.getValue().getName());
                    race.setText(viewModel.character.getValue().getRace());
                    build.setText(viewModel.character.getValue().getBuild());

                    strength.setText(Double.toString(viewModel.character.getValue().getStrength()));
                    agility.setText(Double.toString(viewModel.character.getValue().getAgility()));
                    dexterity.setText(Double.toString(viewModel.character.getValue().getDexterity()));
                    constitution.setText(Double.toString(viewModel.character.getValue().getConstitution()));
                    intelligence.setText(Double.toString(viewModel.character.getValue().getIntelligence()));
                    will.setText(Double.toString(viewModel.character.getValue().getWill()));
                    charisma.setText(Double.toString(viewModel.character.getValue().getCharisma()));
                    gold.setText(Double.toString(viewModel.character.getValue().getGold()));

                    int flatConstitution = 0;
                    for (int i = 10; i <= viewModel.character.getValue().getConstitution(); ) {
                        if ((i + 10) > ((int)viewModel.character.getValue().getConstitution())) {
                            flatConstitution = i;
                            Log.d("assigned", "assigning flat constitution");
                            i=i+10;
                        }else{
                            i = i + 10;
                            flatConstitution=i;
                        }
                        Log.d("I:", Integer.toString(i));
                        Log.d("Constitution", Double.toString(viewModel.character.getValue().getConstitution()));
                    }
                    Log.d("flatConstitution", Integer.toString(flatConstitution));

                    TextView maxHead = findViewById(R.id.maxHeadHealth);
                    TextView maxTorso = findViewById(R.id.maxTorsoHealth);
                    TextView maxLeftArm = findViewById(R.id.maxLeftArm);
                    TextView maxRightArm = findViewById(R.id.maxRightArm);
                    TextView maxLeftLeg = findViewById(R.id.maxLeftLeg);
                    TextView maxRightLeg = findViewById(R.id.maxRightLeg);

                    int arms;
                    int legs;
                    if (flatConstitution * .15 != (int)(flatConstitution * .15)) {
                        arms = (int)(((double)flatConstitution * .15) - 1);
                        legs = (int)((double)flatConstitution * .15);
                    } else {
                        arms = (int)((double)flatConstitution * .15);
                        legs = (int)((double)flatConstitution * .15);
                    }
                    Log.d("head health double", Double.toString((double)flatConstitution*.1));
                    Log.d("head health", Integer.toString((int)((double)flatConstitution * .1)));

                    maxHead.setText(Integer.toString((int)((double)flatConstitution * .1)));
                    maxTorso.setText(Integer.toString((int)((double)flatConstitution * .3)));
                    maxLeftArm.setText(Integer.toString(arms));
                    maxRightArm.setText(Integer.toString(arms));
                    maxLeftLeg.setText(Integer.toString(legs));
                    maxRightLeg.setText(Integer.toString(legs));

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
                    initViewModel();
                }
            }, 50);
        }
        FloatingActionButton addSkill = findViewById(R.id.addSkill);
        addSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SkillList.class);
                intent.putExtra(CHARACTER_ID_KEY, characterId);
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
                intent.putExtra(CHARACTER_ID_KEY, characterId);
                try{
                    context.startActivity(intent);
                }
                catch(Exception e){
                    Log.d("Except", e.toString());
                }
            }
        });
        FloatingActionButton save = findViewById(R.id.saveCharacter);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saveCharacter()){
                    finish();
                }

            }
        });
    }

    public boolean saveCharacter(){
        EditText name = findViewById(R.id.characterName);
        EditText race = findViewById(R.id.race);
        EditText build = findViewById(R.id.build);

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

        double str = Double.parseDouble(strength.getText().toString());
        double agi = Double.parseDouble(agility.getText().toString());
        double dex = Double.parseDouble(dexterity.getText().toString());
        double con = Double.parseDouble(constitution.getText().toString());
        double intel = Double.parseDouble(intelligence.getText().toString());

        int hdam = Integer.parseInt(headHealth.getText().toString());
        int tdam = Integer.parseInt(torsoHealth.getText().toString());
        int ladam = Integer.parseInt(leftArmHealth.getText().toString());
        int radam = Integer.parseInt(rightArmHealth.getText().toString());
        int lldam = Integer.parseInt(leftLegHealth.getText().toString());
        int rldam = Integer.parseInt(rightLegHealth.getText().toString());

        int har = Integer.parseInt(headArmor.getText().toString());
        int tar = Integer.parseInt(torsoArmor.getText().toString());
        int lar = Integer.parseInt(leftArmArmor.getText().toString());
        int rar = Integer.parseInt(rightArmArmor.getText().toString());
        int rlar = Integer.parseInt(leftLegArmor.getText().toString());
        int llar = Integer.parseInt(rightLegArmor.getText().toString());

        Characters character = new Characters(
                characterId, name.getText().toString(), race.getText().toString(),
                build.getText().toString(), str, dex, agi, intel,
                Double.parseDouble(will.getText().toString()), con,
                Double.parseDouble(charisma.getText().toString()), hdam, tdam, ladam,
                radam, rldam, lldam, har, tar, rar, lar, rlar, llar,
                Double.parseDouble(gold.getText().toString()));
        viewModel.saveCharacter(character);
        return true;
    }

    public void initViewModel(){
        final Observer<List<Skill>> skillObserver = new Observer<List<Skill>>() {
            @Override
            public void onChanged(@Nullable List<Skill> skills){
                if(starredSkills.size()!=0) {
                    starredSkills.clear();
                }
                starredSkills.addAll(skills);
                if(starredInventory != null){
                    for (int i = 0; i<starredInventory.size(); i++){
                        insertRow(starredInventory.get(i));
                    }
                }
                if(starredSkills != null){
                    for(int i=0; i<starredSkills.size(); i++){
                        insertRow(starredSkills.get(i));
                    }
                }
            }
        };
        final Observer<List<Inventory>> inventoryObserver = new Observer<List<Inventory>>(){
            @Override
            public void onChanged(@Nullable List<Inventory> inventory) {
                if(starredInventory.size() != 0) {
                    starredInventory.clear();
                }
                starredInventory.addAll(inventory);
                if (starredInventory != null) {
                    for (int i = 0; i < starredInventory.size(); i++) {
                        insertRow(starredInventory.get(i));
                    }
                }
                if (starredSkills != null) {
                    for (int i = 0; i < starredSkills.size(); i++) {
                        insertRow(starredSkills.get(i));
                    }
                }
            }
        };
        viewModel.starredSkills.observe(this, skillObserver);
        viewModel.starredInventory.observe(this, inventoryObserver);
    }

    public void insertRow(final Inventory inventory){
        LinearLayout contain = findViewById(R.id.rowContainer);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View newRow = inflater.inflate(R.layout.favorite_cards, null);
        TextView nameHeading = newRow.findViewById(R.id.nameHeading);
        nameHeading.setText("Item Name");
        TextView name = newRow.findViewById(R.id.name);
        name.setText(inventory.getName());
        TextView skill = newRow.findViewById(R.id.dice);
        skill.setText(Integer.toString(viewModel.getDice(inventory.getSkillName(), characterId)));
        final TextView train = newRow.findViewById(R.id.train);
        train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.train(inventory.getSkillName(), characterId);
            }
        });
    }

    public void insertRow(final Skill addSkill){
        LinearLayout contain = findViewById(R.id.rowContainer);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View newRow = inflater.inflate(R.layout.favorite_cards, null);
        TextView nameHeading = newRow.findViewById(R.id.nameHeading);
        nameHeading.setText("Skill Name");
        TextView name = newRow.findViewById(R.id.name);
        name.setText(addSkill.getName());
        TextView rank = newRow.findViewById(R.id.dice);
        rank.setText(Integer.toString(viewModel.getDice(addSkill)));
        final FloatingActionButton train = newRow.findViewById(R.id.train);
        train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewModel.train(addSkill);
            }
        });
        newRow.setTag(addSkill.getName());
        View exists = contain.findViewWithTag(addSkill.getName());
        if(exists == null){
            contain.addView(newRow);
        }else{
            ((ViewGroup) exists.getParent()).removeView(exists);
            contain.addView(newRow);
        }
    }
}
