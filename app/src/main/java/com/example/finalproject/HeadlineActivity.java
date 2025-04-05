package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HeadlineActivity extends BaseActivity {

    private ListView headingList;
    private ArrayList<Article> articles = new ArrayList<>();
    private ArticleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_headline, findViewById(R.id.content_frame));

        //Run the adapter on headingList to add items to listview
        headingList = findViewById(R.id.headingList);
        adapter = new ArticleAdapter(this, articles);
        headingList.setAdapter(adapter);

        //Run the AsyncTask to pull the article info
        new FetchHeadlinesTask().execute("https://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml");

        headingList.setOnItemClickListener((parent, view, position, id) -> {
            Article article = articles.get(position);
            Intent intent = new Intent(HeadlineActivity.this, ArticleDetailActivity.class);
            intent.putExtra("title", article.getTitle());
            intent.putExtra("description", article.getDescription());
            intent.putExtra("pubDate", article.getPubDate());
            intent.putExtra("link", article.getLink());
            startActivity(intent);
        });
    }

    //Class to gather all of the aritcle information from BBC
    private class FetchHeadlinesTask extends AsyncTask<String, Void, ArrayList<Article>> {

        @Override
        protected ArrayList<Article> doInBackground(String... urls) {
            ArrayList<Article> result = new ArrayList<>();
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(inputStream, null);

                int eventType = parser.getEventType();
                Article currentArticle = null;
                boolean inItem = false;

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String tagName = parser.getName();
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            if (tagName.equals("item")) {
                                currentArticle = new Article("", "", "", "");
                                inItem = true;
                            } else if (tagName.equals("title") && inItem) {
                                currentArticle.setTitle(parser.nextText());
                            } else if (tagName.equals("description") && inItem) {
                                currentArticle.setDescription(parser.nextText());
                            } else if (tagName.equals("pubDate") && inItem) {
                                currentArticle.setPubDate(parser.nextText());
                            } else if (tagName.equals("link") && inItem) {
                                currentArticle.setLink(parser.nextText());
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if (tagName.equals("item")) {
                                result.add(currentArticle);
                                inItem = false;
                            }
                            break;
                    }
                    eventType = parser.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<Article> articlesList) {
            if (articlesList != null) {
                articles.clear();
                articles.addAll(articlesList);
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(HeadlineActivity.this, "Failed to load articles", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
//Adapter class to add the articles to the ListView
class ArticleAdapter extends ArrayAdapter<Article> {

    private Context context;
    private List<Article> articles;

    public ArticleAdapter(Context context, List<Article> articles) {
        super(context, 0, articles);
        this.context = context;
        this.articles = articles;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_article, parent, false);
        }

        Article article = getItem(position);

        TextView titleText = convertView.findViewById(R.id.titleText);
        titleText.setText(article.getTitle());

        return convertView;
    }
}