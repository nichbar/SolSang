package info.nich.solsang.adapters;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.provider.UserDictionary;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.nich.solsang.R;
import info.nich.solsang.utils.DatabaseHelper;

/**
 * Created by nich- on 2015/10/15.
 */
public class EdgeViewAdapter extends RecyclerView.Adapter<EdgeViewAdapter.EdgeViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private Context mContext;
    private static List<String> mDatas;
    private static ClipboardManager clipboardManager;

    public EdgeViewAdapter(Context context, List<String> mDatas) {
        mContext = context;
        this.mDatas = mDatas;
        mLayoutInflater = LayoutInflater.from(context);
        clipboardManager = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    @Override
    public EdgeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EdgeViewHolder(mLayoutInflater.inflate(R.layout.item_text, parent, false));
    }

    @Override
    public void onBindViewHolder(EdgeViewHolder holder, int position) {
        holder.textView.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public static class EdgeViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.text_view) TextView textView;
        @Bind(R.id.star) ImageButton imageButton;

        public EdgeViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipData clip = ClipData.newPlainText("emoji", mDatas.get(getAdapterPosition()));
                    clipboardManager.setPrimaryClip(clip);
                    Snackbar.make(itemView, mDatas.get(getAdapterPosition()) + "已复制到剪贴板", Snackbar.LENGTH_SHORT).show();
                }
            });

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //create a database helper
                    DatabaseHelper helper = new DatabaseHelper(itemView.getContext(), "emoji.db", null, 1);

                    //create a writable database connection
                    SQLiteDatabase sqliteDatabase = helper.getWritableDatabase();

                    //generate a ContentValues object as a middleware of inserting data.
                    ContentValues cv = new ContentValues();
                    cv.put("emoji", mDatas.get(getAdapterPosition()));
                    sqliteDatabase.insert("starsEmoji", null, cv);
                    cv.clear();
                    Snackbar.make(itemView, mDatas.get(getAdapterPosition()) + "已收藏", Snackbar.LENGTH_SHORT).show();
                }
            });

            textView.setOnLongClickListener(new View.OnLongClickListener() {
                private AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                private Context context = itemView.getContext();
                final String[] items = {"分享", "添加到输入法词典"};

                @Override
                public boolean onLongClick(final View v) {
                    builder.setTitle(mDatas.get(getAdapterPosition()));
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
                                    View item = LayoutInflater.from(context).inflate(R.layout.dialog2, null, false);
                                    final EditText et = (EditText) item.findViewById(R.id.dialog_addToDictionary);
                                    final String mDataRightNow = mDatas.get(getAdapterPosition());
                                    AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                                    builder2.setTitle(R.string.addToDic);
                                    builder2.setMessage(mDataRightNow);
                                    builder2.setView(item);
                                    builder2.setPositiveButton(context.getString(R.string.yes), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            UserDictionary.Words.addWord(context, mDataRightNow, 250, et.getText().toString(), null);
                                            Snackbar.make(itemView, mDataRightNow + " 已经添加到系统词典", Snackbar.LENGTH_SHORT).show();
                                        }
                                    });
                                    AlertDialog dialog2 = builder2.create();
                                    dialog2.show();
                                    break;
                            }
                        }
                    });
                    builder.setCancelable(true);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return true;
                }
            });
        }
    }
}
