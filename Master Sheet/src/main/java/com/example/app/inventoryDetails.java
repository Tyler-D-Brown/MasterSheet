package com.example.app;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import com.example.app.InventoryClasses.Armor;
import com.example.app.InventoryClasses.Item;
import com.example.app.InventoryClasses.Weapon;
import com.example.app.ViewModel.InventoryDetailsViewModel;
import com.example.app.db.dbClasses.Inventory;
import com.example.app.db.dbClasses.Skill;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.app.Utilities.Constants.CHARACTER_ID_KEY;
import static com.example.app.Utilities.Constants.INVENTORY_ID_KEY;

public class inventoryDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private final Context context = this;
    private int characterId = 0;
    private int itemId = 0;
    private InventoryDetailsViewModel viewModel;
    private String type = "";
    private String location = "";
    private Item item = new Item();
    private Armor armor = new Armor();
    private Weapon weapon = new Weapon();
    private Weapon stabAndSmasher;
    private Skill gotSkill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(InventoryDetailsViewModel.class);
        setContentView(R.layout.inventory_details);
        Bundle intent = getIntent().getExtras();
        characterId = intent.getInt(CHARACTER_ID_KEY);
        itemId = intent.getInt(INVENTORY_ID_KEY);
        if(itemId!=-1) {
            viewModel.loadItem(itemId, characterId);
        }
        initViewModel();
    }

    public void initViewModel(){
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
        final List<String> locationList = new ArrayList<String>();
        locationList.add("Head");
        locationList.add("Torso");
        locationList.add("leftArm");
        locationList.add("rightArm");
        locationList.add("leftLeg");
        locationList.add("rightLeg");
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locationList);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpin.setAdapter(locationAdapter);
        locationSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View selectedItemView, int position, long id)
            {
                location=parent.getItemAtPosition(position).toString();
                //your code here

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView)
            {
                //return;
            }
        });
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
                viewModel.deleteSkill(itemId);
                finish();
            }
        });
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
              @Override
              public void run() {
                  if(itemId!=-1) {
                      type = viewModel.item.getValue().getType();
                  }
                  switch (type) {
                      case "Item":
                          EditText name = (EditText) findViewById(R.id.name);
                          EditText quantity = findViewById(R.id.quantity);
                          if (typeList.indexOf(type)!=-1){
                              typeSpin.setSelection(typeList.indexOf(type));
                          }
                          EditText description = (EditText) findViewById(R.id.description);
                          CheckBox favorited = (CheckBox) findViewById(R.id.favorite);
                          favorited.setVisibility(View.GONE);
                          EditText rank = (EditText) findViewById(R.id.rank);
                          rank.setVisibility(View.GONE);
                          locationSpin.setVisibility(View.GONE);
                          if(itemId!=-1) {
                              item = new Item(viewModel.item.getValue());
                              name.setText(item.getName());
                              quantity.setText(Double.toString(item.getQty()));
                              description.setText(item.getDescription());
                          }
                          break;
                      case "Weapon":
                          EditText name2 = (EditText) findViewById(R.id.name);
                          EditText quantity2 = findViewById(R.id.quantity);

                          if (typeList.indexOf(type)!=-1){
                              typeSpin.setSelection(typeList.indexOf(type));
                          }
                          EditText description2 = (EditText) findViewById(R.id.description);
                          CheckBox favorited2 = (CheckBox) findViewById(R.id.favorite);
                          TextView rankHeading = findViewById(R.id.rankHeading);
                          rankHeading.setText("Skill Name");
                          EditText rank2 = (EditText) findViewById(R.id.rank);
                          rank2.setInputType(InputType.TYPE_CLASS_TEXT);
                          locationSpin.setVisibility(View.GONE);

                          if(itemId !=-1) {
                              weapon = new Weapon(viewModel.item.getValue());
                              name2.setText(weapon.getName());
                              description2.setText(weapon.getDescription());
                              favorited2.setChecked(weapon.isStarred());
                              rank2.setText(weapon.getSkillName());
                              quantity2.setText(Double.toString(weapon.getQty()));
                          }
                          break;
                      case "Armor":
                          EditText name3 = (EditText) findViewById(R.id.name);
                          EditText quantity3 = findViewById(R.id.quantity);
                          if (typeList.indexOf(type)!=-1){
                              typeSpin.setSelection(typeList.indexOf(type));
                          }
                          EditText description3 = (EditText) findViewById(R.id.description);
                          CheckBox favorited3 = (CheckBox) findViewById(R.id.favorite);
                          favorited3.setVisibility(View.GONE);
                          TextView rankHeading3 = findViewById(R.id.rankHeading);
                          EditText rank3 = (EditText) findViewById(R.id.rank);
                          rank3.setInputType(InputType.TYPE_CLASS_NUMBER);

                          if(itemId!=-1) {
                              armor = new Armor(viewModel.item.getValue());
                              name3.setText(armor.getName());
                              quantity3.setText(Double.toString(armor.getQty()));
                              description3.setText(armor.getDescription());
                              rankHeading3.setText("Rank");
                              rank3.setText(Integer.toString(armor.getRating()));
                              if (locationList.indexOf(armor.getArmorLocation())!=-1){

                                  locationSpin.setSelection(locationList.indexOf(armor.getArmorLocation()));
                              }
                          }
                          break;
                      default:
                  }
              }
        }, 200);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

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
                type="Item";
                break;
            case "Weapon":
                armorLocation.setVisibility(View.GONE);
                rank.setVisibility(View.VISIBLE);
                locationHeading.setVisibility(View.GONE);
                rankHeading.setVisibility(View.VISIBLE);
                favoriteHeading.setVisibility(View.VISIBLE);
                favorite.setVisibility(View.VISIBLE);
                rankHeading.setText("Skill Name");
                rank.setInputType(InputType.TYPE_CLASS_TEXT);
                type="Weapon";
                break;
            case "Armor":
                favorite.setVisibility(View.GONE);
                rank.setVisibility(View.VISIBLE);
                armorLocation.setVisibility(View.VISIBLE);
                favoriteHeading.setVisibility(View.GONE);
                rankHeading.setVisibility(View.VISIBLE);
                locationHeading.setVisibility(View.VISIBLE);
                rankHeading.setText("Damage Reduction");
                rank.setInputType(InputType.TYPE_CLASS_NUMBER);
                type="Armor";
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public boolean saveItem(){
        EditText name = findViewById(R.id.name);
        EditText quantity = findViewById(R.id.quantity);
        EditText description = findViewById(R.id.description);
        if(type == "Weapon"){
            EditText skill = findViewById(R.id.rank);
            CheckBox fav = findViewById(R.id.favorite);
            try {
                Log.d("quantity", quantity.getText().toString());
                stabAndSmasher=(new Weapon(itemId,
                        name.getText().toString(),
                        description.getText().toString(),
                        Double.parseDouble(quantity.getText().toString()),
                        characterId,
                        skill.getText().toString(),
                        fav.isChecked()));
            }catch (Exception e){
                Log.e("Invalid input", "saveItem: ", e);
                Toast.makeText(context, "Please make sure all fields are filled out.", Toast.LENGTH_SHORT).show();
                return false;
            }
            try {
                final Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        gotSkill = viewModel.getSkill(stabAndSmasher.getSkillName(), characterId);
                    }
                });
                thread.start();
                thread.join();
                try {
                    gotSkill.getName();
                    viewModel.saveItem(stabAndSmasher);
                    return true;
                }catch (Exception e){
                    Log.e("Database Error", "saveItem: ", e);
                    Toast.makeText(context, "Error saving the Weapon to the database, Please make sure you have the skill to use it.", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }catch (Exception e){
                Log.e("Database Error", "saveItem: ", e);
                Toast.makeText(context, "Error saving the Weapon to the database, Please make sure you have the skill to use it.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }else if(type == "Armor"){
            EditText reduction = findViewById(R.id.rank);
            Armor armor = null;
                try {
                    armor = new Armor(itemId,
                            name.getText().toString(),
                            description.getText().toString(),
                            Double.parseDouble(quantity.getText().toString()),
                            characterId,
                            Integer.parseInt(reduction.getText().toString()),
                            location
                    );
                }catch (Exception e){
                    Log.e("Invalid input", "saveItem: ", e);
                    Toast.makeText(context, "Please make sure all fields are filled out.", Toast.LENGTH_SHORT).show();
                    return false;
                }
                try {
                    viewModel.saveItem(new Inventory(armor));
                }catch (Exception e){
                    Log.e("Database Error", "Error saving to database", e);
                    Toast.makeText(context, "Error saving to the database, please try again. ", Toast.LENGTH_SHORT).show();
                    return false;
                }
        }else{
            Item item = null;
                try {
                    item = new Item(itemId,
                            name.getText().toString(),
                            description.getText().toString(),
                            Double.parseDouble(quantity.getText().toString()),
                            characterId
                    );
                }catch (Exception e){
                    Log.e("Invalid input", "saveItem: ", e);
                    Toast.makeText(context, "Please make sure all fields are filled out.", Toast.LENGTH_SHORT).show();
                    return false;
                }
                try {
                    viewModel.saveItem(new Inventory(item));
                }catch (Exception e){
                    Log.e("Database Error", "Error saving to database", e);
                    Toast.makeText(context, "Error saving to the database, please try again. ", Toast.LENGTH_SHORT).show();
                    return false;
                }
        }
        return true;
    }
}
