package info.nich.solsang.activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import info.nich.solsang.R;

/**
 * Created by nich- on 2015/11/18.
 */
public class AboutActivity extends AppCompatActivity{
    private Toolbar toolbar;
    private CollapsingToolbarLayout toolbarLayout;
    private TextView author, github, blog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        setUpToolbar();
        github = (TextView) findViewById(R.id.github);
        author = (TextView) findViewById(R.id.author);
        blog = (TextView) findViewById(R.id.blog);
        author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://weibo.com/nichbar");
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });
        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.github.com/nichbar");
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });
        blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://inich.info");
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });
    }

    private void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_about);
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        toolbarLayout.setTitle(this.getString(R.string.action_about));
        toolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
