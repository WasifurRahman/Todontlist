package inc.onix.todontlist;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //String[] item = {"Task1", "Task2", "Task3", "Task4"};

    ArrayList<TaskItem> items = new ArrayList<TaskItem>();

    ListView list1;
    DBHelper helper;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new DBHelper(this);
        //helper.onCreate(helper.getWritableDatabase());

        list1 = (ListView) findViewById(R.id.ourListview);

        //final ArrayAdapter<String> adpt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, item);

        //list1.setAdapter(adpt);



        final AmaderAdapter adpt = new AmaderAdapter();
        list1.setAdapter(adpt);

        populateFromDatabase(adpt);

        ImageButton btnAdd = (ImageButton) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txt1 = (TextView) findViewById(R.id.txtTask);
                TextView txt2 = (TextView) findViewById(R.id.txtCause);
                TaskItem newItem = new TaskItem(txt1.getText().toString(), txt2.getText().toString());
                helper.insertTask(newItem);
                populateFromDatabase(adpt);

            }
        });

        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView lbl = (TextView) findViewById(R.id.lblSelected);
                lbl.setText(items.get(position).toString());
            }
        });
        LayoutTransition layoutT  = list1.getLayoutTransition();
        layoutT.enableTransitionType(LayoutTransition.APPEARING);
        layoutT.enableTransitionType(LayoutTransition.DISAPPEARING);
        layoutT.enableTransitionType(LayoutTransition.CHANGE_APPEARING);
        layoutT.enableTransitionType(LayoutTransition.CHANGE_DISAPPEARING);
        layoutT.enableTransitionType(LayoutTransition.CHANGING);
    }

    private void populateFromDatabase(AmaderAdapter adpt) {

        items.clear();
        adpt.notifyDataSetChanged();

        items.addAll(helper.retrieveData());
        adpt.notifyDataSetChanged();
    }

    public class AmaderAdapter extends ArrayAdapter<TaskItem>
    {
        public AmaderAdapter()
        {
            super(getApplicationContext(), R.layout.task_row, items);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View amaderView = convertView;
            if (amaderView == null)
                amaderView = getLayoutInflater().inflate(R.layout.task_row, parent, false);

            TextView t1 = (TextView) amaderView.findViewById(R.id.txt1);

            TextView t2 = (TextView) amaderView.findViewById(R.id.txt2);
            ImageButton btnDel = (ImageButton) amaderView.findViewById(R.id.btnDel);

            btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TaskItem taskToBeDeleted = items.get(position);
                    int rowId = taskToBeDeleted.rowId;
                    helper.deleteEntry(rowId);
                    items.remove(position);
                    notifyDataSetChanged();
                }
            });

            btnDel.setFocusable(false);

            t1.setText(items.get(position).Task);


            t2.setText(items.get(position).Cause);

            return amaderView;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
