package com.example.app.ViewModel;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.app.InventoryClasses.Weapon;
import com.example.app.db.AppRepository;
import com.example.app.db.dbClasses.Inventory;
import com.example.app.db.dbClasses.Skill;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class InventoryDetailsViewModel extends AndroidViewModel {
    public AppRepository repository;
    public MutableLiveData<Inventory> item = new MutableLiveData<Inventory>();
    private Executor executor = Executors.newSingleThreadExecutor();
    public MutableLiveData<Skill> skillCheck = new MutableLiveData<Skill>();
    public LiveData<List<String>> skills;

    public InventoryDetailsViewModel(@NonNull Application application) {
        super(application);
        repository = AppRepository.getInstance(getApplication());
    }

    public void deleteSkill(int id) {
        if(id !=-1) {
            repository.deleteInventory(item.getValue());
        }
    }

    public void saveItem(final Inventory newItem) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                repository.saveInventory(newItem);
            }
        });
    }

    public void loadItem(final int itemId, final int character) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                item.postValue(repository.getItemById(itemId));
                skills = repository.getSkillNames(character);
            }
        });
    }

    public boolean saveItem(final Weapon stabAndSmasher) {
        try {
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            repository.saveInventory(new Inventory(stabAndSmasher));
                        }
                    });
                }
            },50);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Skill getSkill(String skillName, int characterId) {
        return repository.getSkill(skillName, characterId);
    }
}
