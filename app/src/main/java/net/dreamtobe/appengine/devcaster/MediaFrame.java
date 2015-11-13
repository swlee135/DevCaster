package net.dreamtobe.appengine.devcaster;

import android.media.MediaCodec;

import java.nio.ByteBuffer;

/**
 * Created by swlee on 2015-10-29.
 */
public class MediaFrame {
    ByteBuffer mBuffer;
    MediaCodec.BufferInfo mInfo;

    public MediaFrame(ByteBuffer buffer , MediaCodec.BufferInfo info) {
        this.mBuffer = buffer;
        this.mInfo = info;
    }
}
