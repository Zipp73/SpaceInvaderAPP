package com.example.spaceinvader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Spinner
import android.widget.Toast

class SettingActivity : AppCompatActivity() {
    private lateinit var spinner : Spinner
    private var colorArray = R.array.color_array

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when(MainActivity.t){
            "Standard"  -> setTheme(R.style.Theme_SpaceInvader)
            "Pink"      -> setTheme(R.style.DarkPink)
            "Blue"      -> setTheme(R.style.DarkBlue)
            "Red"       -> setTheme(R.style.DarkRed)
            "Green"     -> setTheme(R.style.DarkGreen)
        }

        setContentView(R.layout.activity_setting)
    }
	
	fun onClick(v: View) {
        when(v.id){
            R.id.bt_SaveSettings    -> toSaveSettings()
			R.id.bt_Back            -> toReturn()
        }
    }
	
	private fun toSaveSettings() {
        Toast.makeText(this, "saving in progress...", Toast.LENGTH_SHORT).show()

        spinner = findViewById(R.id.spinner)
        val c = spinner.selectedItem
        when(c.toString()){
            "Standard"  -> setTheme(R.style.Theme_SpaceInvader)
            "Pink"      -> setTheme(R.style.DarkPink)
            "Blue"      -> setTheme(R.style.DarkBlue)
            "Red"       -> setTheme(R.style.DarkRed)
            "Green"     -> setTheme(R.style.DarkGreen)
        }
        MainActivity.t = spinner.selectedItem.toString()
        setContentView(R.layout.activity_setting)

        Toast.makeText(this, "save completed", Toast.LENGTH_SHORT).show()
    }
	
	private fun toReturn() {
        this.onBackPressed()
    }
}