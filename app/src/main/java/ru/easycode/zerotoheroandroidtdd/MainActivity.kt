package ru.easycode.zerotoheroandroidtdd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private lateinit var contentLayout: LinearLayout
    private lateinit var editText: TextInputEditText
    private lateinit var actionButton: Button

    private val contentList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        contentLayout = findViewById(R.id.contentLayout)
        editText = findViewById(R.id.inputEditText)
        actionButton = findViewById(R.id.actionButton)

        actionButton.setOnClickListener {
            val newText = TextView(this)
            newText.text = editText.text.toString()
            contentList.add(newText.text.toString())
            contentLayout.addView(newText)
            editText.text?.clear()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArray("contentList", contentList.toTypedArray())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val contentTextView = savedInstanceState.getStringArray("contentList")
        if (contentTextView != null) {
            contentTextView.forEach {
                val newText = TextView(this)
                newText.text = it.toString()
                contentLayout.addView(newText)
            }
        }
    }
}