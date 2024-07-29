package com.prem.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.os.Handler
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private var isRunning = false
    private var startTime = 0L
    private var elapsedTime = 0L
    private lateinit var stopwatchHandler: Handler
    private lateinit var stopwatchRunnable: Runnable

    private var btnStartPause : Button? = null
    private var btnReset : Button? = null
    private var textStopwatch : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStartPause = findViewById(R.id.btnStartPause)
        btnReset = findViewById(R.id.btnReset)
        textStopwatch = findViewById(R.id.textStopwatch)

        stopwatchHandler = Handler()
        stopwatchRunnable = object : Runnable {
            override fun run() {
                if (isRunning) {
                    updateStopwatch()
                }
                stopwatchHandler.postDelayed(this, 10) // Update every 10 milliseconds
            }
        }
    }

    fun onStartPauseButtonClick(view: android.view.View) {
        if (isRunning) {
            // Pause the stopwatch
            isRunning = false
            btnStartPause?.text = "Start"
        } else {
            // Start the stopwatch or resume if already started
            isRunning = true
            btnStartPause?.text = "Pause"

            if (elapsedTime == 0L) {
                // The stopwatch is starting from 0
                startTime = System.currentTimeMillis()
            } else {
                // The stopwatch is resuming from a paused state
                startTime = System.currentTimeMillis() - elapsedTime
            }
            stopwatchHandler.post(stopwatchRunnable)
        }
    }

    fun onResetButtonClick(view: android.view.View) {
        // Reset the stopwatch
        isRunning = false
        elapsedTime = 0L
        btnStartPause?.text = "Start"
        textStopwatch?.text = "00:00:00.000"
    }

    private fun updateStopwatch() {
        // Calculate the elapsed time
        elapsedTime = System.currentTimeMillis() - startTime

        // Format the elapsed time into hours, minutes, seconds, and milliseconds
        val hours = elapsedTime / 3600000
        val minutes = (elapsedTime % 3600000) / 60000
        val seconds = (elapsedTime % 60000) / 1000
        val milliseconds = elapsedTime % 1000

        // Display the formatted time in the TextView
        textStopwatch?.text = String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds)
    }
}
