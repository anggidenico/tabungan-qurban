package com.gicoza.tabunganqurban;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class Menabung extends AppCompatActivity {
    private SQLiteHandler db;
    private EditText etTanggal, etJenisPulsa, etHargaJual;
    private Calendar calendar;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menabung);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);

        db = new SQLiteHandler(getApplicationContext());
        etTanggal = (EditText) findViewById(R.id.tgl);
        final String saldo = getIntent().getStringExtra("totalsaldo");

        final EditText jumlah = (EditText) findViewById(R.id.jumlah);

        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    final int saldosekarang = Integer.parseInt(saldo) + Integer.parseInt(jumlah.getText().toString().trim());
                    Log.e("saldo skrg", String.valueOf(saldosekarang));
                    final String tgl = etTanggal.getText().toString().trim();
                    db.addTransaksi(tgl, jumlah.getText().toString().trim(), saldosekarang);
                    finish();
                    Toast.makeText(Menabung.this, "Berhasil menabung", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Menabung.this, MainActivity.class);
                    finish();
                    startActivity(i);
                } catch (Exception e) {
                    Toast.makeText(Menabung.this, "Masukan jumlah tabungan", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // here argument values...
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2 + 1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        etTanggal = (EditText) findViewById(R.id.tgl);
        etTanggal.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        this.finish();
        Intent i = new Intent(Menabung.this, MainActivity.class);
        startActivity(i);
        return;
    }
}
