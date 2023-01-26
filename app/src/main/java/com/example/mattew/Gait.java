package com.example.mattew;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Set;

public class Gait extends AppCompatActivity {
    private int BLUETOOTH_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gait);

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Button btnblue = (Button) findViewById(R.id.bluetooth);
        Button back = (Button) findViewById(R.id.back);
        Button pair = (Button) findViewById(R.id.pair);
        ListView list = (ListView) findViewById(R.id.list);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Gait.this, MainActivity.class));
            }
        });

        ActivityResultLauncher<Intent> startForResult =
                registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        new ActivityResultCallback<ActivityResult>() {
                            @Override
                            public void onActivityResult(ActivityResult activityResult) {
                                int result = activityResult.getResultCode();
                                Intent intent = activityResult.getData();
                                if (result == RESULT_OK) {
                                    Toast.makeText(Gait.this, "Bluetooth turned on", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

        btnblue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(Gait.this,
                        Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                } else {
                    requestBluetoothConnection();
                }

                if (bluetoothAdapter == null) {
                    // Device doesn't support Bluetooth
                    Toast.makeText(Gait.this, "error: bluetooth not supported", Toast.LENGTH_LONG).show();
                } else if (!bluetoothAdapter.isEnabled()) {
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startForResult.launch(intent);
                } else {
                    bluetoothAdapter.disable();
                }
            }
        });

        pair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (ActivityCompat.checkSelfPermission(Gait.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
//                   return;
//                }
//                Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
//                String[] strings = new String[pairedDevices.size()];
//                int index = 0;
//
//                if(pairedDevices.size()>0){
//                    for(BluetoothDevice device:pairedDevices){
//                        strings[index]= device.getName();
//                        index++;
//                    }
//                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,strings);
//                    list.setAdapter(arrayAdapter);
//                }
            }
        });
    }

    private void requestBluetoothConnection() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(Gait.this,Manifest.permission.BLUETOOTH_CONNECT)){
            new AlertDialog.Builder(Gait.this)
                    .setTitle("Permission needed")
                    .setMessage("Permission is needed to connect the device")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(Gait.this,new String[] {Manifest.permission.BLUETOOTH_CONNECT},BLUETOOTH_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create().show();
        }else{
            ActivityCompat.requestPermissions(Gait.this,new String[] {Manifest.permission.BLUETOOTH_CONNECT},BLUETOOTH_PERMISSION_CODE);
        }
    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == BLUETOOTH_PERMISSION_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(Gait.this, "Permission Granted!", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(Gait.this, "Permission! Denied", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
}


