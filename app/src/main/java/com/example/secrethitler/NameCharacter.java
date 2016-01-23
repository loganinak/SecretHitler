package com.example.secrethitler;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class NameCharacter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
    }

    public void endConfirm(View view){
        Intent data = new Intent();
        EditText name = (EditText)findViewById(R.id.inputName);

        ArrayList<Player> group = getIntent().getParcelableArrayListExtra("group");

        group.get(0).setName(name.getText().toString());
        group.add(group.remove(0));

        data.putParcelableArrayListExtra("group", group);
        setResult(Activity.RESULT_OK, data);
        finish();
    }

    @Override
    public void onBackPressed() {
    }
}
