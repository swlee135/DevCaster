package net.dreamtobe.appengine.devcaster;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.util.Log;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;

/**
 * Created by swlee on 2015-10-29.
 */
public class AVCEncoder {
    private String TAG = "[DevCaster]AVCEncoder";
    private MediaCodec mMediaCodec;

    private byte[] mSPS;
    private byte[] mPPS;

    private ArrayDeque<MediaFrame> m_FrameQueue;

    public AVCEncoder(MediaCodec mMediaCodec, byte[] sps, byte[] pps, ArrayDeque<MediaFrame> m_FrameQueue) {
        this.mMediaCodec = mMediaCodec;
        this.mSPS = sps;
        this.mPPS = pps;
        this.m_FrameQueue = m_FrameQueue;
    }

    public MediaFormat getMediaFormat() {
        return mMediaCodec.getOutputFormat();
    }

    @Override
    public void close() throws IOException {
        mMediaCodec.stop();
        mMediaCodec.release();
    }

    @Override
    public void drainEncoder(byte[] input) {
        try {
            ByteBuffer[] inputBuffers = mMediaCodec.getInputBuffers();
            ByteBuffer[] outputBuffers = mMediaCodec.getOutputBuffers();
            int inputBufferIndex = mMediaCodec.dequeueInputBuffer(-1);
            if (inputBufferIndex >= 0) {
                ByteBuffer inputBuffer = inputBuffers[inputBufferIndex];
                inputBuffer.clear();
                inputBuffer.put(input);
                mMediaCodec.queueInputBuffer(inputBufferIndex, 0, input.length, 0, 0);
            }

            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();

            while (true) {
                int outputBufferIndex = mMediaCodec.dequeueOutputBuffer(bufferInfo, 0);
                if (outputBufferIndex == MediaCodec.INFO_TRY_AGAIN_LATER) { // no output available yet
                    if (input != null) { // if not end of stream
                        break;
                    }
                    else {
                        Log.d(TAG, "no output available, spinning to await EOS");
                    }
                }
                else if (outputBufferIndex == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED) { // not expected for an encoder
                    outputBuffers = mMediaCodec.getOutputBuffers();
                }
                else if (outputBufferIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) { // should happen before receiving buffers, and should only happen once
                    MediaFormat newFormat = mMediaCodec.getOutputFormat();
                    Log.d(TAG, "encoder output format changed: " + newFormat);
                }
                else if (outputBufferIndex < 0) {
                    Log.w(TAG, "unexpected result from encoder.dequeueOutputBuffer: " +
                            outputBufferIndex);
                }
                else {
                    ByteBuffer encodedData = outputBuffers[outputBufferIndex];

                    if (encodedData == null) {
                        throw new RuntimeException("encoderOutputBuffer " + outputBufferIndex +
                                " was null");
                    }

                    if ((bufferInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
                        // The codec config data was pulled out and fed to the muxer when we got
                        // the INFO_OUTPUT_FORMAT_CHANGED status.  Ignore it.
                        Log.d(TAG, "ignoring BUFFER_FLAG_CODEC_CONFIG");
                        bufferInfo.size = 0;
                    }

                    if (bufferInfo.size != 0) {
                        // adjust the ByteBuffer values to match BufferInfo (not needed?)
                        encodedData.position(bufferInfo.offset);
                        encodedData.limit(bufferInfo.offset + bufferInfo.size);

                    }

                    mMediaCodec.releaseOutputBuffer(outputBufferIndex, false);

                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }
}
