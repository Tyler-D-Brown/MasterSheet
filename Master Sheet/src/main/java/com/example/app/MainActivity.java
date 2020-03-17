package com.example.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.app.ViewModel.MainViewModel;
import com.example.app.db.dbClasses.Characters;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import static com.example.app.Utilities.Constants.CHARACTER_ID_KEY;

public class MainActivity extends AppCompatActivity {
    private final Context context = this;
    private MainViewModel mainViewModel;
    private List<Characters> charactersList = new ArrayList<Characters>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_list);
        initViewModel();
        FloatingActionButton fab = findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, characterDetails.class);
                int id = -1;
                intent.putExtra(CHARACTER_ID_KEY, id);
                try{
                    context.startActivity(intent);
                }
                catch(Exception e){
                    Log.d("Except", e.toString());
                }
            }
        });
    }

    private void initViewModel() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        final Observer<List<Characters>> characterObserver = new Observer<List<Characters>>() {
            @Override
            public void onChanged(@Nullable List<Characters> characters){
                if(charactersList.size() != 0) {
                    charactersList.clear();
                }
                if(characters.size() != 0) {
                    charactersList.addAll(characters);
                }
                if(charactersList != null){
                    for(int i = 0; i < charactersList.size(); i++){
                        insertCharacterRow(charactersList.get(i));
                    }
                }
            }
        };
        mainViewModel.repository.characters.observe(this, characterObserver);
    }

    private void insertCharacterRow(final Characters add){
        final LinearLayout layout = findViewById(R.id.rowContainer);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View newCharacterRow = inflater.inflate(R.layout.cards, null);
        Button button = newCharacterRow.findViewById(R.id.title);
        button.setText(add.getName());
        EditText race = newCharacterRow.findViewById(R.id.field2);
        race.setText(add.getRace());
        EditText build = newCharacterRow.findViewById(R.id.field3);
        build.setText(add.getBuild());
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View button){
                Intent intent = new Intent(getBaseContext(), characterDetails.class);
                intent.putExtra(CHARACTER_ID_KEY, add.getId());
                try{
                    context.startActivity(intent);
                }
                catch(Exception e){
                    Log.d("Except", e.toString());
                }
            }
        });
        FloatingActionButton delete = newCharacterRow.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View Button){
                mainViewModel.deleteCharacter(add.getId());
                initViewModel();
                View exists = layout.findViewById(add.getId());
                ((ViewGroup) exists.getParent()).removeView(exists);
            }
        });
        newCharacterRow.setId(add.getId());
        View exists = layout.findViewById(add.getId());
        if(exists == null){
            layout.addView(newCharacterRow);
        }else{
            ((ViewGroup) exists.getParent()).removeView(exists);
            layout.addView(newCharacterRow);
        }
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
 */
}
