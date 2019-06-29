package com.albert.androidatlas;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.albert.androidatlas.material_design_312_313.MaterialDesign312Activity;
import com.albert.androidatlas.material_design_314.MaterialDesign314Activity;
import com.albert.androidatlas.screen_fit_213.ScreenFit213Activity;
import com.albert.androidatlas.screen_fit_214.DisplayCutoutActivity;
import com.albert.androidatlas.screen_fit_215.Screen215MusicAdapterActivity;
import com.albert.androidatlas.ui_core_123.UI123CanvasActivity;
import com.albert.androidatlas.ui_core_124.UI124CanvasActivity;
import com.albert.androidatlas.ui_core_125.UI125CanvasActivity;
import com.albert.androidatlas.ui_core_141.UI141AnimationActivity;
import com.albert.androidatlas.ui_core_142.UI142MusicAnimationActivity;
import com.google.android.material.navigation.NavigationView;


/**
 * FileName : MainActivity.java
 *
 * @author : Mihawk
 * @since : 2019-06-07
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.ui_121) {
            // Handle the camera action
        } else if (id == R.id.ui_122) {

        } else if (id == R.id.ui_123) {
            startNextAcitivity(UI123CanvasActivity.class);
        } else if (id == R.id.ui_124) {
            startNextAcitivity(UI124CanvasActivity.class);
        } else if (id == R.id.ui_125) {
            startNextAcitivity(UI125CanvasActivity.class);
        } else if (id == R.id.ui_141) {
            startNextAcitivity(UI141AnimationActivity.class);
        } else if (id == R.id.ui_142) {
            startNextAcitivity(UI142MusicAnimationActivity.class);
        } else if (id == R.id.fit_213) {
            startNextAcitivity(ScreenFit213Activity.class);
        } else if (id == R.id.fit_214) {
            startNextAcitivity(DisplayCutoutActivity.class);
        } else if (id == R.id.fit_215) {
            startNextAcitivity(Screen215MusicAdapterActivity.class);
        } else if (id == R.id.design_312_313) {
            startNextAcitivity(MaterialDesign312Activity.class);
        } else if (id == R.id.design_314) {
            startNextAcitivity(MaterialDesign314Activity.class);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void startNextAcitivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }


}
