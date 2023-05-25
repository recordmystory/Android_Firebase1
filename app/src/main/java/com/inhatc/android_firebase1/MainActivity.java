package com.inhatc.android_firebase1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseDatabase myFirebase;
    DatabaseReference myDB_Reference = null;

    TextView txtFirebase;
    EditText edtCustomerName;
    Button btnInsert;
    String strHeader = "Customer Information";
    String strCName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtFirebase = (TextView) findViewById(R.id.txtFirebase);
        edtCustomerName = (EditText) findViewById(R.id.edtCustomerName);

        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(this);

        myFirebase = FirebaseDatabase.getInstance();
        myDB_Reference = myFirebase.getReference();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnInsert:
                strCName = edtCustomerName.getText().toString();
                if (!strCName.equals("")) {
                    mSet_FirebaseDatabase(true);
                    mGet_FirebaseDatabase();
                }
                edtCustomerName.setText("");
                break;
            default:
                edtCustomerName.setText("");
                break;
        }
    }

        private void mGet_FirebaseDatabase () {
            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    txtFirebase.setText("Firebase Value: ");
                    Object objValue = dataSnapshot.getValue(Object.class);
                    txtFirebase.append("\n" + objValue);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError dbError) {
                    Log.w("Tag: ", "Failed to read value", dbError.toException());
                }
            };

            Query sortbyName = FirebaseDatabase.getInstance().getReference().child(strHeader).orderByChild(strCName);
            sortbyName.addListenerForSingleValueEvent(postListener);
        }

        private void mSet_FirebaseDatabase(boolean bFlag){
            if (bFlag) {
                myDB_Reference.child(strHeader).push().setValue(strCName);
            }
        }
    }
