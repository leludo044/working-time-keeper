package net.leludo.wtk;

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

    private Date inTime;
    private Date outTime;
    private Date today;
    SimpleDateFormat sdf;
    TextView durationText;
    TextView durationOfTheDayText;
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

        Log.d("lifecylce", "onCreate");
        mDB = new PeriodDatabase(this);

        today = Calendar.getInstance().getTime();
        sdf = new SimpleDateFormat("EEEE dd MMMM", Locale.FRANCE);

        TextView todayText = (TextView) findViewById(R.id.todayText);
        todayText.setText(sdf.format(today));

        this.inEditText = (EditText) findViewById(R.id.inTime);
        this.outEditText = (EditText) findViewById(R.id.outTime);

        durationText = (TextView) findViewById(R.id.durationText);
        durationOfTheDayText = (TextView) findViewById(R.id.durationOfTheDayText);
        durationList = (ListView) findViewById(R.id.durationList);
        //periods.add(new Period());
        durationList.setAdapter(new ArrayAdapter<Period>(this, android.R.layout.simple_list_item_1, periods));

        this.loadExistingPeriods();
    }

    public void onClickClose(View view) {
        finish();
    }

    public void onIn(View view) {
        this.inTime = Calendar.getInstance().getTime();

        this.inEditText.setText(String.format("%tT", this.inTime));
        this.outEditText.setText("");
    }

    public void onOut(View view) {
        this.outTime = Calendar.getInstance().getTime();

        this.outEditText.setText(String.format("%tT", this.outTime));
        this.compute();
    }

    private void compute() {
        this.period = new Period(this.inTime, this.outTime);
            durationText.setText(period.format());
    }

    public void onAccept(View view) {
        //this.periods.add(this.period) ;
        //((ArrayAdapter<Period>)this.durationList.getAdapter()).notifyDataSetChanged();
        ((ArrayAdapter<Period>)this.durationList.getAdapter()).add(this.period);

        mDB.create(this.inTime, this.outTime);

        this.inEditText.setText("");
        this.outEditText.setText("");
        this.durationText.setText("");
        this.inTime = null ;
        this.outTime = null;

        this.dailyDuration = new Duration(this.dailyDuration.duration()+this.period.duration());
        this.durationOfTheDayText.setText(this.dailyDuration.format());

        //this.period = null ;
        Toast.makeText(this, "Period saved", Toast.LENGTH_SHORT).show();
    }

    private void loadExistingPeriods() {
        List<Period> periods = mDB.find(this.today);
        ((ArrayAdapter<Period>)this.durationList.getAdapter()).addAll(periods);

        long sum = 0 ;
        for (Period period: periods) {
            sum += period.duration();
        }

        this.dailyDuration = new Duration(sum);
        Log.d("sum", this.dailyDuration.format());
        this.durationOfTheDayText.setText(this.dailyDuration.format());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.inTime != null) {
            outState.putLong("in", this.inTime.getTime());
        } else {
            outState.putLong("in", 0L);
        }
        if (this.outTime != null) {
            outState.putLong("out", this.outTime.getTime());
        }
        else {
            outState.putLong("out", 0L);
        }
        Log.i("state", "saved");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Calendar cal = Calendar.getInstance();
        long inTimeInMillis = savedInstanceState.getLong("in") ;
        if (inTimeInMillis != 0L) {
            cal.setTimeInMillis(inTimeInMillis);
            this.inTime = cal.getTime();
            this.inEditText.setText(String.format("%tT", this.inTime));
        }
        long outTimeInMillis = savedInstanceState.getLong("out") ;
        if (outTimeInMillis != 0L) {
            cal.setTimeInMillis(outTimeInMillis);
            this.outTime = cal.getTime();
            this.compute();
            this.outEditText.setText(String.format("%tT", this.outTime));
        }
        Log.i("state", "restored");
    }
}
