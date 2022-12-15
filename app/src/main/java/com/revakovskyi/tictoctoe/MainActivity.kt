package com.revakovskyi.tictoctoe

import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.revakovskyi.tictoctoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var victoryChecker: VictoryChecker? = null

    private var firstTurn = Turn.CROSS
    private var currentTurn = Turn.CROSS

    private var scoreCrosses = 0
    private var scoreNoughts = 0

    private var crosses: String = ""
    private var noughts: String = ""
    private var draw: String = ""

    private lateinit var cellA1: Button
    private lateinit var cellA2: Button
    private lateinit var cellA3: Button

    private lateinit var cellB1: Button
    private lateinit var cellB2: Button
    private lateinit var cellB3: Button

    private lateinit var cellC1: Button
    private lateinit var cellC2: Button
    private lateinit var cellC3: Button

    private var cellsList = mutableListOf<Button>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNames()
        setTurnLabel()
        initCells()
        initCellsList()
        setCellsClickListener()
        initVictoryChecker()
        setButtonsAction()
    }

    private fun initNames() {
        crosses = getString(R.string.x)
        noughts = getString(R.string.o)
        draw = getString(R.string.draw)
    }

    private fun setTurnLabel() {
        val turnText = when (currentTurn) {
            Turn.NOUGHT -> getString(R.string.turn) + getString(R.string.o)
            Turn.CROSS -> getString(R.string.turn) + getString(R.string.x)
        }
        binding.turnPlayerText.text = turnText
    }

    private fun initCells() {
        binding.apply {
            cellA1 = a1
            cellA2 = a2
            cellA3 = a3

            cellB1 = b1
            cellB2 = b2
            cellB3 = b3

            cellC1 = c1
            cellC2 = c2
            cellC3 = c3
        }
    }

    private fun initCellsList() {
        cellsList.add(cellA1)
        cellsList.add(cellA2)
        cellsList.add(cellA3)

        cellsList.add(cellB1)
        cellsList.add(cellB2)
        cellsList.add(cellB3)

        cellsList.add(cellC1)
        cellsList.add(cellC2)
        cellsList.add(cellC3)
    }

    private fun setCellsClickListener() {
        cellA1.setOnClickListener { onCellClicked(cellA1) }
        cellA2.setOnClickListener { onCellClicked(cellA2) }
        cellA3.setOnClickListener { onCellClicked(cellA3) }

        cellB1.setOnClickListener { onCellClicked(cellB1) }
        cellB2.setOnClickListener { onCellClicked(cellB2) }
        cellB3.setOnClickListener { onCellClicked(cellB3) }

        cellC1.setOnClickListener { onCellClicked(cellC1) }
        cellC2.setOnClickListener { onCellClicked(cellC2) }
        cellC3.setOnClickListener { onCellClicked(cellC3) }
    }

    private fun initVictoryChecker() {
        victoryChecker = VictoryChecker(
            cellA1 = cellA1,
            cellA2 = cellA2,
            cellA3 = cellA3,

            cellB1 = cellB1,
            cellB2 = cellB2,
            cellB3 = cellB3,

            cellC1 = cellC1,
            cellC2 = cellC2,
            cellC3 = cellC3
        )
    }


    private fun onCellClicked(cell: Button) {
        if (cell.text.isNotBlank()) showToast()
        else if (currentTurn == Turn.NOUGHT) writeNought(cell)
        else if (currentTurn == Turn.CROSS) writeCross(cell)

        setTurnLabel()
        checkGameProgress()
    }

    private fun checkGameProgress() {
        if (checkVictory(crosses)) crossesWon()

        if (checkVictory(noughts)) noughtsWon()

        if (isBoardFull()) openResultScreen(draw)
    }

    private fun checkVictory(symbol: String): Boolean = victoryChecker?.isVictory(symbol) ?: false

    private fun noughtsWon() {
        scoreNoughts++
        openResultScreen(getString(R.string.nought_win))
    }

    private fun crossesWon() {
        scoreCrosses++
        openResultScreen(getString(R.string.crosses_win))
    }

    private fun isBoardFull(): Boolean {
        for (cell in cellsList) {
            if (cell.text.isBlank()) return false
        }
        return true
    }

    private fun writeCross(cell: Button) {
        currentTurn = Turn.NOUGHT
        cell.text = getString(R.string.x)
    }

    private fun writeNought(cell: Button) {
        currentTurn = Turn.CROSS
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cell.setTextColor(getColor(R.color.noughts))
        }
        cell.text = getString(R.string.o)
    }


    private fun openResultScreen(title: String) {
        val message =
            getString(R.string.crosses_score) + " $scoreCrosses" + getString(R.string.noughts_score) + " $scoreNoughts"

        showResultsDialog(
            title = title,
            message = message,
            positiveAction = { startNewGame() },
            negativeAction = {
                exitGame()
                startNewGame()
            }
        )
    }

    private fun exitGame() {
        showExitDialog()
    }

    private fun startNewGame() {
        for (cell in cellsList) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cell.setTextColor(getColor(R.color.button))
            }
            cell.text = ""
        }
        changeTurn()
    }

    private fun changeTurn() {
        firstTurn = when (firstTurn) {
            Turn.CROSS -> Turn.NOUGHT
            Turn.NOUGHT -> Turn.CROSS
        }
    }


    private fun setButtonsAction() {
        binding.apply {
            startNewGameButton.setOnClickListener { startNewGame() }
            exitButton.setOnClickListener { exitGame() }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        victoryChecker = null
    }

    enum class Turn {
        CROSS,
        NOUGHT
    }
}