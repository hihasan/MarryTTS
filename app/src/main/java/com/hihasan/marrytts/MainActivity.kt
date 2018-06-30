package com.hihasan.marrytts

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.marytts.android.link.MaryLink

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.LinearLayout
import android.view.Gravity
import android.R.attr.gravity
import android.app.ActionBar
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toolbar
import com.hihasan.marrytts.R.id.editTtsText




class MainActivity : AppCompatActivity()
{

    private val pd: TransparentProgressDialog? = null
    private val h: Handler? = null
    private val r: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        speak.setOnClickListener(){
            Toast.makeText(this,"This is Button Press",Toast.LENGTH_SHORT).show()
            val text = editTtsText.text.toString().trim()
            if (!text.isEmpty()){
                MaryLink.getInstance().startTTS(text)
            }
        }

        fab.setOnClickListener { view ->

        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        if (h != null) {
            h.removeCallbacks(r)
        }
        if (pd != null) {
            if (pd.isShowing()) {
                pd.dismiss()
            }
        }
        super.onDestroy()
    }


    private inner class TransparentProgressDialog
    (getActivity: MainActivity, resourceIdOfImage: Int) : Dialog(this, R.style.TransparentProgressDialog) {

        private val iv: ImageView

        init {
            val wlmp = window!!.attributes
            wlmp.gravity = Gravity.CENTER_HORIZONTAL
            window!!.attributes = wlmp
            setTitle(null)
            setCancelable(false)
            setOnCancelListener(null)
            val layout = LinearLayout(context)
            layout.orientation = LinearLayout.VERTICAL
            val params = LinearLayout.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
            iv = ImageView(context)
            iv.setImageResource(resourceIdOfImage)
            layout.addView(iv, params)
            addContentView(layout, params)
        }

        override fun show() {
            super.show()
            val anim = RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f)
            anim.interpolator = LinearInterpolator()
            anim.repeatCount = Animation.INFINITE
            anim.duration = 3000
            iv.setAnimation(anim)
            iv.startAnimation(anim)
        }
    }
}

