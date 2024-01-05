package com.example.exe10_2024;
/**
 * @author		Yiftah David yd2058@bs.amalnet.k12.il
 * @version	1.1
 * @since		13/12/2023 (the date of the package the class was added)
 * this activity manages the input for the student database
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SDBin extends AppCompatActivity implements View.OnCreateContextMenuListener {
    private SQLiteDatabase db;
    private HelperDB hlp;
    EditText etpn, etpp, etdn, etdp, etmn, etmp, etha, ethp, etid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdbin);


        initAll();

    }
    /**
     * This function initiates all display components to connect them to java file.
     * <p>
     *
     *
     */

    private void initAll() {
        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();

        etpn = findViewById(R.id.etpn);
        etpp = findViewById(R.id.etpp);
        etdn = findViewById(R.id.etdn);
        etdp = findViewById(R.id.etdp);
        etmn = findViewById(R.id.etmn);
        etmp = findViewById(R.id.etmp);
        etha = findViewById(R.id.etha);
        ethp = findViewById(R.id.ethp);
        etid = findViewById(R.id.etid);
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
        return true;
    }
    /**
     * this function saves to student database a new record.
     * <p>
     *
     * @param	view Description	refers to current activity xml file.
     */

    public void srecsave(View view) {
        if(takin()) {
            ContentValues cv = new ContentValues();

            cv.put(StudentDB.ID, etid.getText().toString());
            cv.put(StudentDB.NAME,etpn.getText().toString());
            cv.put(StudentDB.PHONE,etpp.getText().toString());
            cv.put(StudentDB.ADDRESS,etha.getText().toString());
            cv.put(StudentDB.HOME_PHONE,ethp.getText().toString());
            if(etdn.getText().toString().equals("")) {
                cv.put(StudentDB.DAD_NAME, "");
                cv.put(StudentDB.DAD_PHONE, "");
            }
            else{
                cv.put(StudentDB.DAD_NAME, etdn.getText().toString());
                cv.put(StudentDB.DAD_PHONE, etdp.getText().toString());
            }
            if(etmn.getText().toString().equals("")) {
                cv.put(StudentDB.MOM_NAME, "");
                cv.put(StudentDB.MOM_PHONE, "");
            }
            else{
                cv.put(StudentDB.MOM_NAME, etmn.getText().toString());
                cv.put(StudentDB.MOM_PHONE, etmp.getText().toString());
            }
            cv.put(StudentDB.ACTIVE,1);


            db = hlp.getWritableDatabase();
            db.insert(StudentDB.TABLE_STUDENTS, null, cv);
            db.close();
            Toast.makeText(this, "record saved", Toast.LENGTH_LONG).show();
        }
        else Toast.makeText(this, "you are missing some data", Toast.LENGTH_LONG).show();
    }
    /**
     * this function returns if all necessary data inputs have been filled.
     * <p>
     *
     */

    public boolean takin(){
        return(!(etid.getText().toString().equals(""))&&
                !(etpn.getText().toString().equals(""))&&
                !(etpp.getText().toString().equals(""))&&
                !(etha.getText().toString().equals(""))&&
                !(ethp.getText().toString().equals(""))&&
                (!(etdn.getText().toString().equals("")&&etdp.getText().toString().equals(""))||!(etmn.getText().toString().equals("")&&etmp.getText().toString().equals(""))));
    }


}