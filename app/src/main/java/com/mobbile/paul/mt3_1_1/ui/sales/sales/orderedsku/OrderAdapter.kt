package com.mobbile.paul.mt3_1_1.ui.sales.sales.orderedsku


import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobbile.paul.mt3_1_1.R
import com.mobbile.paul.mt3_1_1.models.SalesEntrieHolderRoom
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.order_adapter_layout.view.*
import java.math.RoundingMode
import java.text.DecimalFormat

class OrderAdapter(private var mItems: List<SalesEntrieHolderRoom>):
    RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context)
            .inflate(R.layout.order_adapter_layout, p0, false)
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
        private val TAG = "OrderAdapter"
    }

    inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(item: SalesEntrieHolderRoom) {
            val df = DecimalFormat("#.#")


            df.roundingMode = RoundingMode.FLOOR
            containerView.mt_sku_id_tv.text = item.product_name.toLowerCase().capitalize()
            containerView.mt_inventory_id_tv.text = item.inventory.toString()
            containerView.mt_pricing_id_tv.text = item.pricing.toString()
            containerView.mt_order_id_tv.text = item.orders.toString()
            containerView.mt_amount_id_tv.text = String.format("%,.1f",(df.format(item.order_price).toDouble()))

            if(item.seperator.equals("3")){
                containerView.mt_sku_id_tv.setTextColor(Color.parseColor("#01579B"))
            }

        }
    }
}

