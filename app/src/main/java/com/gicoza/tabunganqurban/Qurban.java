package com.gicoza.tabunganqurban;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Qurban extends AppCompatActivity {

    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qurban);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //menentukan harga hewan
        Spinner hewan = (Spinner) findViewById(R.id.hewan);
        hewan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                EditText harga = (EditText) findViewById(R.id.harga);
                String jenis = parent.getSelectedItem().toString();
                switch (jenis){
                    case "Sapi":
                        harga.setText("17000000");
                        break;
                    case "Kambing":
                        harga.setText("2500000");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

    });



        db = new SQLiteHandler(getApplicationContext());

        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText jumlah = (EditText) findViewById(R.id.harga);

                try {
                    db.updateTarget(String.valueOf(jumlah.getText().toString().trim()));
                    finish();
                    Toast.makeText(Qurban.this,"Berhasil memilih target",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Qurban.this,MainActivity.class);
                    finish();
                    startActivity(i);
                }catch (Exception e){
                    Toast.makeText(Qurban.this,"Pastikan anda menginput angka!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
    @Override
    public void onBackPressed() {
        this.finish();
        Intent i = new Intent(Qurban.this,MainActivity.class);
        startActivity(i);
        return;
    }
}
