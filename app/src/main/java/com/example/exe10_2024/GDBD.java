package com.example.exe10_2024;

import static com.example.exe10_2024.GradeDB.KEY_ID;
import static com.example.exe10_2024.GradeDB.STUDENT_ID;
import static com.example.exe10_2024.GradeDB.TABLE_GRADES;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class GDBD extends AppCompatActivity implements View.OnCreateContextMenuListener, AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
    Spinner sfsp;
    ListView lstg;
    String[] spin = {"None","Only Math","By Subject","Group By Quarter"};
    int fil=0, cur;
    SQLiteDatabase db;
    ArrayList<String> grades, ids, names;
    Cursor crsr;
    HelperDB hlp;
    String[] columns, selectionArgs;
    String selection, orderBy, tmp, tmpn;
    ArrayAdapter<String> adpf;
    EditText adetgr, adetsb, adettt, adetcq;
    AlertDialog.Builder adb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gdbd);
        sfsp = findViewById(R.id.sfsp);
        lstg = findViewById(R.id.lstg);
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,spin);
        sfsp.setAdapter(adp);
        sfsp.setOnItemSelectedListener(this);
        hlp = new HelperDB(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updatelst();
    }

    private void updatelst() {
        db = hlp.getWritableDatabase();
        grades = new ArrayList<>();
        ids = new ArrayList<>();
        names = new ArrayList<>();
        columns = null;
        switch (fil){
            case 0://all grades
                selection = GradeDB.ACTIVEST+"=?";
                selectionArgs = new String[]{"1"};

                crsr = db.query(GradeDB.TABLE_GRADES, columns, selection, selectionArgs, null, null, null);
                break;
            case 1://only Math
                selection = GradeDB.SUBJECT+"=?";
                selectionArgs = new String[]{"Math"};
                crsr = db.query(GradeDB.TABLE_GRADES, columns, selection, selectionArgs, null, null, null);
                break;
            case 2://By Subject
                orderBy = GradeDB.SUBJECT;

                crsr = db.query(GradeDB.TABLE_GRADES, columns, null, null, null, null, orderBy);
                break;
            case 3://Group By Quarter
                orderBy = GradeDB.QUARTER;

                crsr = db.query(GradeDB.TABLE_GRADES, columns, null, null, null, null, orderBy);
                break;
        }
        crsr.moveToFirst();
        while ((!crsr.isAfterLast())){
            if(crsr.getString(Math.abs(crsr.getColumnIndex(GradeDB.ACTIVEST))).equals("1")) {
                tmp = "";
                tmpn = crsr.getString(Math.abs(crsr.getColumnIndex(GradeDB.STUDENT_NAME)));
                ids.add(crsr.getString(Math.abs(crsr.getColumnIndex(GradeDB.KEY_ID))));
                names.add(tmpn);

                tmp += tmpn + "-";
                tmp += crsr.getString(Math.abs(crsr.getColumnIndex(GradeDB.SUBJECT))) + "-";
                tmp += crsr.getString(Math.abs(crsr.getColumnIndex(GradeDB.TYPE))) + "-";
                tmp += crsr.getString(Math.abs(crsr.getColumnIndex(GradeDB.GRADE))) + "- Quarter: ";
                tmp += crsr.getString(Math.abs(crsr.getColumnIndex(GradeDB.QUARTER)));
                grades.add(tmp);
            }
            crsr.moveToNext();
        }
        crsr.close();
        db.close();
        adpf = new ArrayAdapter<>(this, androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, grades);
        lstg.setAdapter(adpf);
        lstg.setOnItemClickListener(this);
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
            Intent si = new Intent(this, SDBD.class);
            startActivity(si);
        }
        else if (id ==R.id.gdbt) {

        }
        else if (id ==R.id.sibt) {
            Intent si = new Intent(this, SDBin.class);
            startActivity(si);
        }
        return true;
    }


    /**
     * @param parent
     * @param view
     * @param pos
     * @param rowid
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long rowid) {
        fil = pos;
        updatelst();
    }

    /**
     * @param adapterView
     */
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * @param adapterView
     * @param view
     * @param pos
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
        LinearLayout grchad = (LinearLayout) getLayoutInflater().inflate(R.layout.grchad, null);
        adetsb = grchad.findViewById(R.id.adetsb);
        adetgr = grchad.findViewById(R.id.adetgr);
        adettt = grchad.findViewById(R.id.adettt);
        adetcq = grchad.findViewById(R.id.adetcq);
        cur = Integer.parseInt(ids.get(pos));

        adb = new AlertDialog.Builder(this);
        adb.setView(grchad);
        adb.setTitle("Grade for "+names.get(pos));
        adb.setPositiveButton("Confirm", geditt);
        adb.setNeutralButton("Cancel", geditt);
        adb.setNegativeButton("Delete\nRecord", geditt);
        adb.show();
    }
    DialogInterface.OnClickListener geditt = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(which == DialogInterface.BUTTON_POSITIVE){
                ContentValues cv = new ContentValues();
                db = hlp.getWritableDatabase();
                if(!adetsb.getText().toString().equals("")){cv.put(GradeDB.SUBJECT,adetsb.getText().toString());db.update(TABLE_GRADES,cv,GradeDB.KEY_ID+"=?", new String[]{cur+""});}
                if(!adetgr.getText().toString().equals("")){cv.put(GradeDB.GRADE,adetgr.getText().toString());db.update(TABLE_GRADES,cv,GradeDB.KEY_ID+"=?", new String[]{cur+""});}
                if(!adettt.getText().toString().equals("")){cv.put(GradeDB.TYPE,adettt.getText().toString());db.update(TABLE_GRADES,cv,GradeDB.KEY_ID+"=?", new String[]{cur+""});}
                if(!adetcq.getText().toString().equals("")){cv.put(GradeDB.QUARTER,adetcq.getText().toString());db.update(TABLE_GRADES,cv,GradeDB.KEY_ID+"=?", new String[]{cur+""});}

                db.close();
                updatelst();

            }
            if(which == DialogInterface.BUTTON_NEGATIVE){
                ContentValues cv = new ContentValues();
                db = hlp.getWritableDatabase();
                db.delete(TABLE_GRADES, KEY_ID+"=?", new String[]{Integer.toString(cur)});
                db.close();
                updatelst();

            }
            if(which == DialogInterface.BUTTON_NEUTRAL){
                dialog.cancel();
            }
        }
    };

    public void addg(View view) {
        Intent si = new Intent(this, GDBin.class);
        startActivity(si);
    }
}