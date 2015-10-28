package net.dreamtobe.appengine.devcaster;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by swlee on 2015-10-28.
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback, Camera.PreviewCallback {
    private Camera mCamera;
    private boolean mbCapture;
    private SurfaceHolder mHolder;

    private String TAG = "[FirstAndroidProject]CameraPreview";

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;
        mbCapture = false;

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "SurfaceCreated");

        try {
            mCamera.setPreviewDisplay(holder);
			/*
			Camera.Parameters params = mCamera.getParameters();
			PixelFormat lPixelFormat = new PixelFormat();
		    PixelFormat.getPixelFormatInfo(params.getPreviewFormat(), lPixelFormat);
		    int lSourceSize = params.getPreviewSize().width * params.getPreviewSize().height * lPixelFormat.bitsPerPixel / 8;
		    mCallbackBuffer = new byte[lSourceSize];
		    mCamera.addCallbackBuffer(mCallbackBuffer);

			mCamera.setPreviewCallbackWithBuffer(new PreviewCallback() {
				public void onPreviewFrame(byte[] _data, Camera _camera) {
					Camera.Parameters params = _camera.getParameters();
					int w = params.getPreviewSize().width;
					int h = params.getPreviewSize().height;
					int format = params.getPreviewFormat();

					Log.d(TAG, "onPreviewFrame call: " + String.format("w(%d), h(%d), format(%d)", w, h, format));
				}
			});
			*/
            mCamera.setPreviewCallback(this);
            mCamera.startPreview();
        }
        catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged");
        // Make sure to stop the preview before resizing or reformatting it.
        if (mHolder.getSurface() == null) {
            // preview surface does not exist
            Log.d(TAG, "preview surface does not exist");
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        }
        catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        try {
            mCamera.setPreviewDisplay(mHolder);
			/*
			Camera.Parameters params = mCamera.getParameters();
			PixelFormat lPixelFormat = new PixelFormat();
		    PixelFormat.getPixelFormatInfo(params.getPreviewFormat(), lPixelFormat);
		    int lSourceSize = params.getPreviewSize().width * params.getPreviewSize().height * lPixelFormat.bitsPerPixel / 8;
		    mCallbackBuffer = new byte[lSourceSize];
		    mCamera.addCallbackBuffer(mCallbackBuffer);

			mCamera.setPreviewCallbackWithBuffer(new PreviewCallback() {
				public void onPreviewFrame(byte[] _data, Camera _camera) {
					Camera.Parameters params = _camera.getParameters();
					int w = params.getPreviewSize().width;
					int h = params.getPreviewSize().height;
					int format = params.getPreviewFormat();

					Log.d(TAG, "onPreviewFrame call: " + String.format("w(%d), h(%d), format(%d)", w, h, format));
				}
			});
			*/
            mCamera.setPreviewCallback(this);
            mCamera.startPreview();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }

//		mCamera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mCamera != null)
            mCamera.release();
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        Camera.Parameters params = camera.getParameters();
        int w = params.getPreviewSize().width;
        int h = params.getPreviewSize().height;
        int format = params.getPreviewFormat();

        Log.d(TAG, String.format("time(%d) width(%d) , height(%d), format (%d)",
                System.currentTimeMillis(), w, h , format));
    }
}
