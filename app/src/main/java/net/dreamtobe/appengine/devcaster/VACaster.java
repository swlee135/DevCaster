package net.dreamtobe.appengine.devcaster;

/**
 * Created by swlee on 2015-11-13.
 */
public class VACaster {
    /// Preview를 위한 SurfaceView 객체
    private PreviewSurfaceView mSurfaceView;

    public VACaster() {
    }

    public VACaster(PreviewSurfaceView surfaceView) {
        mSurfaceView = surfaceView;
    }

    public int StartPreview() {
        return 0;
    }

    public int StopPreview() {
        return 0;
    }

    public int StartUpStreaming() {
        return 0;
    }

    public int StopUpStreaming() {
        return 0;
    }

    public int refreshCamera() {
        return 0;
    }

    public int changePreviewSize(int previewSize) {
        return 0;
    }

    public int changePictureSize(int pictureSize) {
        return 0;
    }

    public byte[] takePicture() {
        return null;
    }
}
