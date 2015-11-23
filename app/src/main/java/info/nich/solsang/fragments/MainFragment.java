package info.nich.solsang.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import info.nich.solsang.R;
import info.nich.solsang.adapters.MainViewAdapter;
import info.nich.solsang.entities.Emoji;
import info.nich.solsang.utils.DatabaseHelper;

/**
 * Created by nich- on 2015/10/15.
 */
public class MainFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Emoji> eDatasList;
    private static MainViewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        init(view);
        try {
            setUpRecyclerView();
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        return view;
    }

    private void init(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recView);
    }

    private void setUpRecyclerView() throws IOException, XmlPullParserException {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fetchDataFromDatabase();
    }

    private void fetchDataFromDatabase() {
        int id;
        String emoji;
        eDatasList = new ArrayList<>();

        DatabaseHelper helper = new DatabaseHelper(getActivity().getApplicationContext(), "emoji.db", null, 1);
        SQLiteDatabase sqliteDatabase = helper.getReadableDatabase();

        Cursor cursor = sqliteDatabase.query("starsEmoji", new String[]{"_id", "emoji"}, null, null, "emoji", null, "_id desc", null);

//      cursor.moveToFirst();
        while (cursor.moveToNext()) {
            id = cursor.getInt(cursor.getColumnIndex("_id"));
            emoji = cursor.getString(cursor.getColumnIndex("emoji"));
            eDatasList.add(new Emoji(id, emoji));
        }
        cursor.close();
        if (eDatasList != null) {
            adapter = new MainViewAdapter(getContext(), eDatasList);
            recyclerView.setAdapter(adapter);
        }
    }


}
