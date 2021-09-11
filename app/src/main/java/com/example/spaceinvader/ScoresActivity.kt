package com.example.spaceinvader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
/**/import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


//val database: FirebaseDatabase = FirebaseDatabase.getInstance()
//val mRootRef: DatabaseReference = database.getReference()//players


class ScoresActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scores)


    }

    override fun onClick(v: View){
        when(v.id){
            R.id.bt_return -> finish()
        }
    }

}
