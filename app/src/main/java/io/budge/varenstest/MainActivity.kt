package io.budge.varenstest

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
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
        val lineChart = binding.lineChart
        val data = getLineData()
        // add some transparency to the color with "& 0x90FFFFFF"
        setupLineChart(lineChart, data)

        setUpCandlestickChart(binding.candlestickChart, getCandlestickData())
    }

    private fun setUpCandlestickChart(chart: CandleStickChart, data: CandleData) {
        chart.description.isEnabled = false
        chart.setDrawGridBackground(false)
        chart.setTouchEnabled(true)

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false)

        // enable scaling and dragging
        chart.isDragEnabled = true
        chart.setScaleEnabled(true)
        chart.setPinchZoom(false)

        val xAxis = chart.xAxis
        xAxis.position = XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        val l = chart.legend
        l.isEnabled = false
        val leftAxis = chart.axisLeft
        leftAxis.isEnabled = false

        val rightAxis = chart.axisRight
        rightAxis.apply {
            setLabelCount(7, false)
            setDrawGridLines(true)
            setDrawAxisLine(false)
        }
        chart.setViewPortOffsets(100f, 100f, 100f, 100f)
        // animate calls invalidate()...
        chart.data = data
//        chart.invalidate()
    }

    private fun setupLineChart(chart: LineChart, data: LineData) {

        // no description text
        chart.description.isEnabled = false


        //
        // enable / disable grid background
        chart.setDrawGridBackground(false)

        // enable touch gestures
        chart.setTouchEnabled(true)

        // enable scaling and dragging
        chart.isDragEnabled = true
        chart.setScaleEnabled(true)

        chart.setPinchZoom(false)

        chart.data = data

        val l = chart.legend
        l.isEnabled = false
        val axisLeft = chart.axisLeft
        axisLeft.apply {
            isEnabled = false
            spaceTop = 40f
            spaceBottom = 40f
            setDrawAxisLine(false)
        }

        val axisRight = chart.axisRight
        axisRight.apply {
            isEnabled = true
            textSize = 10f
            setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
            textColor = parseColor("#0CB1A0")
            setCenterAxisLabels(true)
            axisMaximum = 1800f
            axisMinimum = 1300f
            setDrawAxisLine(false)
            setLabelCount(6, true)
            yOffset = -9f
            valueFormatter = object : IndexAxisValueFormatter() {
                override fun getFormattedValue(value: Float, axis: AxisBase): String {
                    return "$$value"
                }
            }
        }

        val xAxis = chart.xAxis
        xAxis.apply {
            isEnabled = true
            textSize = 10f
            textColor = Color.WHITE
            textColor = Color.rgb(255, 192, 56)
            setDrawGridLines(false)
            setCenterAxisLabels(true)
            position = XAxisPosition.BOTTOM
            valueFormatter = object : IndexAxisValueFormatter() {
                override fun getFormattedValue(value: Float, axis: AxisBase): String {
                    val s = when (value) {
                        in 0f..7f -> "Sun"

                        in 7.1f..14f -> "Mon"

                        in 14.1f..21f  -> "Tue"

                        in 21.1f..28f -> "Wed"

                        in 28.1f..35f -> "Thu"

                        in 35.1f..42f -> "Fri"

                        else -> "Sat"
                    }
                    return s
                }
            }

            setLabelCount(8, true)
        }

        chart.setViewPortOffsets(100f, 100f, 100f, 100f)
        // animate calls invalidate()...
        chart.animateX(2500)
    }


    private fun getLineData(): LineData {
        val values = ArrayList<Entry>()
        for (i in 0 until 56) {
            val value = nextInt(1300, 1800).toFloat()
            Log.d("getData", "getData:$i $value")
            values.add(Entry(i.toFloat(), value))
        }

        // create a dataset and give it a type
        val drawable = ContextCompat.getDrawable(this, R.drawable.gradient_drawable)

        val set1 = LineDataSet(values, "DataSet 1")
        set1.setDrawCircleHole(false)
        set1.setDrawCircles(false)
        set1.setDrawFilled(true)
        set1.lineWidth = 2f
        set1.setDrawValues(false)
        set1.fillDrawable = drawable
        set1.color = parseColor("#0CB1A0")
        // create a data object with the data sets
        return LineData(set1)
    }

    private fun getCandlestickData(): CandleData {
        val values = ArrayList<CandleEntry>()

        val progress = 40

        for (i in 0 until progress) {
            val multi: Float = (100 + 1).toFloat()
            val value = (Math.random() * 40).toFloat() + multi
            val high = (Math.random() * 9).toFloat() + 8f
            val low = (Math.random() * 9).toFloat() + 8f
            val open = (Math.random() * 6).toFloat() + 1f
            val close = (Math.random() * 6).toFloat() + 1f
            val even = i % 2 == 0
            values.add(
                CandleEntry(
                    i.toFloat(), value + high,
                    value - low,
                    if (even) value + open else value - open,
                    if (even) value - close else value + close,
                    ResourcesCompat.getDrawable(resources, R.drawable.ic_launcher_background, null)
//                    resources.getDrawable(R.drawable.star)
                )
            )
        }

        val set1 = CandleDataSet(values, "Data Set")

        set1.setDrawIcons(false)
        set1.axisDependency = AxisDependency.RIGHT
        //        set1.setColor(Color.rgb(80, 80, 80));
        set1.shadowColor = Color.DKGRAY
        set1.shadowWidth = 0.7f
        set1.decreasingColor = Color.RED
        set1.decreasingPaintStyle = Paint.Style.FILL
        set1.increasingColor = Color.rgb(122, 242, 84)
        set1.increasingPaintStyle = Paint.Style.STROKE
        set1.neutralColor = Color.BLUE
        //set1.setHighlightLineWidth(1f);


        // create a data object with the
        return CandleData(set1)
    }

    private fun parseColor(colorString: String) = Color.parseColor(colorString)

}