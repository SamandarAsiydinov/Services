package uz.context.services.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.SystemClock
import android.widget.Chronometer
import uz.context.services.util.toast

class BoundService : Service() {
    private val mBinder: IBinder = MyBinder()
    private var mChronometer: Chronometer? = null

    override fun onCreate() {
        super.onCreate()
        toast("Bound Service Created")
        mChronometer = Chronometer(this)
        mChronometer!!.base = SystemClock.elapsedRealtime()
        mChronometer!!.onChronometerTickListener =
            Chronometer.OnChronometerTickListener { chronometer ->
                toast(chronometer.base.toString())
            }
        mChronometer!!.start()
    }

    override fun onBind(p0: Intent?): IBinder? {
        toast("Bound Service onBind")
        return mBinder
    }

    override fun onRebind(intent: Intent?) {
        toast("Bound Service onBind")
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        toast("Bound Service onBind")
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        toast("Bound Service Stopped")
        mChronometer!!.stop()
    }

    val timestamp: String
        get() {
            val elapsedMillis = SystemClock.elapsedRealtime() - mChronometer!!.base
            val hours = (elapsedMillis / 3600000).toInt()
            val minutes = (elapsedMillis - hours * 3600000).toInt() / 60000
            val seconds = (elapsedMillis - hours * 3600000 - minutes * 60000).toInt() / 1000
            val millis =
                (elapsedMillis - hours * 3600000 - minutes * 60000 - seconds * 1000).toInt()
            return "$hours:$minutes:$seconds:$millis"
        }

    inner class MyBinder : Binder() {
        fun getService(): BoundService = this@BoundService
    }
}