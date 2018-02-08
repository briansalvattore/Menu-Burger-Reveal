package com.horsesdeveloper.menucqv

import android.animation.Animator
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import com.balysv.materialmenu.MaterialMenuDrawable
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var materialMenu: MaterialMenuDrawable
    private var isBurger = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        materialMenu = MaterialMenuDrawable(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN)
        toolbar.navigationIcon = materialMenu
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setNavigationOnClickListener {
            if (isBurger) {
                isBurger = false
                materialMenu.animateIconState(MaterialMenuDrawable.IconState.X)
                open()
            }
            else {
                isBurger = true
                materialMenu.animateIconState(MaterialMenuDrawable.IconState.BURGER)
                close(400)
            }
        }

        header.post { close(0) }
        help.setOnClickListener {
            close(400)
            isBurger = true
            materialMenu.animateIconState(MaterialMenuDrawable.IconState.BURGER)
        }

    }

    private fun open() {

        val toolbarIcon = toolbar.getChildAt(1)

        val cx = (toolbarIcon.left + toolbarIcon.right) / 2
        val cy = 0

        val dx = Math.max(cx, header.width - cx)
        val dy = Math.max(cy, header.height - cy)
        val finalRadius = Math.hypot(dx.toDouble(), dy.toDouble()).toFloat()

        val animator = ViewAnimationUtils.createCircularReveal(header, cx, cy, 0f, finalRadius)
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                help.visibility = View.VISIBLE
            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationStart(p0: Animator?) {
                header.visibility = View.VISIBLE
            }
        })
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.duration = 400
        animator.start()
    }

    private fun close(duration: Long) {

        val toolbarIcon = toolbar.getChildAt(1)

        val cx = (toolbarIcon.left + toolbarIcon.right) / 2
        val cy = 0

        val dx = Math.max(cx, header.width - cx)
        val dy = Math.max(cy, header.height - cy)
        val finalRadius = Math.hypot(dx.toDouble(), dy.toDouble()).toFloat()

        val animator = ViewAnimationUtils.createCircularReveal(header, cx, cy, finalRadius, 0f)
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                header.visibility = View.GONE
            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationStart(p0: Animator?) {
                help.visibility = View.GONE
            }
        })
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.duration = duration
        animator.start()
    }
}
