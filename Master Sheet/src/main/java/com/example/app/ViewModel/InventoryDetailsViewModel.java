package com.example.app.ViewModel;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.app.InventoryClasses.Weapon;
import com.example.app.db.AppRepository;
import com.example.app.db.dbClasses.Inventory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class InventoryDetailsViewModel extends AndroidViewModel {
    public AppRepository repository;
    public MutableLiveData<Inventory> item = new MutableLiveData<Inventory>();
    private Executor executor = Executors.newSingleThreadExecutor();

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

    public void loadItem(final int itemId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                item.postValue(repository.getItemById(itemId));
            }
        });
    }

    public void saveItem(final Weapon stabAndSmasher) {
        Log.d("Starred? ", String.valueOf(stabAndSmasher.isStarred()));
        executor.execute(new Runnable() {
            @Override
            public void run() {
                repository.saveInventory(new Inventory(stabAndSmasher));
            }
        });
    }
}
