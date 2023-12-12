package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import org.mariuszgromada.math.mxparser.Expression

class MainActivity : AppCompatActivity() {
    private lateinit var inputTextView: TextView
    private lateinit var outputTextView: TextView
    private var currentExpression: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputTextView = findViewById(R.id.input)
        outputTextView = findViewById(R.id.output)

        // number buttons (0 - 9)
        val numberClickListener = View.OnClickListener {
            val clickedButton = it as Button
            currentExpression += clickedButton.text
            updateInputDisplay()
        }
        // setting up listener for each button
        for (i in 0..9) {
            val buttonId = resources.getIdentifier("btn$i", "id", packageName)
            findViewById<Button>(buttonId).setOnClickListener(numberClickListener)
        }

        // operator buttons (+, -, *, /)
        val operatorClickListener = View.OnClickListener {
            val clickedOperator = (it as Button).text
            currentExpression += " $clickedOperator "
            updateInputDisplay()
        }
        // setting up listener for each button
        val operatorButtonIds = arrayOf(R.id.add, R.id.subtract, R.id.multiply, R.id.divide)
        for (buttonId in operatorButtonIds) {
            findViewById<Button>(buttonId).setOnClickListener(operatorClickListener)
        }

        // equals button
        findViewById<Button>(R.id.equals).setOnClickListener {
            try {
                // attempt calculating expression
                val result = evaluateExpression()
                currentExpression = result.toString()
                updateOutputDisplay()
            }
            catch (e: Exception) {
                // handles invalid expressions
                currentExpression = "Error: Expression is invalid."
                updateOutputDisplay()
            }
        }

        // clear, dot, and bracket buttons
        findViewById<Button>(R.id.clear).setOnClickListener {
            currentExpression = ""
            updateInputDisplay()
            updateOutputDisplay()
        }
        findViewById<Button>(R.id.dot).setOnClickListener {
            currentExpression += "."
            updateInputDisplay()
        }
        findViewById<Button>(R.id.bracketLeft).setOnClickListener {
            currentExpression += "("
            updateInputDisplay()
        }
        findViewById<Button>(R.id.bracketRight).setOnClickListener {
            currentExpression += ")"
            updateInputDisplay()
        }

    }

    private fun updateInputDisplay() {
        inputTextView.text = currentExpression
    }

    private fun updateOutputDisplay() {
        outputTextView.text = currentExpression
    }

    private fun evaluateExpression(): Double {
        // Use a basic expression evaluator for simplicity
        val expression = Expression(currentExpression)
        return expression.calculate()
    }
}