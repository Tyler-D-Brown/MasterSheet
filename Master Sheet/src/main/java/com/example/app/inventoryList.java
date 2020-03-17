package com.example.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.app.ViewModel.InventoryListViewModel;
import com.example.app.ViewModel.SkillListViewModel;
import com.example.app.db.dbClasses.Inventory;
import com.example.app.db.dbClasses.Skill;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.example.app.Utilities.Constants.CHARACTER_ID_KEY;
import static com.example.app.Utilities.Constants.INVENTORY_ID_KEY;
import static com.example.app.Utilities.Constants.SKILL_ID_KEY;

public class inventoryList extends AppCompatActivity {
    private final Context context = this;
    private InventoryListViewModel viewModel;
    private List<Inventory> inventoryList = new ArrayList<Inventory>();
    int characterId;
    private Executor executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SearchView search = findViewById(R.id.search);
        search.setVisibility(View.GONE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchable_list);
        initViewModel();
        final TextView heading = findViewById(R.id.listHeading);
        Bundle intent = getIntent().getExtras();
        characterId = intent.getInt(CHARACTER_ID_KEY);
        viewModel.getCharacterName(characterId);
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                heading.setText(viewModel.character.getValue().getName().concat("'s Inventory"));
            }
        }, 50);
        FloatingActionButton fab = findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, inventoryDetails.class);
                intent.putExtra(CHARACTER_ID_KEY, characterId);
                int id = -1;
                intent.putExtra(INVENTORY_ID_KEY, id);
                try{
                    context.startActivity(intent);
                }
                catch(Exception e){
                    Log.d("Except", e.toString());
                }
                initViewModel();
            }
        });
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(InventoryListViewModel.class);
        final Observer<List<Inventory>> characterObserver = new Observer<List<Inventory>>() {
            @Override
            public void onChanged(@Nullable List<Inventory> inventory){
                if(inventoryList.size() != 0) {
                    inventoryList.clear();
                }
                if(inventory.size() != 0) {
                    inventoryList.addAll(inventory);
                }
                if(inventoryList != null){
                    for(int i = 0; i < inventoryList.size(); i++){
                        insertRow(inventoryList.get(i));
                    }
                }
            }
        };
        viewModel.repository.inventory.observe(this, characterObserver);
    }

    private void insertRow(final Inventory add){
        final MutableLiveData<Skill> itemSkill = new MutableLiveData<>();
        if(add.getType().equals("Weapon")) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    itemSkill.postValue(viewModel.repository.getSkill(add.getSkillName(), characterId));
                }
            });
        }
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                final LinearLayout layout = findViewById(R.id.rowContainer);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                final View newSkillRow = inflater.inflate(R.layout.searchable_list_card, null);
                Button button = newSkillRow.findViewById(R.id.name);
                button.setText(add.getName());
                if(add.getType().equals("Weapon")){
                    TextView dice = newSkillRow.findViewById(R.id.dice);
                    int die = viewModel.getDice(itemSkill.getValue());
                    dice.setText(Integer.toString(die));
                }else{
                    TextView dice = newSkillRow.findViewById(R.id.dice);
                    dice.setText("N/A");
                }
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View button) {
                        Intent intent = new Intent(getBaseContext(), inventoryDetails.class);
                        intent.putExtra(INVENTORY_ID_KEY, add.getId());
                        intent.putExtra(CHARACTER_ID_KEY, add.getCharacter());
                        try {
                            context.startActivity(intent);
                        } catch (Exception e) {
                            Log.d("Except", e.toString());
                        }
                    }
                });
                if(add.getType().equals("Weapon")){
                    FloatingActionButton train = newSkillRow.findViewById(R.id.train);
                    train.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View Button) {
                            executor.execute(new Runnable() {
                                @Override
                                public void run() {
                                    viewModel.repository.trainSkill(add.getSkillName(), add.getCharacter());
                                }
                            });
                            initViewModel();
                        }
                    });
                }
                newSkillRow.setTag(add.getName());
                View exists = layout.findViewWithTag(add.getName());
                if (exists == null) {
                    layout.addView(newSkillRow);
                } else {
                    ((ViewGroup) exists.getParent()).removeView(exists);
                    layout.addView(newSkillRow);
                }
            }
        }, 50);
    }
}
