package com.mobbile.paul.mt3_1_1.ui.sales.sales.depots


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobbile.paul.mt3_1_1.R
import com.mobbile.paul.mt3_1_1.models.ProductsRoom
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.depot_adapter.view.*
import java.math.RoundingMode
import java.text.DecimalFormat


class DepotAdapter(private var mItems: List<ProductsRoom>) :
    RecyclerView.Adapter<DepotAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context)
            .inflate(R.layout.depot_adapter, p0, false)
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
            val df = DecimalFormat("#.#")
            df.roundingMode = RoundingMode.FLOOR
            containerView.tv_sku_q.text = item.productname.toLowerCase().capitalize()
            containerView.order_tv_q.text = item.qty
            containerView.amt_tv_q.text = String.format("%,.1f",(df.format(item.totalqtysold).toDouble()))
            containerView.tv_aty_q.text = String.format("%,.1f",(item.qty.toDouble()-item.totalqtysold))
        }
    }
}

