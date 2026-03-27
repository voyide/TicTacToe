package com.example.tictactoe

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var isXTurn = true
    private val board = Array(3) { arrayOfNulls<Button>(3) }
    private lateinit var statusText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setBackgroundColor(Color.parseColor("#121212"))
        }

        statusText = TextView(this).apply {
            text = "Player X's Turn"
            textSize = 24f
            setTextColor(Color.WHITE)
            setPadding(0, 0, 0, 50)
        }
        layout.addView(statusText)

        val grid = GridLayout(this).apply {
            rowCount = 3
            columnCount = 3
        }

        for (i in 0..2) {
            for (j in 0..2) {
                val btn = Button(this).apply {
                    textSize = 40f
                    setTextColor(Color.WHITE)
                    setBackgroundColor(Color.parseColor("#333333"))
                    layoutParams = GridLayout.LayoutParams().apply {
                        width = 250
                        height = 250
                        setMargins(10, 10, 10, 10)
                    }
                    setOnClickListener { 
                        if ((it as Button).text.isEmpty() && checkWinner() == null) {
                            it.text = if (isXTurn) "X" else "O"
                            it.setTextColor(if (isXTurn) Color.CYAN else Color.MAGENTA)
                            val winner = checkWinner()
                            if (winner != null) {
                                statusText.text = if (winner == "Draw") "Draw!" else "Player $winner Wins!"
                            } else {
                                isXTurn = !isXTurn
                                statusText.text = "Player ${if (isXTurn) "X" else "O"}'s Turn"
                            }
                        }
                    }
                }
                board[i][j] = btn
                grid.addView(btn)
            }
        }
        layout.addView(grid)

        val resetBtn = Button(this).apply {
            text = "Restart"
            setBackgroundColor(Color.parseColor("#555555"))
            setTextColor(Color.WHITE)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { setMargins(0, 50, 0, 0) }
            
            setOnClickListener {
                board.flatten().forEach { it?.text = "" }
                isXTurn = true
                statusText.text = "Player X's Turn"
            }
        }
        layout.addView(resetBtn)
        setContentView(layout)
    }

    private fun checkWinner(): String? {
        for (i in 0..2) {
            if (board[i][0]?.text == board[i][1]?.text && board[i][1]?.text == board[i][2]?.text && board[i][0]?.text?.isNotEmpty() == true) return board[i][0]?.text.toString()
            if (board[0][i]?.text == board[1][i]?.text && board[1][i]?.text == board[2][i]?.text && board[0][i]?.text?.isNotEmpty() == true) return board[0][i]?.text.toString()
        }
        if (board[0][0]?.text == board[1][1]?.text && board[1][1]?.text == board[2][2]?.text && board[0][0]?.text?.isNotEmpty() == true) return board[0][0]?.text.toString()
        if (board[0][2]?.text == board[1][1]?.text && board[1][1]?.text == board[2][0]?.text && board[0][2]?.text?.isNotEmpty() == true) return board[0][2]?.text.toString()
        
        if (board.flatten().all { it?.text?.isNotEmpty() == true }) return "Draw"
        return null
    }
}
