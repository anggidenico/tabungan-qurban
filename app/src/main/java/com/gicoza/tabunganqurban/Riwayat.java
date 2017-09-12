package com.gicoza.tabunganqurban;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class Riwayat extends AppCompatActivity {
    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TableLayout tableLayout = (TableLayout) findViewById(R.id.table_data);
        try {
            db = new SQLiteHandler(getApplicationContext());

            ArrayList<ArrayList<Object>> data = db.showTransaksi();
            for (int posisi = 0; posisi < data.size(); posisi++) {
                TableRow tabelBaris = new TableRow(this);
                ArrayList<Object> baris = data.get(posisi);

                TextView idTxt = new TextView(this);
                idTxt.setText(baris.get(0).toString());
                tabelBaris.addView(idTxt);

                TextView tglTxt = new TextView(this);
                tglTxt.setText(baris.get(1).toString());
                tabelBaris.addView(tglTxt);

                TextView jumlahTxt = new TextView(this);
                jumlahTxt.setText("Rp. "+baris.get(2).toString());
                tabelBaris.addView(jumlahTxt);

                tableLayout.addView(tabelBaris);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Riwayat.this);
                builder.setMessage("Apakah anda yakin?")
                        .setTitle("Reset")
                        .setCancelable(false)
                        .setPositiveButton("Ya",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        db = new SQLiteHandler(getApplicationContext());
                                        db.clearData();
                                        finish();
                                        Intent i = new Intent(Riwayat.this, MainActivity.class);
                                        startActivity(i);
                                    }
                                })
                        .setNegativeButton("Tidak",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return false;
    }
}