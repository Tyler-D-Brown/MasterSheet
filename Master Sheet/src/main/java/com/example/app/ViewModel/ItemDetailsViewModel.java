package com.example.app.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.app.InventoryClasses.Item;
import com.example.app.db.AppRepository;
import com.example.app.db.dbClasses.Inventory;
import com.example.app.db.dbClasses.Skill;

public class ItemDetailsViewModel extends AndroidViewModel {
    public AppRepository repository;
    public MutableLiveData<Inventory> item = new MutableLiveData<Inventory>();

    public ItemDetailsViewModel(@NonNull Application application) {
        super(application);
        repository = AppRepository.getInstance(getApplication());
    }

    public void deleteSkill() {
    }

    public void getItem(int itemId, int characterId) {
    }

    public void saveItem(Item newItem) {
    }
}
