package com.example.wintercamp.ui.layout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RadialGradient
import android.graphics.Shader
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.view.View
import java.util.Collections


class CircleOnClick(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val mypaintList: MutableList<myPaint?> =
        Collections.synchronizedList(ArrayList())
    private var isStart: Boolean = false
    private val width = context?.resources?.displayMetrics?.widthPixels
    private val height = context?.resources?.displayMetrics?.heightPixels


    private inner class myPaint(
        val paint: Paint,
        var radius: Int
    )

    private fun initmyPaint(): myPaint {
        val paint = Paint()
        val mypaint = myPaint(paint, 1)
        paint.alpha = 255
        return mypaint
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in mypaintList.indices) {
            val mypaint = mypaintList[i]
            if (mypaint != null) {
                val colorList = intArrayOf(
                    Color.argb(102, 183, 225, 255),
                    Color.argb(140, 225, 242, 255),
                    Color.argb(180, 229, 244, 255)
                )
                val position = floatArrayOf(
                    0.7f, 0.9181f, 1f
                )
                val radialGradient = RadialGradient(
                    width!!.toFloat()/2, height!!.toFloat()/2, mypaint.radius.toFloat(),
                    colorList, position, Shader.TileMode.CLAMP
                )
                mypaint.paint.shader = radialGradient
                canvas.drawCircle(
                    width.toFloat()/2, height.toFloat()/2,
                    mypaint.radius.toFloat(), mypaint.paint
                )
            }
        }
    }

    fun show() {
        if (mypaintList.size == 0)
            isStart = true
        val mypaint = initmyPaint()
        mypaintList.add(mypaint)
        invalidate()
        if (isStart) {
            handler.sendEmptyMessage(0)
        }
    }

    private fun flush() {
        for (i in mypaintList.indices) {
            var mypaint = mypaintList[i]
            if (mypaint != null) {
                if (!isStart && mypaint.paint.alpha< 2) {
                    mypaint.paint.alpha = 0
                    mypaint = null
                    continue
                } else if (isStart) {
                    isStart = false
                }
                mypaint.radius += 10
                mypaint.paint.alpha -= 2
            }
        }
    }

    private val handler = object : Handler(Looper.myLooper()!!) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                0 -> {
                    flush()
                    invalidate()
                    if (mypaintList.size > 0) {
                        sendEmptyMessageDelayed(0, 20)
                    }
                }
            }
        }
    }
}