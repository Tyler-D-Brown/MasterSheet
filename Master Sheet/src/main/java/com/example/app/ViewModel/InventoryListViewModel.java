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

public class InventoryListViewModel extends AndroidViewModel {
    public AppRepository repository;
    public MutableLiveData<Characters> character = new MutableLiveData<Characters>();
    private Executor executor = Executors.newSingleThreadExecutor();


    public InventoryListViewModel(@NonNull Application application) {
        super(application);
        repository = AppRepository.getInstance(application.getApplicationContext());
    }

    public void getCharacterName(final int id) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                character.postValue(repository.getCharacterById(id));
            }
        });
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
                }
                else{
                    attributeDice = (int)(att/10);
                }
                break;
            case "Agility":
                att = character.getValue().getAgility();
                if(att/10< (int)(att/10)) {
                    attributeDice = (int)(att/10) - 1;
                }
                else{
                    attributeDice = (int)(att/10);
                }
                break;
            case "Dexterity":
                att = character.getValue().getDexterity();
                if(att/10< (int)(att/10)) {
                    attributeDice = (int)(att/10) - 1;
                }
                else{
                    attributeDice = (int)(att/10);
                }
                break;
            case "Constitution":
                att = character.getValue().getConstitution();
                if(att/10< (int)(att/10)) {
                    attributeDice = (int)(att/10) - 1;
                }
                else{
                    attributeDice = (int)(att/10);
                }
                break;
            case "Intelligence":
                att = character.getValue().getIntelligence();
                if(att/10< (int)(att/10)) {
                    attributeDice = (int)(att/10) - 1;
                }
                else{
                    attributeDice = (int)(att/10);
                }
                break;
            case "Will":
                att = character.getValue().getWill();
                if(att/10< (int)(att/10)) {
                    attributeDice = (int)(att/10) - 1;
                }
                else{
                    attributeDice = (int)(att/10);
                }
                break;
            case "Charisma":
                att = character.getValue().getCharisma();
                if(att/10< (int)(att/10)) {
                    attributeDice = (int)(att/10) - 1;
                }
                else{
                    attributeDice = (int)(att/10);
                }
                break;
        }
        double rank = skill.getRank();
        if(rank < (int)rank) {

            return attributeDice + (int)rank-1;
        }
        else{
            return attributeDice + (int)rank;
        }
    }
}
