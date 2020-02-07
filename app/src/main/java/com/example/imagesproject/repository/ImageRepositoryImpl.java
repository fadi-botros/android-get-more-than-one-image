package com.example.imagesproject.repository;

import androidx.arch.core.util.Function;

import com.example.imagesproject.interactor.ImageRepository;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ImageRepositoryImpl implements ImageRepository {
    @Override
    public void imageURL(String fromURL, final Function<String, Void> callback) {
        Request request = new Request.Builder()
                .url(fromURL)
                .build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                // Bad practice, but this is just for demo
                // The correct thing is to use Optional or anything like that
                callback.apply(null);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                callback.apply(parseFromResponseBody(response.body().byteStream()));
            }
        });
    }

    public static String parseFromResponseBody(InputStream reader) {
//        try {
//            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
//            String tags = "";
//            parser.setInput(reader);
//            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
//            while(parser.next() != XmlPullParser.END_TAG) {
//                tags+=parser.getName();
//                if(parser.getEventType() == XmlPullParser.START_TAG) {
//                    if ("meta".equals(parser.getName())) {
////                        return "Entered2";
//                        if ("og:image".equals(parser.getAttributeValue(null, "property"))) {
//                            return parser.getAttributeValue(null, "content");
//                        }
//                    }
//                }
//            }
//            // Parsed the whole XML and no image found
//            return "Finished" + tags;
//        } catch (XmlPullParserException | IOException e) {
//            e.printStackTrace();
//            return "Exception";
//        }
        try {
            Document doc = Jsoup.parse(reader, "utf8", "");
            for(Element elem: doc.head().getElementsByTag("meta")) {
                if("og:image".equals(elem.attr("property"))) {
                    return elem.attr("content");
                }
            }
            return null;
        } catch(Exception ex) {
            return null;
        }
    }
}
