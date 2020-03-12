package com.example.app;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.app.InventoryClasses.Armor;
import com.example.app.InventoryClasses.Item;
import com.example.app.InventoryClasses.Weapon;
import com.example.app.ViewModel.ItemDetailsViewModel;
import com.example.app.ViewModel.SkillDetailViewModel;
import com.example.app.db.dbClasses.Skill;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

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
    private String location = "";
    private Item item = new Item();
    private Armor armor = new Armor();
    private Weapon weapon = new Weapon();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_details);
        Bundle intent = getIntent().getExtras();
        characterId = intent.getInt(CHARACTER_ID_KEY);
        itemId = intent.getInt(INVENTORY_ID_KEY);
        if(itemId!=-1) {
            type = viewModel.loadItem(itemId);
        }
        initViewModel();
    }

    public void initViewModel(){
        viewModel = ViewModelProviders.of(this).get(ItemDetailsViewModel.class);
        final Spinner typeSpin = (Spinner) findViewById(R.id.type);
        typeSpin.setOnItemSelectedListener(this);
        final List<String> typeList = new ArrayList<String>();
        typeList.add("Item");
        typeList.add("Weapon");
        typeList.add("Armor");
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeList);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpin.setAdapter(typeAdapter);
        final Spinner locationSpin = (Spinner) findViewById(R.id.location);
        List<String> locationList = new ArrayList<>();
        locationList.add("Head");
        locationList.add("Torso");
        locationList.add("leftArm");
        locationList.add("rightArm");
        locationList.add("leftLeg");
        locationList.add("rightLeg");
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locationList);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpin.setAdapter(locationAdapter);
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
        switch(type){
            case "Item":
                item = new Item(viewModel.item.getValue());
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        EditText name = (EditText) findViewById(R.id.name);
                        name.setText(item.getName());
                        EditText quantity = findViewById(R.id.quantity);
                        quantity.setText(Double.toString(item.getQty()));
                        if (typeList.indexOf(viewModel.item.getValue().getType()) != -1) {
                            typeSpin.setSelection(typeList.indexOf(viewModel.item.getValue().getType()));
                        }
                        EditText description = (EditText) findViewById(R.id.description);
                        description.setText(item.getDescription());
                        CheckBox favorited = (CheckBox) findViewById(R.id.favorite);
                        favorited.setVisibility(View.GONE);
                        EditText rank = (EditText) findViewById(R.id.rank);
                        rank.setVisibility(View.GONE);
                        locationSpin.setVisibility(View.GONE);
                    }
                }, 50);
                break;
            case "Weapon":
                weapon = new Weapon(viewModel.item.getValue());
                h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        EditText name = (EditText) findViewById(R.id.name);
                        name.setText(weapon.getName());
                        EditText quantity = findViewById(R.id.quantity);
                        quantity.setText(Double.toString(weapon.getQty()));
                        if (typeList.indexOf(viewModel.item.getValue().getType()) != -1) {
                            typeSpin.setSelection(typeList.indexOf(viewModel.item.getValue().getType()));
                        }
                        EditText description = (EditText) findViewById(R.id.description);
                        description.setText(weapon.getDescription());
                        CheckBox favorited = (CheckBox) findViewById(R.id.favorite);
                        favorited.setSelected(weapon.isStarred());
                        TextView rankHeading = findViewById(R.id.rankHeading);
                        rankHeading.setText("Skill Name");
                        EditText rank = (EditText) findViewById(R.id.rank);
                        rank.setInputType(InputType.TYPE_CLASS_TEXT);
                        rank.setText(weapon.getSkillName());
                        locationSpin.setVisibility(View.GONE);
                    }
                }, 50);
                break;
            case "Armor":
                armor = new Armor(viewModel.item.getValue());
                h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        EditText name = (EditText) findViewById(R.id.name);
                        name.setText(armor.getName());
                        EditText quantity = findViewById(R.id.quantity);
                        quantity.setText(Double.toString(armor.getQty()));
                        if (typeList.indexOf(viewModel.item.getValue().getType()) != -1) {
                            typeSpin.setSelection(typeList.indexOf(viewModel.item.getValue().getType()));
                        }
                        EditText description = (EditText) findViewById(R.id.description);
                        description.setText(armor.getDescription());
                        CheckBox favorited = (CheckBox) findViewById(R.id.favorite);
                        favorited.setVisibility(View.GONE);
                        TextView rankHeading = findViewById(R.id.rankHeading);
                        rankHeading.setText("Rank");
                        EditText rank = (EditText) findViewById(R.id.rank);
                        rank.setInputType(InputType.TYPE_CLASS_NUMBER);
                        rank.setText(armor.getRating());
                    }
                }, 50);
                break;
            default:
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        switch (parent.getId()) {
            case R.id.type:
                type = parent.getItemAtPosition(position).toString();
                EditText rank = findViewById(R.id.rank);
                Spinner armorLocation = findViewById(R.id.location);
                CheckBox favorite = findViewById(R.id.favorite);
                TextView rankHeading = findViewById(R.id.rankHeading);
                TextView locationHeading = findViewById(R.id.locationHeading);
                TextView favoriteHeading = findViewById(R.id.favoriteHeading);
                switch(parent.getItemAtPosition(position).toString()){
                    case "Item":
                        rank.setVisibility(View.GONE);
                        armorLocation.setVisibility(View.GONE);
                        favorite.setVisibility(View.GONE);
                        rankHeading.setVisibility(View.GONE);
                        locationHeading.setVisibility(View.GONE);
                        favoriteHeading.setVisibility(View.GONE);
                        break;
                    case "Weapon":
                        armorLocation.setVisibility(View.GONE);
                        rank.setVisibility(View.VISIBLE);
                        locationHeading.setVisibility(View.GONE);
                        rankHeading.setVisibility(View.VISIBLE);
                        favoriteHeading.setVisibility(View.VISIBLE);
                        favorite.setVisibility(View.VISIBLE);
                        break;
                    case "Armor":
                        favorite.setVisibility(View.GONE);
                        rank.setVisibility(View.VISIBLE);
                        armorLocation.setVisibility(View.VISIBLE);
                        favoriteHeading.setVisibility(View.GONE);
                        rankHeading.setVisibility(View.VISIBLE);
                        locationHeading.setVisibility(View.VISIBLE);
                        break;
                }
                break;
            case R.id.location:
                location=parent.getItemAtPosition(position).toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public boolean saveItem(){
        viewModel.saveItem(new Item());
        return true;
    }
}
