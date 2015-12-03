package info.nich.solsang.activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.nich.solsang.R;

/**
 * Created by nich- on 2015/11/18.
 */
public class AboutActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.toolbar_about) Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar) CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.author) TextView author;
    @Bind(R.id.github) TextView github;
    @Bind(R.id.blog) TextView blog;
    @Bind(R.id.fab) FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        setUpToolbar();
        author.setOnClickListener(this);
        github.setOnClickListener(this);
        blog.setOnClickListener(this);
        fab.setOnClickListener(this);
    }

    private void setUpToolbar() {
        toolbarLayout.setTitle(this.getString(R.string.action_about));
        toolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        Uri uri;
        switch (v.getId()){
            case R.id.author:
                uri = Uri.parse("http://weibo.com/nichbar");
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
                break;
            case R.id.github:
                uri = Uri.parse("https://www.github.com/nichbar");
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
                break;
            case R.id.blog:
                uri = Uri.parse("http://inich.info");
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
                break;
            case R.id.fab:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "这个颜文字APP不错喔，http://www.pre.im/solsang");
                intent.setType("text/plain");
                startActivity(intent);
                break;
        }
    }
}
