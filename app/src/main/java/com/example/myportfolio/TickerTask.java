package com.example.myportfolio;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.StringTokenizer;

// API Key from AlphaVantage : SUR7PN1DW5G9AJMO
// API Key 'demo' can also be used

public class TickerTask extends AsyncTask<String,String,String>
{
    String result=null;

    String strUrl = "https://www.alphavantage.co/query?apikey=SUR7PN1DW5G9AJMO&function=TIME_SERIES_DAILY&symbol=MSFT";

    public TickerTask() {
        super();
    }

    @Override
    protected String doInBackground(String... strings) {

        String data="";
        String line = "";

        try
        {
            URL url = new URL(strUrl);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
            while (line!=null)
            {
                line = bf.readLine();
                data = data + line;
            }

            System.out.println("Value Received is : " + data);
            result=data;

            //Parse received JSON
            JSONObject jsonObj = new JSONObject(data);

            // Getting JSON Array node
            //new JSONObject(new JSONObject(jsonObj.getString("Time Series (Daily)").split("\\},$",0)[0]).getString("2019-01-18")).getString("1. open")

            String[] dailySummaryArray = jsonObj.getString("Time Series (Daily)").split("\\},$");
//            StringTokenizer tokens = new StringTokenizer(jsonObj.getString("Time Series (Daily)"),"}");
//            String token = tokens.nextToken();
//            while ( token!=null)
//            {
//
//                token = tokens.nextToken();
//            }

            for (String dailySummary:dailySummaryArray) {
                JSONObject dailySummaryJSON = new JSONObject(dailySummary);
                String date = dailySummaryJSON.keys().next();
                //JSONObject OpenQuote = new JSONObject(new JSONObject(dailySummaryJSON.getString(date)).getString("1. open"));
                 result = new JSONObject(dailySummaryJSON.getString(date)).getString("1. open");
            }
            //String aJsonString = jsonObj.getString("Time Series (Daily)");
            //JSONObject dailyQuotes = jsonObj.getJSONArray("Time Series (Daily)");

            // looping through All Contacts
//            for (int i = 0; i < contacts.length(); i++) {
//                JSONObject c = contacts.getJSONObject(i);
//                String id = c.getString("id");
//                String name = c.getString("name");
//                String email = c.getString("email");
//                String address = c.getString("address");
//                String gender = c.getString("gender");
//
//
//            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        return result;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        String ticker = MainActivity.tvTickerName.getText().toString();
        strUrl = strUrl.replace("MSFT",ticker);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        MainActivity.tvTickerPrice.setText(this.result);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
