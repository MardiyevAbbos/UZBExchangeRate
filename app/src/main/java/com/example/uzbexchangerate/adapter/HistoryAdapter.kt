package com.example.uzbexchangerate.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.uzbexchangerate.R
import com.example.uzbexchangerate.databinding.ItemCurrencyViewBinding
import com.example.uzbexchangerate.models.ExchangeRate
import com.example.uzbexchangerate.utils.SharedPreferencesHelper
import com.example.uzbexchangerate.utils.extensions.invisible
import com.example.uzbexchangerate.utils.extensions.visible
import java.lang.ref.WeakReference

class HistoryAdapter(val shared: SharedPreferencesHelper) : ListAdapter<ExchangeRate, HistoryAdapter.VH>(ITEM_DIFF) {

    private var exchangeIconClick: ((ExchangeRate) -> Unit)? = null
    fun setExchangeIconClickListener(f: (item: ExchangeRate) -> Unit) {
        exchangeIconClick = f
    }

    private var deleteIconClick: ((String) -> Unit)? = null
    fun setDeleteIconClickListener(f: (ccy: String) -> Unit) {
        deleteIconClick = f
    }

    inner class VH(private val binding: ItemCurrencyViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val view = WeakReference(binding.root)
        @SuppressLint("ResourceAsColor", "SetTextI18n")
        fun bind(item: ExchangeRate) {
            binding.tvCurrencyName.text = when(shared.getLanguage()){
                "uz" -> { item.CcyNm_UZ }
                "en" -> { item.CcyNm_EN }
                else -> { item.CcyNm_RU }
            }
            binding.tvCurrencyCcy.text = " ${item.Ccy} "
            binding.tvCurrencyAmount.text = " ${item.Rate} "
            binding.tvCurrencyDate.text = " ${item.Date}"
            binding.ivStar.setImageResource(R.drawable.ic_full_star)

            if (item.Diff.toDouble() > 0) {
                binding.ivDiffState.setImageResource(R.drawable.ic_increase)
                binding.tvDiffAmountGreen.text = item.Diff
                binding.tvDiffAmountGreen.visible()
                binding.tvDiffAmountRed.invisible()
            } else {
                binding.ivDiffState.setImageResource(R.drawable.ic_decrease)
                binding.tvDiffAmountRed.text = item.Diff
                binding.tvDiffAmountRed.visible()
                binding.tvDiffAmountGreen.invisible()
            }

            binding.root.setOnClickListener { exchangeIconClick?.invoke(item) }

            view.get()?.let {
                it.setOnClickListener {
                    if (view.get()?.scrollX != 0){
                        view.get()?.scrollTo(0,0)
                    }
                }
            }
            binding.ivDelete.setOnClickListener {
                deleteIconClick?.invoke(item.Ccy)
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

    fun setData(items: List<ExchangeRate>) {
        submitList(items)
    }

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<ExchangeRate>() {
            override fun areItemsTheSame(oldItem: ExchangeRate, newItem: ExchangeRate): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: ExchangeRate, newItem: ExchangeRate): Boolean {
                return oldItem.Ccy == newItem.Ccy
                        && oldItem.id == newItem.id
                        && oldItem.Rate == newItem.Rate
                        && oldItem.Date == newItem.Date
            }

        }
    }

}