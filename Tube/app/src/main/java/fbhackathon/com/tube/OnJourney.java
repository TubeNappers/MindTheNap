package fbhackathon.com.tube;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import fbhackathon.com.tube.MapData.Line;
import fbhackathon.com.tube.MapData.MapMaker;
import fbhackathon.com.tube.MapData.Station;

public class OnJourney extends AppCompatActivity {

    private Station current;
    private Station destination;
    private Line line;
    private TextView currentTextView;
    private TextView destinationTextView;
    private ListView stopsListView;
    private Button startJourneyButton;
    private boolean direction;
    private List<String> stops = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_journey);
        line = (Line) getIntent().getSerializableExtra("line");
        current = (Station) getIntent().getSerializableExtra("start");
        destination = (Station) getIntent().getSerializableExtra("end");
        currentTextView = (TextView) findViewById(R.id.current_station);
        destinationTextView = (TextView) findViewById(R.id.end_station);
        stopsListView = (ListView) findViewById(R.id.remaining_stations);
        startJourneyButton = (Button) findViewById(R.id.start_journey_button);

        currentTextView.setText(current.getName());
        destinationTextView.setText(destination.getName());
        direction = line.findDirection(current, destination);
        makeListOfStops();

        startJourneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OnJourney.this, SpeechInputNewActivity.class);
                String[] stopsArr = (String[]) stops.toArray();
                intent.putExtra("stops", stopsArr);
                startActivity(intent);
            }
        }
        );
    }

    private void makeListOfStops() {
        Station current = this.current;
        while (!line.getNext(current, direction).equals(destination)) {
            stops.add(line.getNext(current, direction).getName());
            current = line.getNext(current, direction);
        }
        stops.add(destination.getName());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stops);
        stopsListView.setAdapter(adapter);
    }


}
