package com.snyder.general

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gi_jump.setItemClickListener(object : GeneralItemView.ItemClickListener{
            override fun itemClick(v: View?) {
                Toast.makeText(this@MainActivity, "跳转详情页", Toast.LENGTH_LONG).show()
            }
        })
        gi_content.setItemClickListener(object : GeneralItemView.ItemClickListener{
            override fun itemClick(v: View?) {
                gi_content.text = "测试"
            }
        })
        gi_edit.setRightIconClickListener(object : GeneralItemView.RightIconClickListener{
            override fun onRightIconClick(v: View?) {
                Toast.makeText(this@MainActivity, "打开扫描", Toast.LENGTH_LONG).show()
            }
        })
    }
}