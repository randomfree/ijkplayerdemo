package com.ly.demo2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;

import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        NiceVideoPlayer player = findViewById(R.id.textureView);
        player.setPlayerType(NiceVideoPlayer.TYPE_IJK);
        player.setUp("rtmp://mobliestream.c3tv.com:554/live/goodtv.sdp",null);

        TxVideoPlayerController controller = new TxVideoPlayerController(this);
        controller.setTitle("美超：美国队长vs钢铁侠");
        controller.setImage(R.mipmap.poster);

        player.setController(controller);

        NiceVideoPlayerManager.instance().setCurrentNiceVideoPlayer(player);
    }

    @Override
    protected void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        // 在全屏或者小窗口时按返回键要先退出全屏或小窗口，
        // 所以在Activity中onBackPress要交给NiceVideoPlayer先处理。
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }
}