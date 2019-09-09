package com.mobbile.paul.mt3_1_1.ui.sales.sales_history


import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.mobbile.paul.mt3_1_1.R
import com.mobbile.paul.mt3_1_1.models.repSalesHistoryRoom
import com.mobbile.paul.mt3_1_1.ui.sales.sales_history.salesdetails.SalesDetailsActivity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.sales_adapter.view.*


class SalesHistoryAdapter(private var mItems: List<repSalesHistoryRoom>, private var contexts: Context) :
    RecyclerView.Adapter<SalesHistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context)
            .inflate(R.layout.sales_history_adapter, p0, false)
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

    inner class ViewHolder(override val containerView: View):
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(item: repSalesHistoryRoom) {

            var letter: String? = item.outletname.substring(0, 1)
            var generator = ColorGenerator.MATERIAL
            var drawable = TextDrawable.builder()
                .buildRound(letter, generator.getRandomColor())

            containerView.imageView.setImageDrawable(drawable)
            containerView.tv_name.text = item.outletname.toLowerCase().capitalize()
            containerView.tv_titles.text = item.outletstatus
            containerView.timesetups.text = item.times

            containerView.setOnClickListener {

                when(item.outletstatus) {
                    "open"->{
                        var intent = Intent(contexts, SalesDetailsActivity::class.java)
                        intent.putExtra("token", item.urno)
                        contexts.startActivity(intent)
                    }
                    "close"->{
                        alert()
                    }
                }
            }
        }
    }

    fun alert(){
        val builder = AlertDialog.Builder(contexts, R.style.AlertDialogDanger)
        builder.setMessage("This customer did not purchase any product. Thanks!")
            .setTitle("Outlet Close No Purchase")
            .setIcon(R.drawable.icons8_error_90)
            .setCancelable(false)
            .setNegativeButton("OK") { _, _ ->
            }
        val dialog  = builder.create()
        dialog.show()
    }
}


