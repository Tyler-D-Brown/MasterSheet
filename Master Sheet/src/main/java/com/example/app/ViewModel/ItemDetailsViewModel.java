package com.example.app.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.app.InventoryClasses.Item;
import com.example.app.InventoryClasses.Weapon;
import com.example.app.db.AppRepository;
import com.example.app.db.dbClasses.Inventory;
import com.example.app.db.dbClasses.Skill;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ItemDetailsViewModel extends AndroidViewModel {
    public AppRepository repository;
    public MutableLiveData<Inventory> item = new MutableLiveData<Inventory>();
    private Executor executor = Executors.newSingleThreadExecutor();

    public ItemDetailsViewModel(@NonNull Application application) {
        super(application);
        repository = AppRepository.getInstance(getApplication());
    }

    public void deleteSkill() {
    }

    public void saveItem(Item newItem) {
    }

    public String loadItem(final int itemId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                item.postValue(repository.getItemById(itemId));
            }
        });
        if(item.getValue()!=null) {
            return item.getValue().getType();
        }else if(itemId == -1){
            return "";
        }else{
            return "";
        }
    }
}
