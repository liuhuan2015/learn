package com.liuh.learn

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View


class SportView(context: Context, attrs: AttributeSet?) :
    View(context, attrs) {
    private val RADIUS = 300f
    private val bounds = Rect()


    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 200f
        textAlign = Paint.Align.CENTER
        isFakeBoldText = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //绘制环
        paint.style = Paint.Style.STROKE
        paint.color = Color.parseColor("#90a4ae")
        paint.strokeWidth = 40f
        canvas.drawCircle(width / 2f, height / 2f, RADIUS, paint)

        //绘制进度条
        paint.color = Color.parseColor("#ff4081")
        paint.strokeCap = Paint.Cap.ROUND
        canvas.drawArc(
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            width / 2f + RADIUS,
            height / 2f + RADIUS,
            -90f, 225f,
            false, paint
        )

        paint.style = Paint.Style.FILL
        paint.getTextBounds("abab", 0, "abab".length, bounds)
        Log.i(
            "SportView", "onDraw, bounds.bottom: ${bounds.bottom}," +
                    " bounds.top: ${bounds.top}"
        )
        canvas.drawText(
            "abab", width / 2f,
            height / 2f - (bounds.bottom + bounds.top) / 2f, paint
        )
    }

}