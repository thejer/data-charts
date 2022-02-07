package io.budge.varenstest

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import io.budge.varenstest.databinding.ActivityMainBinding
import kotlin.random.Random.Default.nextInt


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpCandlestickChart(getCandlestickData())
        binding.change.setOnClickListener {
            val modalBottomSheet = ModalBottomSheet()
            modalBottomSheet.show(supportFragmentManager, ModalBottomSheet.TAG)
        }

        var isCandleStickChart = true
        binding.settings.setOnClickListener {
            isCandleStickChart = if (isCandleStickChart) {
                binding.candlestickChart.invalidate()
                binding.candlestickChart.hide()
                binding.lineChart.invalidate()
                binding.lineChart.show()
                setupLineChart(getLineData())
                false
            } else {
                binding.candlestickChart.invalidate()
                binding.candlestickChart.show()
                setUpCandlestickChart(getCandlestickData())
                binding.lineChart.invalidate()
                binding.lineChart.hide()
                true
            }
        }
    }

    private fun setupLineChart(lineData: LineData) = binding.lineChart.run {
        description.isEnabled = false
        setDrawGridBackground(false)
        setTouchEnabled(true)
        isDragEnabled = true
        setScaleEnabled(true)
        setPinchZoom(false)
        data = lineData
        legend.isEnabled = false
        val axisLeft = axisLeft
        axisLeft.apply {
            isEnabled = false
            spaceTop = 40f
            spaceBottom = 40f
            setDrawAxisLine(false)
        }

        axisRight.apply {
            isEnabled = true
            textSize = 12f
            xOffset = 15f
            setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
            textColor = parseColor("#666D8F")
            axisMaximum = 1800f
            axisMinimum = 1300f
            setDrawAxisLine(false)
            setLabelCount(6, true)
            valueFormatter = object : IndexAxisValueFormatter() {
                override fun getFormattedValue(value: Float, axis: AxisBase): String {
                    return "$${value.toInt()}"
                }
            }
        }

        xAxis.apply {
            isEnabled = true
            textSize = 10f
            textColor = parseColor("#666D8F")
            setDrawGridLines(false)
            yOffset = 15f
            position = XAxisPosition.BOTTOM
            valueFormatter = object : IndexAxisValueFormatter() {
                override fun getFormattedValue(value: Float, axis: AxisBase): String {
                    val s = when (value.toInt()) {
                        0 -> "Sun"

                        9 -> "Mon"

                        18  -> "Tue"

                        27 -> "Wed"

                        36 -> "Thu"

                        45 -> "Fri"

                        else -> "Sat"
                    }

                    return s.uppercase()
                }
            }

            setLabelCount(7, true)
        }

        setViewPortOffsets(100f, 100f, 200f, 100f)
        animateX(2500)
    }

    private fun getLineData(): LineData {
        val values = ArrayList<Entry>()
        for (i in 0 until 56) {
            val value = nextInt(1300, 1800).toFloat()
            values.add(Entry(i.toFloat(), value))
        }

        val drawable = ContextCompat.getDrawable(this, R.drawable.gradient_drawable)
        val dataSet = LineDataSet(values, "DataSet")
        dataSet.apply {
            setDrawCircleHole(false)
            setDrawCircles(false)
            setDrawFilled(true)
            lineWidth = 2f
            setDrawValues(false)
            fillDrawable = drawable
            color = parseColor("#0CB1A0")
        }
        return LineData(dataSet)
    }

    private fun setUpCandlestickChart(candleData: CandleData) = binding.candlestickChart.run {
        description.isEnabled = false
        setDrawGridBackground(false)
        setTouchEnabled(true)
        setPinchZoom(false)
        isDragEnabled = true
        setScaleEnabled(false)
        setPinchZoom(false)
        xAxis.apply {
            position = XAxisPosition.BOTTOM
            setDrawGridLines(false)
            setDrawAxisLine(false)
            setLabelCount(7, true)
            axisMinimum = 0f
            textSize = 10f
            textColor = parseColor("#666D8F")
            axisMaximum = 15f
            yOffset = 15f
            valueFormatter = object : IndexAxisValueFormatter() {
                override fun getFormattedValue(value: Float, axis: AxisBase): String {
                    val s = when (value) {
                        0.0f -> "Sun"

                        2.5f -> "Mon"

                        5.0f  -> "Tue"

                        7.5f -> "Wed"

                        10.0f -> "Thu"

                        12.5f -> "Fri"

                        else -> "Sat"
                    }
                    Log.d("getFormattedValue", " $value")
                    return s.uppercase()
                }
            }
        }


        legend.isEnabled = false
        val leftAxis = axisLeft
        leftAxis.isEnabled = false
        axisRight.apply {
            setDrawGridLines(true)
            setDrawAxisLine(false)
            axisMaximum = 1800f
            axisMinimum = 1300f
            textSize = 12f
            textColor = parseColor("#666D8F")
            setLabelCount(6, true)
            xOffset = 15f
            valueFormatter = object : IndexAxisValueFormatter() {
                override fun getFormattedValue(value: Float, axis: AxisBase): String {
                    return "$${value.toInt()}"
                }
            }
        }
        setViewPortOffsets(100f, 100f, 200f, 100f)
        data = candleData
        animateX(2500)
    }

    private fun getCandlestickData(): CandleData {
        val values = ArrayList<CandleEntry>()

        val progress = 15

        for (i in 1 until progress) {
            val value = nextInt(1300, 1800).toFloat()
            val diff = nextInt(100, 400).toFloat()
            val diff2 = nextInt(20, 50).toFloat()
            val open = if (value + diff > 1800) value else value + diff
            val close = if (value - diff < 1300) value else value - diff
            val even = nextInt() % 2 == 0

            val element = CandleEntry(
                i.toFloat(),
                open,
                close,
                if (even) open - diff2 else close + diff2,
                if (even) close+diff2  else open - diff2,
                null
            )
            Log.d("getCandlestickData", ": $element")
            values.add(
                element
            )
        }

        val set1 = CandleDataSet(values, "Data Set")

        set1.setDrawIcons(false)
        set1.axisDependency = AxisDependency.RIGHT
        set1.shadowColorSameAsCandle = true
        set1.shadowWidth = 2f
        set1.barSpace = 0.3f
        set1.decreasingColor = parseColor("#EB876B")
        set1.decreasingPaintStyle = Paint.Style.FILL
        set1.increasingColor = parseColor("#0CB1A0")
        set1.increasingPaintStyle = Paint.Style.FILL
        set1.neutralColor = Color.BLUE
        set1.setDrawValues(false)

        // create a data object with the
        return CandleData(set1)
    }

    private fun parseColor(colorString: String) = Color.parseColor(colorString)

}