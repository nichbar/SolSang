package info.nich.solsang.adapters;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import info.nich.solsang.R;
import info.nich.solsang.entities.Emoji;
import info.nich.solsang.utils.DatabaseHelper;

/**
 * Created by nich- on 2015/11/20.
 */
public class MainViewAdapter extends RecyclerView.Adapter<MainViewAdapter.MainViewHolder> {

    private final LayoutInflater layoutInflater;
    private Context context;
    private List<String> mDatas = new ArrayList<>();
    private List<Emoji> emojiList;
    private ClipboardManager clipboardManager;

    public MainViewAdapter(Context context, List<Emoji> emojiList) {
        this.context = context;
        this.emojiList = emojiList;
        layoutInflater = LayoutInflater.from(context);
        clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

        dataTransform();
    }

    private void dataTransform() {
        for (int i = 0; i < emojiList.size(); i++) {
            mDatas.add(emojiList.get(i).getEmoji());
        }
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(layoutInflater.inflate(R.layout.item_list_main, parent, false));
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        holder.textView.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private AlertDialog.Builder builder;
        final String[] items = {"分享", "删除"};

        public MainViewHolder(final View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.main_textView);
            builder = new AlertDialog.Builder(itemView.getContext());

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipData clip = ClipData.newPlainText("emoji", mDatas.get(getAdapterPosition()));
                    clipboardManager.setPrimaryClip(clip);
                    Snackbar.make(itemView, mDatas.get(getAdapterPosition()) + " 已复制到剪贴板", Snackbar.LENGTH_SHORT).show();
                }
            });

            textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View v) {

                    builder.setTitle(mDatas.get(getAdapterPosition()));
                    builder.setCancelable(true);
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_SEND);
                                    intent.putExtra(Intent.EXTRA_TEXT, mDatas.get(getAdapterPosition()));
                                    intent.setType("text/plain");
                                    context.startActivity(intent);
                                    break;
                                case 1:
                                    deleteEmoji();
                                    break;
                            }
                        }

                        private void deleteEmoji() {
                            DatabaseHelper helper = new DatabaseHelper(itemView.getContext(), "emoji.db", null, 1);
                            SQLiteDatabase database = helper.getWritableDatabase();
                            database.delete("starsEmoji", "emoji=?", new String[]{mDatas.get(getAdapterPosition())});
                            notifyItemRemoved(getAdapterPosition());
                            notifyDataSetChanged();
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    return true;
                }
            });
        }
    }


}
