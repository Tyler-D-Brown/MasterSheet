package com.example.app.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.app.db.AppRepository;
import com.example.app.db.dbClasses.Characters;
import com.example.app.db.dbClasses.Skill;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SkillListViewModel extends AndroidViewModel {
    public AppRepository repository;
    public MutableLiveData<String> characterName = new MutableLiveData<>();
    private Executor executor = Executors.newSingleThreadExecutor();

    public SkillListViewModel(@NonNull Application application){
        super(application);
        repository = AppRepository.getInstance(application.getApplicationContext());
    }

    public void train(String name){

    }

    public void getCharacterName(final int id){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                characterName.postValue(repository.getCharacterById(id).getName());
            }
        });
    }

    public Skill getSkill(String name, int id){
        return repository.getSkill(name, id);
    }

    public int getDice(Skill skill){
        String attribute = skill.getAttribute();
        Characters character = repository.getCharacterById(skill.getCharacter());
        int attributeDice = 0;
        double att;
        switch (attribute) {
            case "Strength":
                att = character.getStrength();
                if(att/10< (int)(att/10)) {
                    attributeDice = (int)(att/10) - 1;
                }
                break;
            case "Agility":
                att = character.getAgility();
                if(att/10< (int)(att/10)) {
                    attributeDice = (int)(att/10) - 1;
                }
                break;
            case "Dexterity":
                att = character.getDexterity();
                if(att/10< (int)(att/10)) {
                    attributeDice = (int)(att/10) - 1;
                }
                break;
            case "Constitution":
                att = character.getConstitution();
                if(att/10< (int)(att/10)) {
                    attributeDice = (int)(att/10) - 1;
                }
                break;
            case "Intelligence":
                att = character.getIntelligence();
                if(att/10< (int)(att/10)) {
                    attributeDice = (int)(att/10) - 1;
                }
                break;
            case "Will":
                att = character.getWill();
                if(att/10< (int)(att/10)) {
                    attributeDice = (int)(att/10) - 1;
                }
                break;
            case "Charisma":
                att = character.getCharisma();
                if(att/10< (int)(att/10)) {
                    attributeDice = (int)(att/10) - 1;
                }
                break;
        }
        double rank = skill.getRank();
        if(rank/10 < (int)(rank/10)) {
            return attributeDice + ((int)(rank/10)-1);
        }
        else{
            return attributeDice + ((int)(rank/10));
        }
    }
}
