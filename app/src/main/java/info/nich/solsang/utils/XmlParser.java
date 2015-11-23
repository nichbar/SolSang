package info.nich.solsang.utils;

import android.content.Context;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import info.nich.solsang.entities.Emoji;

/**
 * Created by nich- on 2015/10/21.
 */
public class XmlParser {
    static final String TAG = "XMLPaser";
    private static final String ns = null;
    private Context context;

    public XmlParser(Context applicationContext) {
        this.context = applicationContext;
    }

    public static List<String> parser(InputStream stream) throws IOException, XmlPullParserException {
        List<String> emojiList = null;
        String emoji = null;

        //get XML parser
        XmlPullParser xmlPullParser = Xml.newPullParser();//创建XmlPullparser实例
        try {
            xmlPullParser.setInput(stream, "utf-8");
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        //get Event Type
        int evnType = xmlPullParser.getEventType();

        while (evnType != XmlPullParser.END_DOCUMENT) {
            switch (evnType) {
                case XmlPullParser.START_DOCUMENT:
                    emojiList = new ArrayList<>();
                    break;
                case XmlPullParser.START_TAG:
                    String tag = xmlPullParser.getName();
                    if (tag.equalsIgnoreCase("entry")) {
                        emoji = "";
                    } else if (emoji != null) {
                        if (tag.equalsIgnoreCase("string")) {
                            emoji = (xmlPullParser.nextText());
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (xmlPullParser.getName().equalsIgnoreCase("entry") && emoji != null) {
                        if (emojiList != null) {
                            emojiList.add(emoji);
                        }
                        emoji = null;
                    }
                    break;
            }
            evnType = xmlPullParser.next();
        }
        stream.close();
        return emojiList;
    }
}
