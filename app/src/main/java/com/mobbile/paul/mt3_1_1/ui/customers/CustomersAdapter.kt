package com.mobbile.paul.mt3_1_1.ui.customers

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.mobbile.paul.mt3_1_1.R
import com.mobbile.paul.mt3_1_1.models.RepCustomers
import com.mobbile.paul.mt3_1_1.ui.customers.editcustomer.EditCustomerActivity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.customer_adapter.*
import kotlinx.android.synthetic.main.customer_adapter.view.*


class CustomersAdapter(private var mItems: List<RepCustomers>, private var context: Context, private var  vmodel: CustomerViewModel) : RecyclerView.Adapter<CustomersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context)
            .inflate(R.layout.customer_adapter, p0, false)
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
        private val TAG = "CustomersAdapter"
    }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        @SuppressLint("SetTextI18n")
        fun bind(item: RepCustomers) {

            val letter: String? = item.outletname.substring(0, 1)
            val generator = ColorGenerator.MATERIAL
            val drawable = TextDrawable.builder()
                .buildRound(letter, generator.getRandomColor())
            containerView.imageView.setImageDrawable(drawable)

            containerView.tv_name.text = item.outletname.toLowerCase().capitalize()
            containerView.tv_titles.text = "${item.urno}, ${item.customerno}"
            progressBar_2.visibility = View.GONE
            //tv_sequence.text = item.

            containerView.icons_images.setOnClickListener {
                val passer = RepCustomers(
                    item.auto, item.id, item.urno, item.customerno,
                    item.outletclassid, item.outletlanguageid, item.outlettypeid,
                    item.outletname, item.outletaddress, item.contactname,
                    item.contactphone, item.latitude, item.longitude, item.outlet_pic
                )
                var intent = Intent(context, EditCustomerActivity::class.java)
                intent.putExtra("extra_item", passer)
                context.startActivity(intent)
            }

            containerView.setOnClickListener {
                val passer = RepCustomers(
                    item.auto, item.id, item.urno, item.customerno,
                    item.outletclassid, item.outletlanguageid, item.outlettypeid,
                    item.outletname, item.outletaddress, item.contactname,
                    item.contactphone, item.latitude, item.longitude, item.outlet_pic
                )
                var intent = Intent(context, EditCustomerActivity::class.java)
                intent.putExtra("extra_item", passer)
                context.startActivity(intent)
            }

            containerView.ref_btn.setOnClickListener {
                vmodel.geos(item.urno)
                progressBar_2.visibility = View.VISIBLE
                ref_btn.visibility = View.GONE
            }
        }
    }
}

