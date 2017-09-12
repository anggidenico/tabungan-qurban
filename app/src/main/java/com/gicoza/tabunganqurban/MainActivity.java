package com.gicoza.tabunganqurban;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SQLiteHandler db;
    private String totalsaldo, target;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        db = new SQLiteHandler(getApplicationContext());

        try {
            HashMap<String, String> user = db.showSaldo();

            //mengambil nilai dari database
            totalsaldo = user.get("totalsaldo");
            target = user.get("target");

            //perhitungan kekurangan
            int kurang = Integer.parseInt(totalsaldo) - Integer.parseInt(target);

            //menampilkan target
            TextView txtTarget = (TextView) findViewById(R.id.tvtarget);
            txtTarget.setText("Rp. " + target);

            //menampilkan saldo
            TextView txtSaldo = (TextView) findViewById(R.id.tvsaldo);
            txtSaldo.setText("Rp. " + totalsaldo);

            //menampilkan kekurangan
            TextView txtKekurangan = (TextView) findViewById(R.id.tvkurang);
            TextView txtStatus = (TextView) findViewById(R.id.labelstatus);
            Log.e("kekurangna", String.valueOf(kurang));
            if (Integer.parseInt(totalsaldo) >= Integer.parseInt(target)) {
                txtStatus.setText("Uang lebih");
            }
            txtKekurangan.setText("Rp. " + String.valueOf(kurang));

        } catch (Exception e) {
            e.printStackTrace();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Menabung.class);
                i.putExtra("totalsaldo", totalsaldo);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_mulai) {
            Intent i = new Intent(MainActivity.this, Qurban.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_menabung) {
            Intent i = new Intent(MainActivity.this, Menabung.class);
            i.putExtra("totalsaldo", totalsaldo);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_riwayat) {
            Intent i = new Intent(MainActivity.this, Riwayat.class);
            startActivity(i);
        } else if (id == R.id.nav_reset) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
                                    Intent i = new Intent(MainActivity.this, MainActivity.class);
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
        } else if (id == R.id.nav_sejarah) {
            Intent i = new Intent(MainActivity.this, Sejarah.class);
            startActivity(i);
        } else if (id == R.id.nav_tentang) {
            Intent i = new Intent(MainActivity.this, Tentang.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
