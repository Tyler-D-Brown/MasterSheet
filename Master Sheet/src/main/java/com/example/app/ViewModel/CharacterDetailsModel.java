package com.example.app.ViewModel;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.app.db.AppRepository;
import com.example.app.db.dbClasses.Characters;
import com.example.app.db.dbClasses.Inventory;
import com.example.app.db.dbClasses.Skill;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CharacterDetailsModel extends AndroidViewModel {
    private AppRepository repository;
    public MutableLiveData<Characters> character = new MutableLiveData<Characters>();
    public LiveData<List<Inventory>> starredInventory;
    public LiveData<List<Skill>> starredSkills;
    private Executor executor = Executors.newSingleThreadExecutor();

    public CharacterDetailsModel(@NonNull Application application){
        super(application);
        repository = AppRepository.getInstance(getApplication());

    }

    public void loadData(final int characterId){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if(repository.getCharacterById(characterId) != null) {
                    Characters charac = repository.getCharacterById(characterId);
                    Log.d("TAG", charac.getName());
                    character.postValue(repository.getCharacterById(characterId));

                }
                if(repository.getStarredCharacterSkills(characterId) != null) {
                    starredSkills = repository.getStarredCharacterSkills(characterId);
                }
                if(repository.getStarredCharacterInventory(characterId) != null) {
                    starredInventory = repository.getStarredCharacterInventory(characterId);
                }
            }
        });
    }

    public void train(String skillName, int characterId){
        Skill skill = repository.getSkill(skillName, characterId);

        skill.setRank(skill.getRank()+.1);
    }

    public void train(Skill skill){
        skill.setRank(skill.getRank()+.1);
    }

    public int getDice(String skillName, int characterId){
        Skill skill = repository.getSkill(skillName, characterId);
        String attribute = skill.getAttribute();
        Characters character = repository.getCharacterById(characterId);
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

    public void saveCharacter(Characters character){
        repository.insertCharacter(character);
    }
}
