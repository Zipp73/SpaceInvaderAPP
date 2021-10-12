package com.example.spaceinvader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Spinner
import android.widget.Toast

class SettingActivity : AppCompatActivity() {
    private lateinit var spinner : Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
    }
	
	fun onClick(v: View) {
        when(v.id){
            R.id.bt_SaveSettings -> toSaveSettings()
			R.id.bt_Back -> toReturn()
        }
    }
	
	private fun toSaveSettings() {
        Toast.makeText(this, "saving in progress...", Toast.LENGTH_SHORT).show()

        spinner = findViewById(R.id.spinner)
        when(spinner.selectedItem.toString()){
            "Pink" -> {
                setTheme(R.style.DarkPink)
                setContentView(R.layout.activity_setting)
            }
            "Blue" -> {
                setTheme(R.style.DarkBlue)
                setContentView(R.layout.activity_setting)
            }
            "Red" -> {
                setTheme(R.style.DarkRed)
                setContentView(R.layout.activity_setting)
            }
            "Green" -> {
                setTheme(R.style.DarkGreen)
                setContentView(R.layout.activity_setting)
            }
        }

        Toast.makeText(this, "save completed", Toast.LENGTH_SHORT).show()
    }
	
	private fun toReturn() {
        this.onBackPressed()
    }
}