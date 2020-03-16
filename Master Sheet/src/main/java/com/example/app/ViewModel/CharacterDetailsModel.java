package com.example.app.ViewModel;

import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
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
    public AppRepository repository;
    public MutableLiveData<Characters> character = new MutableLiveData<Characters>();
    public LiveData<List<Inventory>> starredInventory;
    public LiveData<List<Skill>> starredSkills;
    private Executor executor = Executors.newSingleThreadExecutor();
    MutableLiveData<Skill> skill = new MutableLiveData<>();

    public CharacterDetailsModel(@NonNull Application application){
        super(application);
        repository = AppRepository.getInstance(getApplication());

    }

    public void loadData(final int characterId){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if(repository.getCharacterById(characterId) != null) {
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

    public void train(final String skillName, final int characterId){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                repository.trainSkill(skillName, characterId);
            }
        });
    }

    public void train(final Skill skill){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                repository.trainSkill(skill.getName(), skill.getCharacter());
            }
        });
    }

    public int getDice(final String skillName, final int characterId){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                skill.postValue(repository.getSkill(skillName, characterId));
            }
        });
        String attribute = skill.getValue().getAttribute();
        int attributeDice = 0;
        double att;
        switch (attribute) {
            case "Strength":
                att = character.getValue().getStrength();
                if(att/10< (int)(att/10)) {
                    attributeDice = (int)(att/10) - 1;
                }else{
                    attributeDice = (int)(att/10);
                }
                break;
            case "Agility":
                att = character.getValue().getAgility();
                if(att/10< (int)(att/10)) {
                    attributeDice = (int)(att/10) - 1;
                }else{
                    attributeDice = (int)(att/10);
                }
                break;
            case "Dexterity":
                att = character.getValue().getDexterity();
                if(att/10< (int)(att/10)) {
                    attributeDice = (int)(att/10) - 1;
                }else{
                    attributeDice = (int)(att/10);
                }
                break;
            case "Constitution":
                att = character.getValue().getConstitution();
                if(att/10< (int)(att/10)) {
                    attributeDice = (int)(att/10) - 1;
                }else{
                    attributeDice = (int)(att/10);
                }
                break;
            case "Intelligence":
                att = character.getValue().getIntelligence();
                if(att/10< (int)(att/10)) {
                    attributeDice = (int)(att/10) - 1;
                }else{
                    attributeDice = (int)(att/10);
                }
                break;
            case "Will":
                att = character.getValue().getWill();
                if(att/10< (int)(att/10)) {
                    attributeDice = (int)(att/10) - 1;
                }else{
                    attributeDice = (int)(att/10);
                }
                break;
            case "Charisma":
                att = character.getValue().getCharisma();
                if(att/10< (int)(att/10)) {
                    attributeDice = (int)(att/10) - 1;
                }else{
                    attributeDice = (int)(att/10);
                }
                break;
        }
        double rank = skill.getValue().getRank();
        if(rank/10 < (int)(rank/10)) {
            return attributeDice + ((int)rank-1);
        }
        else{
            return attributeDice + ((int)rank);
        }
    }

    public int getDice(Skill skill){
        String attribute = skill.getAttribute();
        int attributeDice = 0;
        double att;
        switch (attribute) {
            case "Strength":
                att = character.getValue().getStrength();
                if(att/10< (int)(att/10)) {
                    attributeDice = (int)(att/10) - 1;
                }else{
                    attributeDice = (int)(att/10);
                }
                break;
            case "Agility":
                att = character.getValue().getAgility();
                if(att/10< (int)(att/10)) {
                    attributeDice = (int)(att/10) - 1;
                }else{
                    attributeDice = (int)(att/10);
                }
                break;
            case "Dexterity":
                att = character.getValue().getDexterity();
                if(att/10< (int)(att/10)) {
                    attributeDice = (int)(att/10) - 1;
                }else{
                    attributeDice = (int)(att/10);
                }
                break;
            case "Constitution":
                att = character.getValue().getConstitution();
                if(att/10< (int)(att/10)) {
                    attributeDice = (int)(att/10) - 1;
                }else{
                    attributeDice = (int)(att/10);
                }
                break;
            case "Intelligence":
                att = character.getValue().getIntelligence();
                if(att/10< (int)(att/10)) {
                    attributeDice = (int)(att/10) - 1;
                }else{
                    attributeDice = (int)(att/10);
                }
                break;
            case "Will":
                att = character.getValue().getWill();
                if(att/10< (int)(att/10)) {
                    attributeDice = (int)(att/10) - 1;
                }else{
                    attributeDice = (int)(att/10);
                }
                break;
            case "Charisma":
                att = character.getValue().getCharisma();
                if(att/10< (int)(att/10)) {
                    attributeDice = (int)(att/10) - 1;
                }else{
                    attributeDice = (int)(att/10);
                }
                break;
        }
        double rank = skill.getRank();
        if(rank < (int)(rank)) {
            return attributeDice + ((int)rank-1);
        }else{
            return attributeDice + (int)rank;
        }
    }

    public void saveCharacter(Characters character){
        repository.insertCharacter(character);
    }
}
