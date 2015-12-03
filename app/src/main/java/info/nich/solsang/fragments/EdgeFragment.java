package info.nich.solsang.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.nich.solsang.R;
import info.nich.solsang.adapters.EdgeViewAdapter;
import info.nich.solsang.utils.XmlParser;

/**
 * Created by nich- on 2015/11/10.
 */
public class EdgeFragment extends Fragment {

    @Bind(R.id.recView) RecyclerView recyclerView;
    private List<String> mDatas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base, container, false);
        ButterKnife.bind(this,view);
        try {
            setUpRecyclerView();
            initData();
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        return view;
    }

    private void initData() throws IOException, XmlPullParserException {
        mDatas = new ArrayList<String>();
        InputStream inputStream = getActivity().getApplicationContext().getResources().getAssets().open("yashi.xml");
        mDatas = XmlParser.parser(inputStream);
        recyclerView.setAdapter(new EdgeViewAdapter(getActivity(), mDatas));
    }

    private void setUpRecyclerView() throws IOException, XmlPullParserException {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
