package uz.context.services.services

import android.app.Service
import android.content.Intent
import android.os.IBinder

class SampleService: Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}