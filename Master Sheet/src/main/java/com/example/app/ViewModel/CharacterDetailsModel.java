package com.example.app.ViewModel;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.app.db.AppRepository;
import com.example.app.db.dbClasses.Characters;
import com.example.app.db.dbClasses.Inventory;
import com.example.app.db.dbClasses.Skill;

import java.util.List;

public class CharacterDetailsModel extends AndroidViewModel {
    private AppRepository repository;
    public MutableLiveData<Characters> character;
    public LiveData<List<Inventory>> starredInventory;
    public LiveData<List<Skill>> starredSkills;

    public CharacterDetailsModel(@NonNull Application application){
        super(application);
        repository = AppRepository.getInstance(getApplication());

    }

    public void loadData(final int characterId){
        character.postValue(repository.getCharacterById(characterId));
        starredSkills = repository.getStarredCharacterSkills(characterId);
        starredInventory = repository.getStarredCharacterInventory(characterId);
    }
}
