package com.aditya.autoreply.ui

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.os.Handler

 import android.util.DisplayMetrics
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import com.aditya.autoreply.R
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {

    lateinit var springForce: SpringForce

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            //do stuff
            //Like your Background calls and all
            springForce = SpringForce(0f)
            relative_layout.pivotX = 0f
            relative_layout.pivotY = 0f
            val springAnim = SpringAnimation(relative_layout, DynamicAnimation.ROTATION).apply {
                springForce.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
                springForce.stiffness = SpringForce.STIFFNESS_VERY_LOW
            }
            springAnim.spring = springForce
            springAnim.setStartValue(80f)
            springAnim.addEndListener(object : DynamicAnimation.OnAnimationEndListener {
                override fun onAnimationEnd(animation: DynamicAnimation<out DynamicAnimation<*>>?, canceled: Boolean, value: Float, velocity: Float) {
                    val displayMetrics = DisplayMetrics()
                    windowManager.defaultDisplay.getMetrics(displayMetrics)
                    val height = displayMetrics.heightPixels.toFloat()
                    val width = displayMetrics.widthPixels
                    relative_layout.animate()
                            .setStartDelay(1)
                            .translationXBy(width.toFloat() / 2)
                            .translationYBy(height)
                            .setListener(object : Animator.AnimatorListener {
                                override fun onAnimationRepeat(p0: Animator?) {

                                }

                                override fun onAnimationEnd(p0: Animator?) {
                                    val intent = Intent(applicationContext, CustomizeMessageActivity::class.java)
                                    finish()
                                    startActivity(intent)
                                    overridePendingTransition(0, 0)
                                }

                                override fun onAnimationCancel(p0: Animator?) {

                                }

                                override fun onAnimationStart(p0: Animator?) {

                                }

                            })
                            .setInterpolator(DecelerateInterpolator(1f))
                            .start()
                }
            })
            springAnim.start()
        }, 1000)

    }
}