package net.dreamtobe.appengine.devcaster;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by swlee on 2015-11-13.
 */
public class PreviewSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    Camera      mCamera;

    public PreviewSurfaceView(Context context) {
        super(context);
        mCamera = null;
        getHolder().addCallback(this);
    }

    public Camera getCamera() {
        return mCamera;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
