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

        when(MainActivity.t){
            "Standard"  -> setTheme(R.style.Theme_SpaceInvader)
            "Pink"      -> setTheme(R.style.DarkPink)
            "Cyan"      -> setTheme(R.style.DarkCyan)
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
        spinner = findViewById(R.id.spinner)
        val c = spinner.selectedItem

        if(MainActivity.t != c.toString()){
            Toast.makeText(this, "saving in progress...", Toast.LENGTH_SHORT).show()

            when (c.toString()) {
                "Standard" -> setTheme(R.style.Theme_SpaceInvader)
                "Pink" -> setTheme(R.style.DarkPink)
                "Cyan" -> setTheme(R.style.DarkCyan)
                "Red" -> setTheme(R.style.DarkRed)
                "Green" -> setTheme(R.style.DarkGreen)
            }
            val i = spinner.selectedItemPosition
            MainActivity.t = spinner.selectedItem.toString()
            setContentView(R.layout.activity_setting)

            Toast.makeText(this, "save completed", Toast.LENGTH_SHORT).show()

            spinner.dispatchDisplayHint(i)
        }
        else{
            Toast.makeText(this, "Theme already selected", Toast.LENGTH_SHORT).show()
        }
        /*when(MainActivity.t){
            "Standard"  -> spinner.setSelection(i)
            "Pink"      -> setTheme(R.style.DarkPink)
            "Blue"      -> setTheme(R.style.DarkBlue)
            "Red"       -> setTheme(R.style.DarkRed)
            "Green"     -> setTheme(R.style.DarkGreen)
        }
        if(MainActivity.t == "Green") {
            Toast.makeText(this, i.toString(), Toast.LENGTH_SHORT).show()
            Toast.makeText(this, spinner.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show()
        }*/
    }
	
	private fun toReturn() {
        this.onBackPressed()
    }
}