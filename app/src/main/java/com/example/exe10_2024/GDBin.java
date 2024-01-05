package com.example.exe10_2024;

import static com.example.exe10_2024.GradeDB.STUDENT_ID;
import static com.example.exe10_2024.GradeDB.TABLE_GRADES;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class GDBin extends AppCompatActivity implements View.OnCreateContextMenuListener, AdapterView.OnItemSelectedListener {
    EditText adetsb,adettt,adetcq,adetgr;
    Spinner stselsp;
    String selection;
    String[] columns, selectionArgs;
    ArrayList<String> names, ids, selec;
    Cursor crsr;
    SQLiteDatabase db;
    HelperDB hlp;
    ArrayAdapter<String> adp;
    int curst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gdbin);

    }

    @Override
    protected void onResume() {
        super.onResume();
        initiall();
    }

    private void initiall() {
        hlp = new HelperDB(this);
        adetsb = findViewById(R.id.gradetsb);
        adetgr = findViewById(R.id.gradetgr);
        adettt = findViewById(R.id.gradettt);
        adetcq = findViewById(R.id.gradetcq);
        stselsp = (Spinner) findViewById(R.id.stselsp);
        names = new ArrayList<>();
        ids = new ArrayList<>();
        selec = new ArrayList<>();

        db = hlp.getWritableDatabase();
        columns = new String[]{StudentDB.NAME,StudentDB.ID};
        selection = StudentDB.ACTIVE+"=?";
        selectionArgs = new String[]{"1"};
        selec.add("Choose Student");

        crsr = db.query(StudentDB.TABLE_STUDENTS, columns, selection, selectionArgs, null, null, null);

        crsr.moveToFirst();
        while(!crsr.isAfterLast()){
            names.add(crsr.getString(Math.abs(crsr.getColumnIndex(StudentDB.NAME))));
            ids.add(crsr.getString(Math.abs(crsr.getColumnIndex(StudentDB.ID))));
            selec.add(crsr.getString(Math.abs(crsr.getColumnIndex(StudentDB.NAME)))+"-"+crsr.getString(Math.abs(crsr.getColumnIndex(StudentDB.ID))));
            crsr.moveToNext();
        }
        crsr.close();
        db.close();
        adp = new ArrayAdapter<>(this, androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, selec);
        stselsp.setAdapter(adp);
        stselsp.setOnItemSelectedListener(this);
    }

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

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.cbt){
            Intent si = new Intent(this,credits.class);
            startActivity(si);
        }
        else if (id ==R.id.sdbt) {
            Intent si = new Intent(this, SDBD.class);
            startActivity(si);
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


    public void addgr(View view) {
        if(curst>=0&&(!adetsb.getText().toString().equals(""))&&(!adetgr.getText().toString().equals(""))&&(!adettt.getText().toString().equals(""))&&(!adetcq.getText().toString().equals(""))){
            ContentValues cv = new ContentValues();
            cv.put(STUDENT_ID, ids.get(curst));
            cv.put(GradeDB.ACTIVEST,1);
            cv.put(GradeDB.SUBJECT,adetsb.getText().toString());
            cv.put(GradeDB.GRADE,adetgr.getText().toString());
            cv.put(GradeDB.TYPE,adettt.getText().toString());
            cv.put(GradeDB.QUARTER,adetcq.getText().toString());
            cv.put(GradeDB.STUDENT_NAME,names.get(curst));
            db = hlp.getWritableDatabase();
            db.insert(TABLE_GRADES, null, cv);
            db.close();
            finish();
        }
        else
            Toast.makeText(this, "Your'e Missing Some Data", Toast.LENGTH_SHORT).show();
    }

    /**
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        curst = i-1;
    }

    /**
     * @param adapterView
     */
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}