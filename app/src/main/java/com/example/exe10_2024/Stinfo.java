package com.example.exe10_2024;

import static com.example.exe10_2024.GradeDB.KEY_ID;
import static com.example.exe10_2024.GradeDB.STUDENT_ID;
import static com.example.exe10_2024.GradeDB.TABLE_GRADES;
import static com.example.exe10_2024.StudentDB.TABLE_STUDENTS;

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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Stinfo extends AppCompatActivity implements View.OnCreateContextMenuListener, AdapterView.OnItemClickListener{
    Intent gi;
    String id, name;
    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;
    TextView tvpn, tvpp, tvdn, tvdp, tvmn, tvmp, tvha, tvhp, tvid;
    ArrayList<String> grades, gid;
    int key, cur;
    ArrayAdapter<String> adp;
    ListView glst;
    AlertDialog.Builder adb;
    LinearLayout stchad, grchad;
    EditText adetpn, adetpp, adetdn, adetdp, adetmn, adetmp, adetha, adethp, adetgr, adetsb, adettt, adetcq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stinfo);



        initiall();

    }

    private void initiall() {
        tvpn = findViewById(R.id.tvpn);
        tvpp = findViewById(R.id.tvpp);
        tvdn = findViewById(R.id.tvdn);
        tvdp = findViewById(R.id.tvdp);
        tvmn = findViewById(R.id.tvmn);
        tvmp = findViewById(R.id.tvmp);
        tvha = findViewById(R.id.tvha);
        tvhp = findViewById(R.id.tvhp);
        tvid = findViewById(R.id.tvid);
        glst = findViewById(R.id.glst);
        hlp = new HelperDB(this);
        glst.setOnItemClickListener(this);

        gi = getIntent();
        id = gi.getStringExtra("id");
        updatest();
        updategd();
    }

    private void updategd() {

        db = hlp.getWritableDatabase();
        grades = new ArrayList<>();

        String[] columns = null;
        String selection = GradeDB.STUDENT_ID+"=?";
        String[] selectionArgs = {id};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;

        crsr = db.query(TABLE_GRADES, columns, selection, selectionArgs, groupBy, having, orderBy, limit);


        crsr.moveToFirst();
        while (!crsr.isAfterLast()) {
            grades.add(crsr.getString(Math.abs(crsr.getColumnIndex(GradeDB.SUBJECT)))+" - "+crsr.getString(Math.abs(crsr.getColumnIndex(GradeDB.GRADE))));
            crsr.moveToNext();
        }
        crsr.close();
        db.close();
        adp = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, grades);
        glst.setAdapter(adp);
    }

    private void updatest(){

        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        gid = new ArrayList<>();

        String[] columns = null;
        String selection = StudentDB.ID+"=?";
        String[] selectionArgs = {id};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;


        crsr = db.query(TABLE_STUDENTS, columns, selection, selectionArgs, groupBy, having, orderBy, limit);

        crsr.moveToFirst();
        key = crsr.getInt(Math.abs(crsr.getColumnIndex(StudentDB.KEY_ID)));
        name = crsr.getString(Math.abs(crsr.getColumnIndex(StudentDB.NAME)));

        tvid.setText("Student - "+id);
        tvpn.setText("Name: "+crsr.getString(Math.abs(crsr.getColumnIndex(StudentDB.NAME))));
        tvpp.setText("Phone: "+crsr.getString(Math.abs(crsr.getColumnIndex(StudentDB.PHONE))));
        tvdn.setText("Dad: "+crsr.getString(Math.abs(crsr.getColumnIndex(StudentDB.DAD_NAME))));
        tvdp.setText("Phone: "+crsr.getString(Math.abs(crsr.getColumnIndex(StudentDB.DAD_PHONE))));
        tvmn.setText("Mom: "+crsr.getString(Math.abs(crsr.getColumnIndex(StudentDB.MOM_NAME))));
        tvmp.setText("Phone: "+crsr.getString(Math.abs(crsr.getColumnIndex(StudentDB.MOM_PHONE))));
        tvha.setText("Address: "+crsr.getString(Math.abs(crsr.getColumnIndex(StudentDB.ADDRESS))));
        tvhp.setText("Home Phone: "+crsr.getString(Math.abs(crsr.getColumnIndex(StudentDB.HOME_PHONE))));

        gid.add(crsr.getString(Math.abs(crsr.getColumnIndex(StudentDB.KEY_ID))));


        crsr.close();
        db.close();
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

    public void change(View view) {
        stchad = (LinearLayout) getLayoutInflater().inflate(R.layout.stchad, null);
        adetpn = stchad.findViewById(R.id.adetpn);
        adetpp = stchad.findViewById(R.id.adetpp);
        adetdn = stchad.findViewById(R.id.adetdn);
        adetdp = stchad.findViewById(R.id.adetdp);
        adetmn = stchad.findViewById(R.id.adetmn);
        adetmp = stchad.findViewById(R.id.adetmp);
        adetha = stchad.findViewById(R.id.adetha);
        adethp = stchad.findViewById(R.id.adethp);

        adb = new AlertDialog.Builder(this);

        adb.setView(stchad);
        adb.setTitle(tvid.getText());
        adb.setPositiveButton("Confirm", click);
        adb.setNeutralButton("Cancel", click);
        adb.setNegativeButton("Delete\nRecord", click);
        adb.show();
    }
    DialogInterface.OnClickListener click = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(which == DialogInterface.BUTTON_POSITIVE){
                ContentValues cv = new ContentValues();
                db = hlp.getWritableDatabase();
                if(!adetpn.getText().toString().equals(""))cv.put(StudentDB.NAME,adetpn.getText().toString());db.update(TABLE_STUDENTS,cv,StudentDB.KEY_ID+"=?", new String[]{key+""});
                if(!adetpp.getText().toString().equals(""))cv.put(StudentDB.PHONE,adetpp.getText().toString());db.update(TABLE_STUDENTS,cv,StudentDB.KEY_ID+"=?", new String[]{key+""});
                if(!adetdn.getText().toString().equals(""))cv.put(StudentDB.DAD_NAME,adetdn.getText().toString());db.update(TABLE_STUDENTS,cv,StudentDB.KEY_ID+"=?", new String[]{key+""});
                if(!adetdp.getText().toString().equals(""))cv.put(StudentDB.DAD_PHONE,adetdp.getText().toString());db.update(TABLE_STUDENTS,cv,StudentDB.KEY_ID+"=?", new String[]{key+""});
                if(!adetmn.getText().toString().equals(""))cv.put(StudentDB.MOM_NAME,adetmn.getText().toString());db.update(TABLE_STUDENTS,cv,StudentDB.KEY_ID+"=?", new String[]{key+""});
                if(!adetmp.getText().toString().equals(""))cv.put(StudentDB.MOM_PHONE,adetmp.getText().toString());db.update(TABLE_STUDENTS,cv,StudentDB.KEY_ID+"=?", new String[]{key+""});
                if(!adetha.getText().toString().equals(""))cv.put(StudentDB.ADDRESS,adetha.getText().toString());db.update(TABLE_STUDENTS,cv,StudentDB.KEY_ID+"=?", new String[]{key+""});
                if(!adethp.getText().toString().equals(""))cv.put(StudentDB.HOME_PHONE,adethp.getText().toString());db.update(TABLE_STUDENTS,cv,StudentDB.KEY_ID+"=?", new String[]{key+""});
                db.close();
                updatest();

            }
            if(which == DialogInterface.BUTTON_NEGATIVE){
                ContentValues cv = new ContentValues();
                db = hlp.getWritableDatabase();
                cv.put(StudentDB.ACTIVE,0);
                db.update(TABLE_STUDENTS,cv,StudentDB.KEY_ID+"=?", new String[]{key+""});
                cv.put(GradeDB.ACTIVEST,0);
                db.update(TABLE_STUDENTS,cv, STUDENT_ID+"=?", new String[]{id+""});
                db.close();
                finish();

            }
            if(which == DialogInterface.BUTTON_NEUTRAL){
                dialog.cancel();
            }
        }
    };

    public void add(View view) {
            grchad = (LinearLayout)getLayoutInflater().inflate(R.layout.grchad,null);
            adetsb = grchad.findViewById(R.id.adetsb);
            adetgr = grchad.findViewById(R.id.adetgr);
            adettt = grchad.findViewById(R.id.adettt);
            adetcq = grchad.findViewById(R.id.adetcq);

            adb = new AlertDialog.Builder(this);
            adb.setView(grchad);
            adb.setTitle("Grade for "+id);
            adb.setPositiveButton("Confirm", gadd);
            adb.setNegativeButton("Cancel", gadd);
            adb.show();


    }

    DialogInterface.OnClickListener gadd = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(which == DialogInterface.BUTTON_POSITIVE){
                if((!adetsb.getText().toString().equals(""))&&(!adetgr.getText().toString().equals(""))&&(!adettt.getText().toString().equals(""))&&(!adetcq.getText().toString().equals(""))){
                    ContentValues cv = new ContentValues();
                    cv.put(STUDENT_ID, id);
                    cv.put(GradeDB.ACTIVEST,1);
                    cv.put(GradeDB.SUBJECT,adetsb.getText().toString());
                    cv.put(GradeDB.GRADE,adetgr.getText().toString());
                    cv.put(GradeDB.TYPE,adettt.getText().toString());
                    cv.put(GradeDB.QUARTER,adetcq.getText().toString());
                    cv.put(GradeDB.STUDENT_NAME,name);
                    db = hlp.getWritableDatabase();
                    db.insert(TABLE_GRADES, null, cv);
                    db.close();
                    updategd();
                }
                else Toast.makeText(Stinfo.this, "You're Missing Some Data", Toast.LENGTH_SHORT).show();
            }
            if(which == DialogInterface.BUTTON_NEGATIVE){
                dialog.cancel();
            }
        }
    };


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
        grchad = (LinearLayout)getLayoutInflater().inflate(R.layout.grchad,null);
        adetsb = grchad.findViewById(R.id.adetsb);
        adetgr = grchad.findViewById(R.id.adetgr);
        adettt = grchad.findViewById(R.id.adettt);
        adetcq = grchad.findViewById(R.id.adetcq);
        cur = Integer.parseInt(gid.get(pos));

        adb = new AlertDialog.Builder(this);
        adb.setView(grchad);
        adb.setTitle("Grade for "+name);
        adb.setPositiveButton("Confirm", gedit);
        adb.setNeutralButton("Cancel", gedit);
        adb.setNegativeButton("Delete\nRecord", gedit);
        adb.show();
    }

    DialogInterface.OnClickListener gedit = new DialogInterface.OnClickListener() {
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
                updatest();

            }
            if(which == DialogInterface.BUTTON_NEGATIVE){
                ContentValues cv = new ContentValues();
                db = hlp.getWritableDatabase();
                db.delete(TABLE_GRADES, KEY_ID+"=?", new String[]{Integer.toString(cur)});
                db.close();
                updategd();

            }
            if(which == DialogInterface.BUTTON_NEUTRAL){
                dialog.cancel();
            }
        }
    };
}