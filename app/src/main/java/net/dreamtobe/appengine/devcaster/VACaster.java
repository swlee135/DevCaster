package net.dreamtobe.appengine.devcaster;

/**
 * Video Upstreaming을 위한 Class
 * @author 이승원 <andrew.lee@dreamtobe.net>
 * @version 0.0.1
 * @since  2015-11-19
 */
public class VACaster {
    /// Preview를 위한 SurfaceView 객체
    private PreviewSurfaceView mSurfaceView;

    /**
     * VACaster 생성자
     * <p>
     * Video Upstreaming을 위한 VACaster 기본 생성자
     * <p>
     * @param surfaceView Preview를 랜더링할 SurfaceView로 PreviewSurfaceView객체를 사용
     */
    public VACaster(PreviewSurfaceView surfaceView) {
        mSurfaceView = surfaceView;
    }

    /**
     * Preview 영상 UpStreaming을 시작
     * <p>
     * 카메라 Preview 영상을 RTSP/RTP를 이용하여 지정된 서버에 Upstreaming 한다.
     * @param url VAAllocator를 통해 지정된 VAController IP, Port 정보 IP:Port 형태로 전달된다.
     * @param commands VA 명령 조합, 장소,얼굴,표정,객체 인식 명령 조합으로 구성
     * @param authToken
     * @param parameter
     * @return
     */
    public int startUpStreaming(String url, int commands, String authToken, String parameter) {
        return 0;
    }

    public int stopUpStreaming() {
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
