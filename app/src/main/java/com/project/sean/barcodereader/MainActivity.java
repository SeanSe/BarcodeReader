package com.project.sean.barcodereader;

import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private Button scanBtn;
    private Button addBtn;
    private Button viewBtn;

    private TextView formatTxt;
    private TextView contentTxt;

    private EditText editItemName;
    private EditText editItemPrice;

    private DatabaseHelper myDb;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myDb = new DatabaseHelper(this);

        scanBtn = (Button)findViewById(R.id.scan_button);
        addBtn = (Button)findViewById(R.id.button_add);
        viewBtn = (Button)findViewById(R.id.button_view);

        formatTxt = (TextView)findViewById(R.id.scan_format);
        contentTxt = (TextView)findViewById(R.id.scan_content);

        editItemName = (EditText)findViewById(R.id.editItemName);
        editItemPrice = (EditText)findViewById(R.id.editItemPrice);

        scanBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        viewBtn.setOnClickListener(this);
    }

    /**
     *
     * @param v
     */
    public void onClick(View v){
        if(v.getId()==R.id.scan_button) {
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
        if(v.getId()==R.id.button_add) {
            addData();
        }
        if(v.getId()==R.id.button_view) {
            viewData();
        }
    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            formatTxt.setText("FORMAT: " + scanFormat);
            contentTxt.setText(scanContent);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     *
     */
    public void addData(){
        boolean isInserted = myDb.insertData(contentTxt.getText().toString(),
                editItemName.getText().toString(),
                editItemPrice.getText().toString());
        if(isInserted == true) {
            Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
            contentTxt.setText("");
            editItemName.getText().clear();
            editItemPrice.getText().clear();
        } else {
            Toast.makeText(MainActivity.this, "Data not inserted", Toast.LENGTH_LONG).show();
        }
    }

    /**
     *
     */
    public void viewData(){
        Cursor result = myDb.getallData();
        if(result.getCount() == 0) {
            //Show error message
            showMessage("Error", "No data found.");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        //Moves cursor to the next result
        //index 0 - ID
        //index 1 - Name
        //index 2 - Surname
        //index 3 - Marks
        while (result.moveToNext()) {
            buffer.append("ID: " + result.getString(0) + "\n");
            buffer.append("Item Name: " + result.getString(1) + "\n");
            buffer.append("Item Price: " + result.getString(2) + "\n");
        }

        //Show all data
        showMessage("Product Information", buffer.toString());
    }

    /**
     * Used to create alert dialogue.
     */
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true); //Cancel after is use
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show(); //Will show the AlertDialog
    }
}
