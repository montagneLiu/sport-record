package com.viewpractice.sportrecord

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    var recordView:RecordView?=null
    var connected=false
    var circleAnimator: ObjectAnimator?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recordView=findViewById(R.id.recordView)
        recordView?.translationY=-50F

    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun onClickConnect(view: View) {

        if(!recordView!!.connected) {
            circleAnimator?.pause()
            val animator: ObjectAnimator = ObjectAnimator.ofFloat(recordView!!, "translationY", 0F)
            animator.start()
            circleAnimator= ObjectAnimator.ofFloat(recordView, "angle", 0F, 360F)
            circleAnimator?.setDuration(3000L)
            circleAnimator?.setInterpolator(LinearInterpolator())
            circleAnimator?.start()
            recordView!!.connected=true
            //动画结束监听实现循环播放
            circleAnimator?.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {
                        circleAnimator?.start()
                }
                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })
        }
        else{
            var scaleAnimator:ObjectAnimator=ObjectAnimator.ofFloat(recordView, "scale", 1F, 1.2F, 1F)
            scaleAnimator.start()
            scaleAnimator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {
                    val animator: ObjectAnimator = ObjectAnimator.ofFloat(recordView!!, "translationY", -50F)
                    animator.start()
                }

                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })
            var circle1Animator: ObjectAnimator = ObjectAnimator.ofFloat(recordView, "angle1", recordView!!.angle-180F, 360F)
            circle1Animator.setDuration(3000L)
            circle1Animator.start()
            recordView!!.connected=false
        }
    }
}
