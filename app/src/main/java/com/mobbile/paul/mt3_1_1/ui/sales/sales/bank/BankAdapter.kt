package com.mobbile.paul.mt3_1_1.ui.sales.sales.bank


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobbile.paul.mt3_1_1.R
import com.mobbile.paul.mt3_1_1.models.ProductsRoom
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.bank_adapter.view.*


class BankAdapter(private var mItems: List<ProductsRoom>) :
    RecyclerView.Adapter<BankAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context)
            .inflate(R.layout.bank_adapter, p0, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val item = mItems[p1]
        p0.bind(item)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    companion object {
        private val TAG = "BankAdapter"
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(item: ProductsRoom) {
            containerView.tv_sku_q.text = item.productname
            containerView.order_tv_q.text = item.totalqtysold.toString()
            containerView.amt_tv_q.text = (item.totalamountsold-item.totalcommission).toString()
            containerView.tv_aty_q.text = item.totalcommission.toString()
        }
    }
}


