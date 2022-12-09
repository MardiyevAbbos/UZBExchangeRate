package com.example.uzbexchangerate.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.uzbexchangerate.R
import com.example.uzbexchangerate.databinding.ItemCurrencyViewBinding
import com.example.uzbexchangerate.models.CurrencyAndState
import com.example.uzbexchangerate.models.ExchangeRate
import com.example.uzbexchangerate.utils.SharedPreferencesHelper
import com.example.uzbexchangerate.utils.extensions.invisible
import com.example.uzbexchangerate.utils.extensions.visible

class CurrenciesAdapter(val shared: SharedPreferencesHelper) : ListAdapter<CurrencyAndState, CurrenciesAdapter.VH>(ITEM_DIFF) {

    private var exchangeIconClick: ((ExchangeRate) -> Unit)? = null
    fun setExchangeIconClickListener(f: (item: ExchangeRate) -> Unit) {
        exchangeIconClick = f
    }

    private var starIconClick: ((ExchangeRate, Boolean) -> Unit)? = null
    fun setStarIconClickListener(f: (currency: ExchangeRate, saveState: Boolean) -> Unit) {
        starIconClick = f
    }

    inner class VH(private val binding: ItemCurrencyViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor", "SetTextI18n")
        fun bind(item: CurrencyAndState) {
            binding.tvCurrencyName.text = when(shared.getLanguage()){
                "uz" -> { item.currency.CcyNm_UZ }
                "en" -> { item.currency.CcyNm_EN }
                else -> { item.currency.CcyNm_RU }
            }
            binding.tvCurrencyCcy.text = " ${item.currency.Ccy} "
            binding.tvCurrencyAmount.text = " ${item.currency.Rate} "
            binding.tvCurrencyDate.text = " ${item.currency.Date}"
            if (item.state) {
                binding.ivStar.setImageResource(R.drawable.ic_full_star)
            } else {
                binding.ivStar.setImageResource(R.drawable.ic_empty_star)
            }
            if (item.currency.Diff.toDouble() > 0) {
                binding.ivDiffState.setImageResource(R.drawable.ic_increase)
                binding.tvDiffAmountGreen.text = item.currency.Diff
                binding.tvDiffAmountGreen.visible()
                binding.tvDiffAmountRed.invisible()
            } else {
                binding.ivDiffState.setImageResource(R.drawable.ic_decrease)
                binding.tvDiffAmountRed.text = item.currency.Diff
                binding.tvDiffAmountRed.visible()
                binding.tvDiffAmountGreen.invisible()
            }

            binding.ivExchange.setOnClickListener { exchangeIconClick?.invoke(item.currency) }
            binding.ivStar.setOnClickListener {
                if (item.state) {
                    binding.ivStar.setImageResource(R.drawable.ic_empty_star)
                    starIconClick?.invoke(item.currency, false)
                } else {
                    binding.ivStar.setImageResource(R.drawable.ic_full_star)
                    starIconClick?.invoke(item.currency, true)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            ItemCurrencyViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))

    fun setData(items: List<CurrencyAndState>) {
        submitList(items)
    }

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<CurrencyAndState>() {
            override fun areItemsTheSame(oldItem: CurrencyAndState, newItem: CurrencyAndState): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: CurrencyAndState, newItem: CurrencyAndState): Boolean {
                return oldItem.currency.Ccy == newItem.currency.Ccy
                        && oldItem.currency.id == newItem.currency.id
                        && oldItem.currency.Rate == newItem.currency.Rate
                        && oldItem.currency.Date == newItem.currency.Date
            }

        }
    }

}