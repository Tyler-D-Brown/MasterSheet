package com.example.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.app.Utilities.Constants.INVENTORY_ID_KEY;

public class inventoryList extends AppCompatActivity {
    private final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchable_list);
        initViewModel();
        FloatingActionButton fab = findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, inventoryDetails.class);
                int id = -1;
                intent.putExtra(INVENTORY_ID_KEY, id);
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

    }
}
