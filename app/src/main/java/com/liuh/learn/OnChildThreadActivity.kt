package com.liuh.learn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import kotlin.concurrent.thread

class OnChildThreadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_child_thread)

        val tv = findViewById<TextView>(R.id.tv)

        Log.i("OnChildThreadActivity", "currentThread: " + Thread.currentThread().name)
        tv.setOnClickListener(View.OnClickListener {
            thread {
                Log.i("OnChildThreadActivity", "currentThread: " + Thread.currentThread().name)
                tv.text = "hahahhaha"
            }
        })

    }
}