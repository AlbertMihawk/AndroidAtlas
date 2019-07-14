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

import com.albert.actual325.Actual325MainActivity;
import com.albert.androidatlas.actual_312_313.MaterialDesign312Activity;
import com.albert.androidatlas.actual_314.MaterialDesign314Activity;
import com.albert.androidatlas.actual_321.MaterialDesign321Activity;
import com.albert.androidatlas.actual_321_2.MaterialDesign3212Activity;
import com.albert.androidatlas.actual_322.MaterialDesign322Activity;
import com.albert.androidatlas.actual_323.MaterialDesign323Activity;
import com.albert.androidatlas.screen_fit_213.ScreenFit213Activity;
import com.albert.androidatlas.screen_fit_214.DisplayCutoutActivity;
import com.albert.androidatlas.screen_fit_215.Screen215MusicAdapterActivity;
import com.albert.androidatlas.screen_fit_215_2.Screen2152RedPackageActivity;
import com.albert.androidatlas.ui_core_123.UI123CanvasActivity;
import com.albert.androidatlas.ui_core_124.UI124CanvasActivity;
import com.albert.androidatlas.ui_core_125.UI125CanvasActivity;
import com.albert.androidatlas.ui_core_141.UI141AnimationActivity;
import com.albert.androidatlas.ui_core_142.UI142MusicAnimationActivity;
import com.albert.actual324.Actual324Activity;
import com.google.android.material.navigation.NavigationView;

//import com.albert.materialdesign324.Actual324Activity;


/**
 * FileName : Actual324Activity.java
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

        switch (id) {
            case R.id.ui_121:
                break;
            case R.id.ui_122:
                break;
            case R.id.ui_123:
                startNextAcitivity(UI123CanvasActivity.class);
                break;
            case R.id.ui_124:
                startNextAcitivity(UI124CanvasActivity.class);
                break;
            case R.id.ui_125:
                startNextAcitivity(UI125CanvasActivity.class);
                break;
            case R.id.ui_141:
                startNextAcitivity(UI141AnimationActivity.class);
                break;
            case R.id.ui_142:
                startNextAcitivity(UI142MusicAnimationActivity.class);
                break;
            case R.id.fit_213:
                startNextAcitivity(ScreenFit213Activity.class);
                break;
            case R.id.fit_214:
                startNextAcitivity(DisplayCutoutActivity.class);
                break;
            case R.id.fit_215:
                startNextAcitivity(Screen215MusicAdapterActivity.class);
                break;
            case R.id.fit_215_2:
                startNextAcitivity(Screen2152RedPackageActivity.class);
                break;
            case R.id.actual_312_313:
                startNextAcitivity(MaterialDesign312Activity.class);
                break;
            case R.id.actual_314:
                startNextAcitivity(MaterialDesign314Activity.class);
                break;
            case R.id.actual_321:
                startNextAcitivity(MaterialDesign321Activity.class);
                break;
            case R.id.actual_321_2:
                startNextAcitivity(MaterialDesign3212Activity.class);
                break;
            case R.id.actual_322:
                startNextAcitivity(MaterialDesign322Activity.class);
                break;
            case R.id.actual_323:
                startNextAcitivity(MaterialDesign323Activity.class);
                break;
            case R.id.actual_324:
                startNextAcitivity(Actual324Activity.class);
                break;
            case R.id.actual_325:
                startNextAcitivity(Actual325MainActivity.class);
                break;
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
