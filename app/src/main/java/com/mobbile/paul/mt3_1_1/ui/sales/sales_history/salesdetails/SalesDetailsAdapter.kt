package com.mobbile.paul.mt3_1_1.ui.sales.sales_history.salesdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobbile.paul.mt3_1_1.R
import com.mobbile.paul.mt3_1_1.models.OutletSalesHistoryDetails
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.sales_details_layout_adapter.view.*


class SalesDetailsAdapter(private var mItems: List<OutletSalesHistoryDetails>) :
    RecyclerView.Adapter<SalesDetailsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context)
            .inflate(R.layout.sales_details_layout_adapter, p0, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val item = mItems[p1]
        p0.bind(item)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    inner class ViewHolder(override val containerView: View):
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(item: OutletSalesHistoryDetails) {

            containerView.sku_tv.text = item.product_name.toLowerCase().capitalize()
            containerView.amt_tv_q.text = item.amount
            containerView.ord_tv.text = item.orders
            containerView.pri_tv.text = item.pricing
            containerView.invt_tv.text = item.inventory
            containerView.com_tv.text = item.com
        }
    }

}