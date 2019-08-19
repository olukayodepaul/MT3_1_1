package com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_fragment

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobbile.paul.mt3_1_1.models.Bank_n_CustomersRoom
import com.mobbile.paul.mt3_1_1.R
import com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_attendant.Sales_Attendant
import com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_map_manager.UsersMap
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.sales_adapter.view.*


class SalesAdapter(private var mItems: List<Bank_n_CustomersRoom>, private var contexts: Context):
    RecyclerView.Adapter<SalesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context)
            .inflate(R.layout.sales_adapter, p0, false)
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
        private val TAG = "ModulesActivity"
    }

    inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(item: Bank_n_CustomersRoom) {

            containerView.tv_name.text = item.outletname
            containerView.tv_titles.text = item.notice

            containerView.setOnClickListener {
                var intent: Intent? = null
                when(item.sort){
                    1->{
                       intent = Intent(contexts, Sales_Attendant::class.java)
                    }
                    2->{
                        intent = Intent(contexts,UsersMap::class.java)
                    }
                }
                contexts.startActivity(intent)
            }
        }
    }
}


