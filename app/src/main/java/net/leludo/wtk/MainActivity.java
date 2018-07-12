package net.leludo.wtk;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.List;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private Date today;
    SimpleDateFormat sdf;
    TextView durationText;
    TextView durationOfTheDayText;
    TextView todayText;
    EditText inEditText;
    EditText outEditText;
    ListView durationList;
    List<Period> periods = new ArrayList<>();
    Period period;
    PeriodDatabase mDB;
    Duration dailyDuration ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("lifecycle", "onCreate");
        mDB = new PeriodDatabase(this);

        today = Calendar.getInstance().getTime();
        sdf = new SimpleDateFormat("EEEE dd MMMM", Locale.FRANCE);

        this.todayText = (TextView) findViewById(R.id.todayText);
        this.todayText.setText(sdf.format(today));

        this.inEditText = (EditText) findViewById(R.id.inTime);
        this.outEditText = (EditText) findViewById(R.id.outTime);

        durationText = (TextView) findViewById(R.id.durationText);
        durationOfTheDayText = (TextView) findViewById(R.id.durationOfTheDayText);
        durationList = (ListView) findViewById(R.id.durationList);
        //periods.add(new Period());
        durationList.setAdapter(new PeriodAdapter(this, periods));

        this.loadExistingPeriods();

        Calendar cal = Calendar.getInstance();
        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        long inTimeInMillis = settings.getLong("in", 0) ;
        if (inTimeInMillis != 0L) {
            cal.setTimeInMillis(inTimeInMillis);
            this.period = new Period().start(cal.getTime());
            this.inEditText.setText(this.period.formatedStartTime());
        }
        long outTimeInMillis = settings.getLong("out", 0) ;
        if (outTimeInMillis != 0L) {
            cal.setTimeInMillis(outTimeInMillis);
            this.period.end(cal.getTime());
            this.outEditText.setText(this.period.formatedEndTime());
            compute();
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lyfecycle", "onPause");

        if (this.period!= null) {
            SharedPreferences settings = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putLong("in", this.period.getStart()!=null?this.period.getStart().getTime():0);
            editor.putLong("out", this.period.getEnd()!=null?this.period.getEnd().getTime():0);
            editor.commit();
        }

    }

    public void onClickClose(View view) {
        finish();
    }

    public void onIn(View view) {
        this.period = new Period().start(Calendar.getInstance().getTime());

        this.inEditText.setText(this.period.formatedStartTime());
        this.outEditText.setText("");
        durationText.setText("");
    }

    public void onOut(View view) {
        this.period.end(Calendar.getInstance().getTime());

        this.outEditText.setText(this.period.formatedEndTime());
        this.compute();
    }

    private void compute() {
//        this.period = new Period(this.inTime, this.outTime);
            durationText.setText(this.period.format());
    }

    public void onAccept(View view) {
        //this.periods.add(this.period) ;
        //((ArrayAdapter<Period>)this.durationList.getAdapter()).notifyDataSetChanged();
        ((ArrayAdapter<Period>)this.durationList.getAdapter()).add(this.period);

        mDB.create(this.period);

        this.inEditText.setText("");
        this.outEditText.setText("");
        this.durationText.setText("");

        this.dailyDuration = new Duration(this.dailyDuration.duration()+this.period.duration());
        this.durationOfTheDayText.setText(this.dailyDuration.format());

        //this.period = null ;
        Toast.makeText(this, "Period saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * On click the previous day button.
     * This reloads the duration for this new day and display them
     * @param view
     */
    public void onPreviousDay(View view) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.today);
        cal.add(Calendar.DAY_OF_YEAR, -1);
        this.today = cal.getTime();
        this.todayText.setText(sdf.format(this.today));
        this.loadExistingPeriods();

    }

    /**
     * On click the next day button.
     * This reloads the duration for this new day and display them
     * @param view
     */
    public void onNextDay(View view) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.today);
        cal.add(Calendar.DAY_OF_YEAR, 1);
        this.today = cal.getTime();
        this.todayText.setText(sdf.format(this.today));
        this.loadExistingPeriods();

    }

    private void loadExistingPeriods() {
        List<Period> periods = mDB.find(this.today);
        ((ArrayAdapter<Period>)this.durationList.getAdapter()).clear();
        ((ArrayAdapter<Period>)this.durationList.getAdapter()).addAll(periods);

        long sum = 0 ;
        for (Period period: periods) {
            sum += period.duration();
        }

        this.dailyDuration = new Duration(sum);
        Log.d("sum", this.dailyDuration.format());
        this.durationOfTheDayText.setText(this.dailyDuration.format());
    }

}
