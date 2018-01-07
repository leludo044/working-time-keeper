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
    EditText inEditText;
    EditText outEditText;
    ListView durationList;
    List<Duration> durations = new ArrayList<>();
    Duration duration ;
    VacationDatabase mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDB = new VacationDatabase(this);

        today = Calendar.getInstance().getTime();
        sdf = new SimpleDateFormat("EEEE dd MMMM", Locale.FRANCE);

        TextView todayText = (TextView) findViewById(R.id.todayText);
        todayText.setText(sdf.format(today));

        this.inEditText = (EditText) findViewById(R.id.inTime);
        this.outEditText = (EditText) findViewById(R.id.outTime);

        durationText = (TextView) findViewById(R.id.durationText);
        durationList = (ListView) findViewById(R.id.durationList);
        //durations.add(new Duration());
        durationList.setAdapter(new ArrayAdapter<Duration>(this, android.R.layout.simple_list_item_1, durations));
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
        this.duration = new Duration(this.inTime, this.outTime);
            durationText.setText(duration.format());
    }

    public void onAccept(View view) {
        //this.durations.add(this.duration) ;
        //((ArrayAdapter<Duration>)this.durationList.getAdapter()).notifyDataSetChanged();
        ((ArrayAdapter<Duration>)this.durationList.getAdapter()).add(this.duration);

        mDB.create(this.inTime, this.outTime);

        this.inEditText.setText("");
        this.outEditText.setText("");
        this.durationText.setText("");
        this.inTime = null ;
        this.outTime = null;

        //this.duration = null ;
        Toast.makeText(this, "Duration saved", Toast.LENGTH_SHORT).show();
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
