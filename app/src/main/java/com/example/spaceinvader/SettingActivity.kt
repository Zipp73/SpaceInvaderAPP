package com.example.spaceinvader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Spinner

class SettingActivity : AppCompatActivity() {
    private lateinit var spinner : Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
    }
	
	fun onClick(v: View) {
        when(v.id){
            R.id.bt_Main -> toMain()
			R.id.bt_Back -> toReturn()
        }
    }
	
	private fun toMain() {
        this.finish()
    }
	
	private fun toReturn() {
        this.onBackPressed()
    }
}