package com.dreamguard.sample;

import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Surface;

import com.dreamguard.gpuvideo.GPUVideoView;
import com.dreamguard.gpuvideo.IVideoSurface;
import com.dreamguard.gpuvideo.filter.test.GPUFilterTest;
import com.dreamguard.gpuvideo.filter.test.GPUFilterTest2;
import com.dreamguard.gpuvideo.filter.textureoes.GPUVideoPixelationFilter;


public class MainActivity extends AppCompatActivity {


    private MediaPlayer mediaPlayer;

    private GPUVideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView = (GPUVideoView)findViewById(R.id.videoView);

        initGPUVideoView();

    }

    public void initGPUVideoView()
    {
        videoView.init(new IVideoSurface() {
            @Override
            public void onCreated(SurfaceTexture surfaceTexture) {
                initPlayer(surfaceTexture);
            }
        });


//        videoView.setFilter(new GPUVideoGammaFilter());
        GPUVideoPixelationFilter filter = new GPUVideoPixelationFilter();
        filter.setPixel(20);
        videoView.setFilter(filter);
//        videoView.setFilter(new GPUFilterTest2());

    }
    public void initPlayer(SurfaceTexture surfaceTexture){

        mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(this, Uri.parse("android.resource://" + getPackageName() + "/" +R.raw.girl4));
//            mediaPlayer.setDataSource(Environment.getExternalStorageDirectory().getAbsolutePath()+"/s.mp4");
            Surface s = new Surface(surfaceTexture);
            mediaPlayer.setSurface(s);
            s.release();
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        stopPlayer();
    }

    public void stopPlayer(){
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
    }
}
