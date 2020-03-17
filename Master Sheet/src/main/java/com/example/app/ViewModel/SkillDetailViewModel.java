package com.example.app.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.app.db.AppRepository;
import com.example.app.db.dbClasses.Skill;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SkillDetailViewModel extends AndroidViewModel {
    private AppRepository repository;
    public MutableLiveData<Skill> skill = new MutableLiveData<Skill>();
    private Executor executor = Executors.newSingleThreadExecutor();

    public SkillDetailViewModel(@NonNull Application application) {
        super(application);
        repository = AppRepository.getInstance(getApplication());
    }

    public void saveSkill(Skill newSkill) {
        repository.saveSkill(newSkill);
    }

    public void getSkill(final String skillId, final int characterId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                skill.postValue(repository.getSkill(skillId, characterId));
            }
        });
    }

    public void deleteSkill(String id) {
        if(!id.equals("")) {
            repository.deleteSkill(skill.getValue());
        }
    }
}
