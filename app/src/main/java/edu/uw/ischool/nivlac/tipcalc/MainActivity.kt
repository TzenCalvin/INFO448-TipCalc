package edu.uw.ischool.nivlac.tipcalc

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tipButton = findViewById<Button>(R.id.showTip)
        val tipAmount = findViewById<EditText>(R.id.editAmount)

        tipButton.isEnabled = false

        tipButton.setOnClickListener {
            val amount = tipAmount.getText().toString().drop(1).toDouble()
            val tip = "%.2f".format(amount * 0.15)

            Toast.makeText(this, "A 15% tip is going to be $$tip", Toast.LENGTH_LONG).show()
        }

        //This function makes it so that you can only input up to two decimal points in the EditText
        //Credit goes to Touhid on StackOverflow
        //https://stackoverflow.com/a/63097909
        fun String.removeAfter2Decimal(et: EditText) {
            return if (this.isNullOrEmpty() || this.isNullOrBlank() || this.lowercase() == "null") {
                //
            } else {
                if(this.contains(".")) {
                    var lastPartOfText = this.split(".")[this.split(".").size-1]

                    if (lastPartOfText.count() > 2) {
                        try {
                            lastPartOfText = this.substring(0, this.indexOf(".")+3)
                            et.setText(lastPartOfText)
                            et.setSelection(lastPartOfText.length)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    } else {

                    }
                } else {

                }
            }
        }

        tipAmount.addTextChangedListener(object: TextWatcher {
            override fun onTextChanged(s:CharSequence, start:Int, before:Int, count:Int) {
                tipButton.isEnabled = s.toString().trim{ it <= ' ' }.isNotEmpty()
                tipAmount.text.toString().removeAfter2Decimal(tipAmount)
            }
            override fun beforeTextChanged(s:CharSequence, start:Int, count:Int,
                                           after:Int) {
            }
            override fun afterTextChanged(s: Editable) {
                if (!s.startsWith("$")) {
                    tipAmount.setText("$" + s)
                    tipAmount.setSelection(tipAmount.text.length)
                }
            }
        })
    }
}