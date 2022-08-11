package com.exsample.btsreception.helper

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import com.exsample.btsreception.R

class QRCodeScannerView : View {
    private var mPaint: Paint? = null
    private var mStrokePaint: Paint? = null
    private val mPath: Path = Path()
    private val pTopLeft: Point = Point()
    private val pBotRight: Point = Point()

    constructor(context: Context?) : super(context) {
        initPaints()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initPaints()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context,
        attrs,
        defStyleAttr) {
        initPaints()
    }

    private fun initPaints() {
        mPaint = Paint()
        mPaint!!.setColor(Color.parseColor("#A6000000"))
        mStrokePaint = Paint()
        mStrokePaint!!.setColor(Color.YELLOW)
        mStrokePaint!!.setStrokeWidth(4F)
        mStrokePaint!!.setStyle(Paint.Style.STROKE)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        pTopLeft.x = getWidth() / 13 + 10
        pTopLeft.y = getHeight() / 4 + 20

        pBotRight.x = getWidth() - pTopLeft.x - 10
        pBotRight.y = getHeight() - pTopLeft.y - 20
        mPaint!!.setColor(Color.parseColor("#77000000"))

        //Top Rect
        mPaint?.let { canvas.drawRect(0F, 0F, getWidth().toFloat(), pTopLeft.y.toFloat(), it) }

        //Left Rect
        canvas.drawRect(0F, pTopLeft.y.toFloat(), pTopLeft.x.toFloat(),
            pBotRight.y.toFloat(), mPaint!!)

        //Right Rect
        canvas.drawRect(pBotRight.x.toFloat(), pTopLeft.y.toFloat(),
            getWidth().toFloat(), pBotRight.y.toFloat(), mPaint!!)

        //Bottom rect
        canvas.drawRect(0F, pBotRight.y.toFloat(), getWidth().toFloat(),
            getHeight().toFloat(), mPaint!!)

        //Draw Outer Line drawable
        val d: Drawable = getResources().getDrawable(R.drawable.scanner_outline, null)
        d.setBounds(pTopLeft.x, pTopLeft.y, pBotRight.x, pBotRight.y)

        Log.d("TAG", "onDraw: ${pTopLeft.x}")
        Log.d("TAG", "onDraw: ${pTopLeft.y}")

        Log.d("TAG", "onDraw: ${pBotRight.x}")
        Log.d("TAG", "onDraw: ${pBotRight.y}")
        Log.d("TAG", "onDraw: ${( pTopLeft.x/ Resources.getSystem().getDisplayMetrics().density)}")
        Log.d("TAG", "onDraw: ${( pTopLeft.y/ Resources.getSystem().getDisplayMetrics().density)}")
        d.draw(canvas)
    }
}