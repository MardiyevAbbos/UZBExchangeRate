package com.example.uzbexchangerate.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.uzbexchangerate.R
import com.example.uzbexchangerate.databinding.ItemCurrencyViewBinding
import com.example.uzbexchangerate.models.CurrencyAndState
import com.example.uzbexchangerate.models.ExchangeRate
import com.example.uzbexchangerate.utils.extensions.invisible
import com.example.uzbexchangerate.utils.extensions.visible
import java.lang.ref.WeakReference

class SearchAdapter : ListAdapter<CurrencyAndState, SearchAdapter.VH>(ITEM_DIFF), Filterable {

    private var items: List<CurrencyAndState> = ArrayList()
    private var itemClick: ((ExchangeRate) -> Unit)? = null
    fun setItemClickListener(f: (item: ExchangeRate) -> Unit) {
        itemClick = f
    }

    inner class VH(private val binding: ItemCurrencyViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val view = WeakReference(binding.root)
        @SuppressLint("SetTextI18n")
        fun bind(item: CurrencyAndState) {
            binding.tvCurrencyName.text = item.currency.CcyNm_EN
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

            binding.root.setOnClickListener { itemClick?.invoke(item.currency) }
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
        this.items = items
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
                        && oldItem.currency.CcyNm_EN == newItem.currency.CcyNm_EN
            }
        }
    }

    override fun getFilter(): Filter {
        return customFilter
    }

    private val customFilter = object : Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults {
            val filterableList = mutableListOf<CurrencyAndState>()
            if (p0 == null || p0.isEmpty()) {
                filterableList.addAll(items)
            } else {
                for (item in items) {
                    if (item.currency.CcyNm_EN.lowercase().contains(p0.toString().lowercase())) {
                        filterableList.add(item)
                    }
                }
            }

            val results = FilterResults()
            results.values = filterableList
            return results
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            submitList(p1?.values as MutableList<CurrencyAndState>)
        }
    }

}