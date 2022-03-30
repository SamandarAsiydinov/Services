package uz.context.services.services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.provider.Settings
import android.widget.Toast
import uz.context.services.util.toast

class StartedService : Service() {

    private var player: MediaPlayer? = null

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        toast("Started Service Created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI)

        player!!.isLooping = true
        player!!.start()
        toast("Service Started")
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        player!!.stop()
        toast("Started Service Stopped")
    }
}