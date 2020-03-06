package com.example.app.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.app.db.AppRepository;
import com.example.app.db.dbClasses.Characters;

public class MainViewModel extends AndroidViewModel {
    public AppRepository repository;


    public MainViewModel(@NonNull Application application){
        super(application);
        repository = AppRepository.getInstance(application.getApplicationContext());
    }

    public Characters getCharacter(int id){
        return repository.getCharacterById(id);
    }

    public void deleteCharacter(Characters character){
        repository.deleteCharacter(character);
    }
}
