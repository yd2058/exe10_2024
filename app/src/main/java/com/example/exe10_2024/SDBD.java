package com.example.exe10_2024;
/**
 * @author		Yiftah David yd2058@bs.amalnet.k12.il
 * @version	    1.1
 * @since		13/12/2023
 * this is a students display screen
 */
import static com.example.exe10_2024.StudentDB.ID;
import static com.example.exe10_2024.StudentDB.NAME;
import static com.example.exe10_2024.StudentDB.TABLE_STUDENTS;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SDBD extends AppCompatActivity implements View.OnCreateContextMenuListener, AdapterView.OnItemClickListener {
    EditText set;
    ArrayAdapter<String> adp;
    ListView slst;
    ArrayList<String> students, idss;
    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdbd);
        initiall();
    }

    @Override
    protected void onResume() {
        super.onResume();
        stupdate();
    }
    /**
     * Updates the student summary listview to all active students.
     * <p>
     *
     *
     */

    private void stupdate() {
        students = new ArrayList<>();
        idss = new ArrayList<>();
        hlp = new HelperDB(this);
        db = hlp.getReadableDatabase();

        String[] columns = {NAME, ID};
        String selection = StudentDB.ACTIVE+"=?";
        String[] selectionArgs = {"1"};

        crsr = db.query(TABLE_STUDENTS, columns, selection, selectionArgs, null, null, null);
        int nam = crsr.getColumnIndex(NAME);
        int ids = crsr.getColumnIndex(ID);

        crsr.moveToFirst();
        while(!crsr.isAfterLast()){
            idss.add(crsr.getString(ids));
            students.add(crsr.getString(nam)+" - "+crsr.getString(ids));
            crsr.moveToNext();
        }
        crsr.close();
        db.close();

        adp = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, students);
        slst.setAdapter(adp);
        slst.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        slst.setOnItemClickListener(this);
    }
    /**
     * initiates all necessary display parts and connects them for use.
     * <p>
     *
     *
     */

    private void initiall() {
        slst = findViewById(R.id.slst);
        stupdate();
        Toast.makeText(this, "Click A Student For More Info", Toast.LENGTH_SHORT).show();

    }


    /**
     * creates the context menu.
     * <p>
     *
     * @param	menu Description	refers to the current context menu.
     * @return	Description			returns a super action of this function.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }
    /**
     * reacts to item selection.
     * <p>
     *
     * @param	item Description	refers to the selected menu item.
     * @return	Description			returns true.
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.cbt){
            Intent si = new Intent(this,credits.class);
            startActivity(si);
        }
        else if (id ==R.id.sdbt) {

        }
        else if (id ==R.id.gdbt) {
            Intent si = new Intent(this, GDBD.class);
            startActivity(si);
        }
        else if (id ==R.id.sibt) {
            Intent si = new Intent(this, SDBin.class);
            startActivity(si);
        }
        return true;
    }
    /**
     * Reacts to selection of student and sends to full view of student record.
     * <p>
     *
     * @param adapterView refers to adapter used for the click.
     * @param view refers to current activity.
     * @param pos refers to position in array selected.
     * @param id refers to line id of listview.
     */

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        Intent si = new Intent(this, Stinfo.class);
        si.putExtra("id",idss.get(pos));
        startActivity(si);
    }

}