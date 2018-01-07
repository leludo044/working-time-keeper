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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        this.inEditText.setText("");
        this.outEditText.setText("");
        this.durationText.setText("");

        //this.duration = null ;
        Toast.makeText(this, "Duration saved", Toast.LENGTH_SHORT).show();
    }
}
