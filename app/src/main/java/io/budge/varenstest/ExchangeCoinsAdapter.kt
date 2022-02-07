package io.budge.varenstest

import android.util.Log
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import io.budge.varenstest.databinding.ExchangeCoinsItemBinding


class ExchangeCoinsAdapter :
    ListAdapter<ExchangeCoin, ExchangeCoinsAdapter.ExchangeCoinViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<ExchangeCoin>() {
        override fun areItemsTheSame(oldItem: ExchangeCoin, newItem: ExchangeCoin): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: ExchangeCoin, newItem: ExchangeCoin): Boolean =
            oldItem.id == newItem.id
    }

    inner class ExchangeCoinViewHolder(private val binding: ExchangeCoinsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ExchangeCoin) = binding.run {
            @DrawableRes val coinContainerBg =
                if (item.id % 2 == 0) R.drawable.coin_bg2 else R.drawable.coin_bg1
            val view = binding.root
            coinContainer.background = view.drawable(coinContainerBg)
            @ColorRes val containerBg =
                if (item.id % 2 == 0) R.color.container_bg else R.color.onPri
            exchangeCurrencyLayout.setBackgroundColor(view.color(containerBg))
            coinName.text = item.coinName.uppercase()
            @ColorRes val coinNameColor = when (item.coinName) {
                "btc" -> R.color.btc_yellow
                "xrp" -> R.color.xrp_blue
                else -> R.color.eth_grey
            }
            Log.d("bind", "bind: ${item.coinName}")
            coinName.setTextColor(view.color(coinNameColor))

            @DrawableRes val coinIcon = when (item.coinName) {
                "btc" -> R.drawable.ic_bitcoin
                "xrp" -> R.drawable.ic_ripple
                "ltc" -> R.drawable.ic_litecoin
                else -> R.drawable.ic_ethereum
            }
            coinImage.load(coinIcon)

            @DrawableRes val coinTrend = when (item.coinName) {
                "btc" -> R.drawable.ic_btc_chart
                "xrp" -> R.drawable.ic_xrp_trend
                "ltc" -> R.drawable.ic_ltc_trend
                else -> R.drawable.ic_eth_trend
            }

            trendChart.load(coinTrend)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeCoinViewHolder =
        ExchangeCoinViewHolder(ExchangeCoinsItemBinding.bind(parent.inflate(R.layout.exchange_coins_item)))

    override fun onBindViewHolder(holder: ExchangeCoinViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}


class ExchangeCoin(
    val id: Int,
    val coinName: String,
)