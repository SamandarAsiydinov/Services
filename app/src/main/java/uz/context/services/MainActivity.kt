package uz.context.services

import android.app.NotificationChannel
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import androidx.annotation.RequiresApi
import uz.context.services.databinding.ActivityMainBinding
import uz.context.services.services.BoundService
import uz.context.services.services.StartedService
import uz.context.services.util.toast

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isBound = false
    private var boundService: BoundService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

    }

    private fun initViews() {
        binding.btnStartBound.setOnClickListener {
            startBoundService()
        }
        binding.btnStopBound.setOnClickListener {
            stopBoundService()
        }
    }

    private fun startBoundService() {
        val intent = Intent(this, BoundService::class.java)
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE)
    }

    private fun stopBoundService() {
        if (isBound)
            unbindService(mServiceConnection)
        isBound = false
    }

    private val mServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val myBinder: BoundService.MyBinder = service as BoundService.MyBinder
            boundService = myBinder.getService()
            isBound = true
            toast(boundService!!.timestamp)
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isBound = false
        }
    }

    private fun notification() {

    }

    private fun startStartedService() {
        startService(Intent(this, StartedService::class.java))
    }

    private fun stopStartedService() {
        stopService(Intent(this, StartedService::class.java))
    }
}