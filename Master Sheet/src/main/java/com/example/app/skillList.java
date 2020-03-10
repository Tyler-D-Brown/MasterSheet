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
import androidx.lifecycle.ViewModelProviders;

import com.example.app.ViewModel.MainViewModel;
import com.example.app.ViewModel.SkillListViewModel;
import com.example.app.db.dbClasses.Characters;
import com.example.app.db.dbClasses.Skill;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.example.app.Utilities.Constants.CHARACTER_ID_KEY;
import static com.example.app.Utilities.Constants.SKILL_ID_KEY;

public class skillList extends AppCompatActivity {
    private final Context context = this;
    private SkillListViewModel viewModel;
    private List<Skill> skillList = new ArrayList<Skill>();
    int characterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchable_list);
        initViewModel();
        final TextView heading = findViewById(R.id.listHeading);
        Bundle intent = getIntent().getExtras();
        characterId = intent.getInt(CHARACTER_ID_KEY);
        viewModel.getCharacterName(characterId);
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
                          @Override
                          public void run() {
                                      heading.setText(viewModel.characterName.getValue().concat("'s Skills"));
                                  }
                      }, 50);
        FloatingActionButton fab = findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, skillDetails.class);
            intent.putExtra(CHARACTER_ID_KEY, characterId);
            int id = -1;
            intent.putExtra(SKILL_ID_KEY, id);
            try{
                context.startActivity(intent);
            }
            catch(Exception e){
                Log.d("Except", e.toString());
            }
        }
    });
}

    private void initViewModel() {
        Log.d("TAG", "initViewModel: ");
        viewModel = ViewModelProviders.of(this).get(SkillListViewModel.class);
        final Observer<List<Skill>> characterObserver = new Observer<List<Skill>>() {
            @Override
            public void onChanged(@Nullable List<Skill> skills){
                if(skillList.size() != 0) {
                    skillList.clear();
                }
                if(skills.size() != 0) {
                    Log.d("TAG", Integer.toString(skills.size()));
                    skillList.addAll(skills);
                }
                if(skillList != null){
                    for(int i = 0; i < skillList.size(); i++){
                        insertRow(skillList.get(i));
                    }
                }
            }
        };
        viewModel.repository.skills.observe(this, characterObserver);
    }

    private void insertRow(final Skill add){
        final LinearLayout layout = findViewById(R.id.rowContainer);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View newSkillRow = inflater.inflate(R.layout.cards, null);
        Button button = newSkillRow.findViewById(R.id.title);
        button.setText(add.getName());
        EditText dice = newSkillRow.findViewById(R.id.dice);
        dice.setText(viewModel.getDice(add));
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View button){
                Intent intent = new Intent(getBaseContext(), skillDetails.class);
                intent.putExtra(SKILL_ID_KEY, add.getName());
                try{
                    context.startActivity(intent);
                }
                catch(Exception e){
                    Log.d("Except", e.toString());
                }
            }
        });
        FloatingActionButton train = newSkillRow.findViewById(R.id.train);
        train.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View Button){
                viewModel.repository.trainSkill(add.getName(), add.getCharacter());
                initViewModel();
            }
        });
        newSkillRow.setTag(add.getName());
        View exists = layout.findViewWithTag(add.getName());
        if(exists == null){
            layout.addView(newSkillRow);
        }else{
            ((ViewGroup) exists.getParent()).removeView(exists);
            layout.addView(newSkillRow);
        }
    }
}
