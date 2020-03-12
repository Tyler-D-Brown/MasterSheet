package com.example.app;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.app.InventoryClasses.Item;
import com.example.app.ViewModel.ItemDetailsViewModel;
import com.example.app.ViewModel.SkillDetailViewModel;
import com.example.app.db.dbClasses.Skill;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.app.Utilities.Constants.CHARACTER_ID_KEY;
import static com.example.app.Utilities.Constants.INVENTORY_ID_KEY;
import static com.example.app.Utilities.Constants.SKILL_ID_KEY;

public class inventoryDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private final Context context = this;
    private int characterId = 0;
    private int itemId = 0;
    private ItemDetailsViewModel viewModel;
    private String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_details);
        Bundle intent = getIntent().getExtras();
        characterId = intent.getInt(CHARACTER_ID_KEY);
        itemId = intent.getInt(INVENTORY_ID_KEY);
        initViewModel();
    }

    public void initViewModel(){
        viewModel = ViewModelProviders.of(this).get(ItemDetailsViewModel.class);
        final Spinner type = (Spinner) findViewById(R.id.type);
        type.setOnItemSelectedListener(this);
        final List<String> typeList = new ArrayList<String>();
        typeList.add("Item");
        typeList.add("Weapon");
        typeList.add("Armor");
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeList);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(typeAdapter);
        FloatingActionButton save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saveItem()){
                    finish();
                }
            }
        });
        FloatingActionButton delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.deleteSkill();
                finish();
            }
        });
        if(itemId != -1){
            viewModel.getItem(itemId, characterId);
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (typeList.indexOf(viewModel.item.getValue().getType()) != -1) {
                        type.setSelection(typeList.indexOf(viewModel.item.getValue().getType()));
                    }
                    EditText name = (EditText) findViewById(R.id.skillName);
                    EditText rank = (EditText) findViewById(R.id.rank);
                    EditText description = (EditText) findViewById(R.id.description);
                    CheckBox favorited = (CheckBox) findViewById(R.id.favorite);
                    Log.e("CheckBox", Boolean.toString(favorited.isChecked()));
                    name.setText(viewModel.item.getValue().getName());
                    rank.setText(Double.toString(viewModel.item.getValue().getQty()));
                    description.setText(viewModel.item.getValue().getDescription());
                    if (viewModel.item.getValue().isStarred()) {
                        favorited.setChecked(true);
                    }
                }
            }, 50);

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        type = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public boolean saveItem(){
        CheckBox favorited = findViewById(R.id.favorite);
        boolean favorite;
        favorite = favorited.isChecked();
        EditText name = (EditText) findViewById(R.id.skillName);
        EditText rank = (EditText) findViewById(R.id.rank);
        EditText description = (EditText) findViewById(R.id.description);

        Item newItem = new Item();

        Log.d("Skill Name", newItem.getName());
        viewModel.saveItem(newItem);
        return true;
    }
}
