package com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_fragment

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.mobbile.paul.mt3_1_1.models.Bank_n_CustomersRoom
import com.mobbile.paul.mt3_1_1.R
import com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_attendant.SalesAttendant
import com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_map_manager.UsersMap
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.sales_adapter.view.*


class SalesAdapter(private var mItems: List<Bank_n_CustomersRoom>, private var contexts: Context) :
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

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(item: Bank_n_CustomersRoom) {

            var letter: String? = item.outletname.substring(0, 1)

            var generator = ColorGenerator.MATERIAL

            var drawable = TextDrawable.builder()
                .buildRound(letter, generator.getRandomColor())

            containerView.imageView.setImageDrawable(drawable)
            containerView.tv_name.text = item.outletname
            containerView.tv_titles.text = item.notice
            containerView.timesetups.text = item.rostertime

            containerView.setOnClickListener {
                var intent: Intent? = null
                when (item.sort) {
                    1 -> {
                        intent = Intent(contexts, SalesAttendant::class.java)
                        intent.putExtra("lat", item.lat)
                        intent.putExtra("lng", item.lng)
                    }
                    2 -> {
                        intent = Intent(contexts, UsersMap::class.java)
                        intent.putExtra("urno", item.urno)
                        intent.putExtra("token", item.token)
                        intent.putExtra("lat", item.lat)
                        intent.putExtra("lng", item.lng)
                        intent.putExtra("visit_sequence", item.sequence_id)
                        intent.putExtra("outletname", item.outletname)
                    }
                }
                contexts.startActivity(intent)
            }
        }
    }
}


