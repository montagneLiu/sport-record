package com.viewpractice.sportrecord

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import java.util.*


class RecordView: View {
    var paint= Paint(Paint.ANTI_ALIAS_FLAG)
    var circlePaint= Paint(Paint.ANTI_ALIAS_FLAG)
    var arcPaint= Paint(Paint.ANTI_ALIAS_FLAG)
    var ovalPaint= Paint(Paint.ANTI_ALIAS_FLAG)
    var shiftArray: MutableList<Float> =ArrayList()
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    public var connected=false
    set(value){
        field=value
    }

    public var angle=0F
    set(value){
        field=value
        invalidate()
//        if(angle==6.28F){
//            angle=0F
//        }
    }

    public var angle1=0F
        set(value){
            field=value
            invalidate()

    }

    public var steps=2274
    public var count=0

    public var scale=1F
        set(value){
            field=value
            invalidate()
        }

    init{

        for(index in 0..40){
            shiftArray.add((Math.random() * index).toFloat())
        }
    }


    public var up=0F
        set(value){
            field=value
            invalidate()
        }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //放大
        canvas.scale(scale, scale, (width / 2).toFloat(), (height / 2).toFloat());

        //绘制文字
        paint.setColor(Color.WHITE)
        paint.textAlign=Paint.Align.CENTER
        paint.textSize=100F
        canvas.drawText(steps.toString(), width / 2.toFloat(), (height / 2).toFloat(), paint)
        paint.textSize=40F
        canvas.drawText("1.5公里" + "|" + "30千卡", width / 2.toFloat(), ((height / 2) + 60F).toFloat(), paint)


        if(!connected) {
            //绘制内环
            canvas.rotate(angle1, width / 2.toFloat(), (height / 2).toFloat())
            arcPaint.style=Paint.Style.STROKE
            arcPaint.color=Color.parseColor("#88FFFFFF")
            val pathEffect: PathEffect = DashPathEffect(floatArrayOf(10f, 5f), 10F)
            arcPaint.pathEffect = pathEffect
            canvas.drawArc(width / 2 - 200F, (height / 2) - 200F, width / 2 + 200F, (height / 2) + 200F, 360F * steps / 3000, 360F * (1 - steps / 3000), false, arcPaint)
            arcPaint.color=Color.parseColor("#FFFFFFFF")
            arcPaint.pathEffect=null
            canvas.drawArc(width / 2 - 200F, (height / 2) - 200F, width / 2 + 200F, (height / 2) + 200F, -90F, 360F * steps / 3000, false, arcPaint)
            //绘制圆形
            arcPaint.style=Paint.Style.FILL
            canvas.drawCircle(
                (width / 2 + 200F * Math.sin((Math.toRadians((360F * steps / 3000).toDouble())).toDouble())).toFloat(),
                (height / 2 - 200F * Math.cos((Math.toRadians((360F * steps / 3000).toDouble())).toDouble())).toFloat().toFloat(),
                5F,
                arcPaint
            )
            //绘制外环
            canvas.rotate(angle, width / 2.toFloat(), (height / 2).toFloat())
            val shader: Shader = LinearGradient(
                width / 2 - 100F,
                height / 2 - 100F,
                width / 2 + 100F,
                height / 2 + 100F,
                Color.parseColor("#FFFFFFFF"),
                Color.parseColor("#88FFFFFF"),
                Shader.TileMode.CLAMP
            );
            circlePaint.setShader(shader);
            circlePaint.strokeWidth = 20F
            circlePaint.style = Paint.Style.STROKE
            canvas.drawCircle(width / 2.toFloat(), (height / 2).toFloat(), 220F, circlePaint)
            circlePaint.strokeWidth = 10F
            //绘制光晕
            ovalPaint.setColor(Color.parseColor("#66FFFFFF"))
            ovalPaint.style=Paint.Style.STROKE
            ovalPaint.strokeWidth=10F
            canvas.drawArc(width / 2 - 225f, height / 2 - 225f, width / 2 + 200f, height / 2 + 200f, 150f, 150F, false, ovalPaint)
            ovalPaint.setColor(Color.parseColor("#44FFFFFF"))
            ovalPaint.style=Paint.Style.STROKE
            ovalPaint.strokeWidth=20F
            canvas.drawArc(width / 2 - 225f, height / 2 - 225f, width / 2 + 200f, height / 2 + 200f, 150f, 150F, false, ovalPaint)
            ovalPaint.setColor(Color.parseColor("#22FFFFFF"))
            ovalPaint.style=Paint.Style.STROKE
            ovalPaint.strokeWidth=30F
            canvas.drawArc(width / 2 - 225f, height / 2 - 225f, width / 2 + 200f, height / 2 + 200f, 150f, 150F, false, ovalPaint)
        }
        else{
            count++
            if (count==100){
                steps++
                count=0
            }
            //绘制尾迹1
            canvas.rotate(angle, width / 2.toFloat(), (height / 2).toFloat())
            var shader: Shader = SweepGradient(
                width / 2.toFloat(), (height / 2).toFloat(), Color.parseColor("#44FFFFFF"),
                Color.parseColor("#FFFFFFFF")
            )
            circlePaint.shader = shader
            canvas.drawCircle(width / 2.toFloat(), (height / 2).toFloat(), 200F, circlePaint)
            //绘制尾迹2
            circlePaint.strokeWidth=3F
             shader  = SweepGradient(
                 width / 2.toFloat(), (height / 2).toFloat(), Color.parseColor("#22FFFFFF"),
                 Color.parseColor("#FFFFFFFF")
             )
            circlePaint.shader = shader
            canvas.drawCircle(width / 2.toFloat(), (height / 2).toFloat(), 205F, circlePaint)
            //绘制尾迹3
            circlePaint.strokeWidth=3F
            shader  = SweepGradient(
                width / 2.toFloat(), (height / 2).toFloat(), Color.parseColor("#22FFFFFF"),
                Color.parseColor("#FFFFFFFF")
            )
            circlePaint.shader = shader
            canvas.drawCircle(width / 2.toFloat(), (height / 2).toFloat(), 195F, circlePaint)
            //绘制尾迹4
            circlePaint.strokeWidth=3F
            shader  = SweepGradient(
                width / 2.toFloat(), (height / 2).toFloat(), Color.parseColor("#00FFFFFF"),
                Color.parseColor("#FFFFFFFF")
            )
            circlePaint.shader = shader
            canvas.drawCircle(width / 2.toFloat(), (height / 2).toFloat(), 210F, circlePaint)
            //绘制尾迹5
            circlePaint.strokeWidth=3F
            shader  = SweepGradient(
                width / 2.toFloat(), (height / 2).toFloat(), Color.parseColor("#00FFFFFF"),
                Color.parseColor("#FFFFFFFF")
            )
            circlePaint.shader = shader
            canvas.drawCircle(width / 2.toFloat(), (height / 2).toFloat(), 190F, circlePaint)
            //绘制主圆形
            arcPaint.style=Paint.Style.FILL
            arcPaint.color=Color.parseColor("#FFFFFFFF")
            canvas.drawCircle(width / 2 + 200F, (height / 2).toFloat(), 10F, arcPaint)
            //绘制其他圆形

            for(index in 0..40) {
                var shake=Math.random()
                if(shiftArray[index]>1.5*index){
                    shiftArray[index]=0F
                }
                var alpha=(0xCC*(1-index/40)).shl(24)
                var transparentColor=alpha+0x00ffffff
                arcPaint.color=transparentColor
                shiftArray[index]+=shake.toFloat()
                canvas.drawCircle((width / 2 - 0.05 * index * index + 200F + shiftArray[index]).toFloat(), (height / 2).toFloat() - 0.1F * index * index, 5F, arcPaint)
            }
        }
    }
}