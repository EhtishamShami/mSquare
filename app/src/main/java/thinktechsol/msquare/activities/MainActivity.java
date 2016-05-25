package thinktechsol.msquare.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import thinktechsol.msquare.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView simpleCustomeListView = (ListView) findViewById(R.id.listView);

//        Item weather_data[] = new Item[]
//                {
//                        new Item(R.drawable.access_icon, "Access File"),
//                        new Item(R.drawable.doc_icon, "Word File"),
//                        new Item(R.drawable.excel_icon, "Excel File"),
//                        new Item(R.drawable.txt_icon, "Text File"),
//                        new Item(R.drawable.access_icon, "Access File"),
//                        new Item(R.drawable.doc_icon, "Word File"),
//                        new Item(R.drawable.excel_icon, "Excel File"),
//                        new Item(R.drawable.txt_icon, "Text File")
//                };
//        ItemAdapter myAdapter = new ItemAdapter(this,
//                R.layout.listview_item_row, weather_data);
//        simpleCustomeListView.setAdapter(myAdapter);
    }
}
