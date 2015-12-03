package info.nich.solsang.activities;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


import butterknife.Bind;
import butterknife.ButterKnife;
import info.nich.solsang.fragments.EdgeFragment;
import info.nich.solsang.fragments.MainFragment;
import info.nich.solsang.R;
import info.nich.solsang.fragments.SideFragment;
import info.nich.solsang.utils.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.container) ViewPager viewPager;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.tabs) TabLayout tabLayout;
    @Bind(R.id.fab) FloatingActionButton fab;

    private MyFragmentAdapter adapter;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setUpToolbar();
        setUpViewPager();
        setUpTabLayout();
        setUpNotification();
        setUpDatabase();
        setUpFloatingActionBarListener();
    }

    private void setUpFloatingActionBarListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View item = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog, null);
                new AlertDialog.Builder(v.getContext())
                        .setTitle(R.string.addNewEmoji)
                        .setView(item)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText et = (EditText) item.findViewById(R.id.dialog_et);

                                //create a database helper to help create database access
                                DatabaseHelper helper = new DatabaseHelper(viewPager.getContext(), "emoji.db", null, 1);

                                //create a writable database connection
                                SQLiteDatabase sqliteDatabase = helper.getWritableDatabase();

                                //generate a ContentValues object as a middleware of inserting data.
                                insertIntoDatabase(et, sqliteDatabase);
                                Snackbar.make(tabLayout, et.getText().toString() + getString(R.string.added), Snackbar.LENGTH_SHORT).show();
                            }

                            private void insertIntoDatabase(EditText et, SQLiteDatabase sqliteDatabase) {
                                if (!et.getText().toString().equals("")) {
                                    ContentValues cv = new ContentValues();
                                    cv.put("emoji", et.getText().toString());
                                    sqliteDatabase.insert("starsEmoji", null, cv);
                                    cv.clear();
                                } else
                                    Snackbar.make(tabLayout, "请不要留空", Snackbar.LENGTH_SHORT);
                            }
                        })
                        .setNegativeButton(R.string.cancel, null)
                        .show();
            }

        });
    }

    private void setUpDatabase() {
        DatabaseHelper helper = new DatabaseHelper(this, "emoji.db", null, 1);
    }

    private void setUpNotification() {
//        NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
//        Notification notification = NotificationHelper.buildNotification(this,Notification.PRIORITY_DEFAULT);
//        notification.flags = Notification.FLAG_NO_CLEAR;
//        manager.notify(0,notification);
    }

    private void setUpToolbar() {
        toolbar.setTitle(this.getString(R.string.app_name));
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.about:
                        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });
    }

    private void setUpTabLayout() {
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabTextColors(Color.WHITE, Color.WHITE);
    }

    private void setUpViewPager() {
        adapter = new MyFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    //两次back退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 3000) {
                Snackbar.make(tabLayout, "再按一次退出", Snackbar.LENGTH_LONG).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public class MyFragmentAdapter extends FragmentStatePagerAdapter {
        final int PAGE_COUNT = 3;
        private String tabTitles[] = new String[]{getString(R.string.Tab1), getString(R.string.Tab2), getString(R.string.Tab3)};

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
//            MainFragment myFragment = null;
            switch (position) {
                case 0:
                    return new MainFragment();
                case 1:
                    return new SideFragment();
                case 2:
                    return new EdgeFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

    }
}
