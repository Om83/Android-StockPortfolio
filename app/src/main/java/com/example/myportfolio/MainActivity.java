package com.example.myportfolio;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity  {

    private TextView mTextMessage;
    private static final String TAG = "MainActivity";
    private LineChart mChart;
    public static TextView tvTickerPrice;
    public static TextView tvTickerName;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Enable Scroll on TickerPrice
        tvTickerPrice = (TextView)findViewById(R.id.tvTickerPrice);
        tvTickerPrice.setMovementMethod(new ScrollingMovementMethod());

        tvTickerName = (TextView)findViewById(R.id.tvTickerName);

        //Hide Keyboard on Boot
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //prevent Text field to be covered when keyboard pops up
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //register eventhandler for Done in keyboard
        EditText targetEditText = (EditText)findViewById(R.id.tvTickerName);
        targetEditText.setOnEditorActionListener(new DoneOnEditorActionListener());

        new TickerTask().execute();
        DrawChart();
    }

    public void updateTickerPrice(View view) {


        //Generate a random price
//        Random r = new Random();
//        double price = (r.nextInt(12000 - 9000) + 9000)/100.00;
//
//        tvTickerPrice.setText(Double.toString(price));

        new TickerTask().execute();
        DrawChart();
    }

    public void DrawChart()
    {
        //LineChart related stuff
        mChart = (LineChart)findViewById(R.id.lineChart);
        //mChart.setOnChartGestureListener(MainActivity.this);
        //mChart.setOnChartValueSelectedListener(MainActivity.this);

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);

        Random r = new Random();
        //double price = (r.nextInt(20000 - 3500) + 3500)/100f;

        ArrayList<Entry> chartValues = new ArrayList<>();
        chartValues.add(new Entry(0,(r.nextInt(20000 - 3500) + 3500)/100f));
        chartValues.add(new Entry(1,(r.nextInt(20000 - 3500) + 3500)/100f));
        chartValues.add(new Entry(2,(r.nextInt(20000 - 3500) + 3500)/100f));
        chartValues.add(new Entry(3,(r.nextInt(20000 - 3500) + 3500)/100f));
        chartValues.add(new Entry(4,(r.nextInt(20000 - 3500) + 3500)/100f));
        chartValues.add(new Entry(5,(r.nextInt(20000 - 3500) + 3500)/100f));
        chartValues.add(new Entry(6,(r.nextInt(20000 - 3500) + 3500)/100f));



        LineDataSet set1 = new LineDataSet(chartValues,"Data Set 1");

        set1.setFillAlpha(110);
        //set1.setColor(Color.BLUE);
        set1.setLineWidth(2f);
        set1.setValueTextSize(12f);
        set1.setValueTextColor(Color.WHITE);

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
        lineDataSets.add(set1);

        LineData ldata = new LineData(lineDataSets);
        mChart.setData(ldata);
        mChart.setBackgroundColor(Color.GRAY);


    }

}

class DoneOnEditorActionListener implements TextView.OnEditorActionListener {
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            v.setCursorVisible(false);
            InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            return true;
        }

        return false;
    }
}