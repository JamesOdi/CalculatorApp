package james.learning.calculator

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import james.learning.calculator.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var view: ActivityMainBinding
    private var value = ""
    private var firstValue = ""
    private var secondValue = ""
    private var first = true
    private var connector = ""
    private var result: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityMainBinding.inflate(layoutInflater)
        setContentView(view.root)

        view.nineBtn.setOnClickListener(this)
        view.eightBtn.setOnClickListener(this)
        view.sevenBtn.setOnClickListener(this)
        view.sixBtn.setOnClickListener(this)
        view.fiveBtn.setOnClickListener(this)
        view.fourBtn.setOnClickListener(this)
        view.threeBtn.setOnClickListener(this)
        view.twoBtn.setOnClickListener(this)
        view.oneBtn.setOnClickListener(this)
        view.zeroBtn.setOnClickListener(this)
        view.multiplyBtn.setOnClickListener(this)
        view.divideBtn.setOnClickListener(this)
        view.addBtn.setOnClickListener(this)
        view.minusBtn.setOnClickListener(this)
        view.clearBtn.setOnClickListener(this)
        view.percentBtn.setOnClickListener(this)
        view.dotBtn.setOnClickListener(this)
    }

    override fun onClick(clickedView: View?) {
        when (clickedView) {
            view.dotBtn -> {
                addValue(".")
            }
            view.clearBtn -> {
                firstValue = ""
                secondValue = ""
                view.inputView.text = ""
                view.resultView.text = ""
                connector = ""
                first = true
                value = ""
            }
            view.nineBtn -> {
                addValue("9")
            }
            view.eightBtn -> {
                addValue("8")
            }
            view.sevenBtn -> {
                addValue("7")
            }
            view.sixBtn -> {
                addValue("6")
            }
            view.fiveBtn -> {
                addValue("5")
            }
            view.fourBtn -> {
                addValue("4")
            }
            view.threeBtn -> {
                addValue("3")
            }
            view.twoBtn -> {
                addValue("2")
            }
            view.oneBtn -> {
                addValue("1")
            }
            view.zeroBtn -> {
                addValue("0")
            }
            view.multiplyBtn -> {
                addValue("X")
            }
            view.divideBtn -> {
                addValue("/")
            }
            view.minusBtn -> {
                addValue("-")
            }
            view.addBtn -> {
                addValue("+")
            }
            view.percentBtn -> {
                addValue("%")
            }
        }
    }

    private fun addValue(value: String) {
        val textField = view.inputView.text.toString()
        if (((value == ".") or isConnector(value)) && textField.isEmpty()) {
            return
        }

        if (textField.contains("%") or textField.contains("X") or textField.contains("/") or textField.contains("-") or textField.contains("+")) {
            first = false
        }

        if (first) {
            if ((value == ".") && firstValue.contains("."))
                return
        } else {
            if ((value == ".") && (secondValue.contains(".") or (secondValue == "")))
                return
        }

        if (textField.contains("%") && (isConnector(value))) {
            this.value += "X$value"
        } else {
            if (this.value.isNotEmpty() && value != "."){
                val currentValue = this.value[this.value.lastIndex].toString().toIntOrNull()
                val inserted = value.toIntOrNull()
                if (currentValue == null && inserted == null) {
                    return
                }
            }
            this.value += value
        }

        view.inputView.text = this.value

        if (isConnector(value)){
            if (value == "%") {
                result /= 100
                view.resultView.text = result.toString()
                connector = "X"
                secondValue = ""
                firstValue = result.toString()
                this.value = firstValue
            } else {
                if (view.resultView.text.isNotEmpty()) {
                    view.inputView.text = result.toString()
                    firstValue = result.toString()
                    view.resultView.text = ""
                    this.value = (firstValue + value)
                    view.inputView.text = (firstValue + value)
                    secondValue = ""
                }
                connector = value
            }
        } else {
            if (value != "%") {
                if (first) {
                    firstValue += value
                    result = getValue(firstValue)
                } else {
                    secondValue += value
                    val firstNumber = getValue(firstValue)
                    val secondNumber = getValue(secondValue)

                    when (connector) {
                        "X" -> {
                            result = firstNumber * secondNumber
                        }
                        "/" -> {
                            result = firstNumber / secondNumber
                        }
                        "+" -> {
                            result = firstNumber + secondNumber
                        }
                        "-" -> {
                            result = firstNumber - secondNumber
                        }
                    }
                    view.resultView.text = result.toString()
                }
            }
        }
    }

    fun getValue(givenValue: String): Double {
        val nf = NumberFormat.getNumberInstance(Locale.getDefault())
        return nf.format(givenValue.toDouble()).toDouble()
    }

    private fun isConnector(value: String): Boolean {
        return (value == "X") or (value == "/") or (value == "-") or (value == "+") or (value == "%")
    }
}