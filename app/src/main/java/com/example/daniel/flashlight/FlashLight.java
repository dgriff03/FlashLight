package com.example.daniel.flashlight;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.widget.Toast;
import android.content.Context;

public class FlashLight extends Activity implements View.OnClickListener{

    private Button flashBtn;
    private boolean light;
    private boolean flashlight;
    private Camera cam;
    private Context context;
    private Parameters camParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_light);
        flashlight = true;
        context = getApplicationContext();
        flashBtn = (Button) findViewById(R.id.lightBtn);
        flashBtn.setOnClickListener(this);
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH) ){
            flashBtn.setText("Must have LED");
            flashlight = false;
        }else {
            cam = Camera.open();
            camParams = cam.getParameters();
        }
        light = false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.flash_light, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void toast_string(CharSequence text){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


    @Override
    public void onClick(View view){
        if (flashlight) {
            if (!light) {
                toast_string("Turning On");
                Parameters p = cam.getParameters();
                camParams.setFlashMode(Parameters.FLASH_MODE_TORCH);
                cam.setParameters(camParams);
                cam.startPreview();
                flashBtn.setText(getString(R.string.lightOnText));
            } else {
                toast_string("Turning Off");
                camParams.setFlashMode(Parameters.FLASH_MODE_OFF);
                cam.setParameters(camParams);
                flashBtn.setText(getString(R.string.lightOffText));
            }
            light = !light;
        }
    }

}
