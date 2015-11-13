package net.dreamtobe.appengine.devcaster;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity implements CameraPreview.OnFrameCaptureListener {
    private Camera mCamera;
    private CameraPreview mPreview;

    private static String TAG = "[DevCaster]MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mCamera = getCameraInstance();

        mPreview = new CameraPreview(this, mCamera);

        mPreview.setOnFrameCaptureListener(this);

        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
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

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    public static Camera getCameraInstance() {
        Camera c = null;

        try {
            c = Camera.open(); // attempt to get a Camera instnace
        }
        catch (Exception e) {
            // Camera is not available (in use or does not exist
        }

        Camera.Parameters param = c.getParameters();
        List<int[]> fpslist = param.getSupportedPreviewFpsRange();
        Log.d("camera", "size= " + fpslist.size());
        for (int[] rates : fpslist) {
            //Log.d(TAG, i + " fps= " + fpslist.get(i)[Camera.Parameters.PREVIEW_FPS_MIN_INDEX]);
            //Log.d(TAG, i + " fps= " + fpslist.get(i)[Camera.Parameters.PREVIEW_FPS_MAX_INDEX]);
            Log.d(TAG, String.format("fps = %d ~ %d" , rates[0], rates[1]));
        }

        param.setPreviewFpsRange(fpslist.get(0)[0] , fpslist.get(0)[1]);

        return c; // return null if camera is unavailable
    }

    /// Detecting camera hardware
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        }
        else {
            // no camera on this device
            return false;
        }
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void OnFrameCapture(byte[] buffer, long pts) {
        Camera.Parameters params = mCamera.getParameters();
        int w = params.getPreviewSize().width;
        int h = params.getPreviewSize().height;
        int format = params.getPreviewFormat();

        Log.d(TAG, String.format("time(%d) width(%d) , height(%d), format (%d)",
                pts, w, h, format));
    }
}
