package com.mobbile.paul.mt3_1_1.ui.sales.sales.salesentries


import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_project.providers.Repository
import com.mobbile.paul.mt3_1_1.R
import com.mobbile.paul.mt3_1_1.models.SalesEntriesRoom
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.sales_entry_adapter.view.*


class SalesEntriesAdapter(
    private var mItems: List<SalesEntriesRoom>,
    private var repository: Repository
) :
    RecyclerView.Adapter<SalesEntriesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context)
            .inflate(R.layout.sales_entry_adapter, p0, false)
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
        private val TAG = "SalesEntriesAdapter"
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(item: SalesEntriesRoom) {

            containerView.tv_skus.text = item.product_name
            containerView.tv_soq.text = item.soq
            if (item.soq.isEmpty()) {
                containerView.tv_soq.text = "0"
            }

            containerView.mt_pricing.addTextChangedListener(
                object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {}
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {

                    }
                })

            containerView.mt_inventory.addTextChangedListener(
                object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {}
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        Log.d(TAG, "JH HFVH")
                    }
                })

            containerView.mt_order.addTextChangedListener(
                object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {}
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {

                        if (containerView.mt_order.text.toString() == ".") {

                            containerView.mt_order.setText("")

                        } else {

                            var trasformOrder = 0.0
                            var trasformPricing = 0
                            var trasformInventory = 0.0
                            var controltrasformOrder = ""
                            var controltrasformPricing = ""
                            var controltrasformInventory = ""

                            if (containerView.mt_order.text.toString().isNotEmpty()) {
                                trasformOrder = containerView.mt_order.text.toString().toDouble()
                                controltrasformOrder = "0"
                            }

                            if (containerView.mt_pricing.text.toString().isNotEmpty()) {
                                trasformPricing = containerView.mt_pricing.text.toString().toInt()
                                controltrasformPricing = "0"
                            }

                            if (containerView.mt_inventory.text.toString().isNotEmpty()) {
                                trasformInventory = containerView.mt_inventory.text.toString().toDouble()
                                controltrasformInventory = "0"

                            }

                            if (item.qty.toDouble() < trasformOrder) {
                                containerView.mt_order.setText("")
                            } else {
                                enterDailySales(
                                    trasformOrder,
                                    trasformInventory,
                                    trasformPricing,
                                    "00:00:00",
                                    item.id,
                                    item.product_id,
                                    trasformOrder * item.price.toDouble(),
                                    controltrasformOrder,
                                    controltrasformPricing,
                                    controltrasformInventory
                                )
                            }
                        }
                    }
                })
        }
    }

    fun enterDailySales(
        orders: Double, inventory: Double, pricing: Int,
        entry_time: String, id: Int, product_id: Int,
        salesprice: Double, contOrder: String, contPrincing: String, contInventory: String
    ) {
        repository.updateDailySales(
            orders, inventory,
            pricing, entry_time, id, product_id, salesprice, contOrder, contPrincing, contInventory
        ).subscribe()
    }

}
