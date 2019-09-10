package com.mobbile.paul.mt3_1_1.ui.customers.editcustomer



import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.mobbile.paul.mt3_1_1.R
import com.mobbile.paul.mt3_1_1.models.ProductTypeRoom
import com.mobbile.paul.mt3_1_1.models.RepCustomers
import com.mobbile.paul.mt3_1_1.ui.customers.CustomerViewModel
import com.mobiletraderv.paul.daggertraining.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_customer.*
import java.util.*
import javax.inject.Inject



class EditCustomerActivity : BaseActivity() {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory

    lateinit var vmodel: CustomerViewModel

    private lateinit var customers: RepCustomers

    lateinit var lsAdapter: OutletTypeSpinnerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_customer)
        vmodel = ViewModelProviders.of(this, modelFactory)[CustomerViewModel::class.java]

        customers = intent.extras!!.getParcelable("extra_item")!!

        customer_name_edit.setText(customers.outletname)
        contact_name_edit.setText(customers.contactname)
        address_edit.setText(customers.outletaddress)
        phone_number_edit.setText(customers.contactphone)

        vmodel.fatchSpinners(3).observe(this, languageObserver)

        lsAdapter = OutletTypeSpinnerAdapter()

        registerBtn.setOnClickListener {
            val outletTypeId = lsAdapter.getValueId(outlettypeedit.selectedItem.toString())
            Log.d(TAG, outletTypeId.toString())
        }

    }

    val languageObserver = Observer<List<ProductTypeRoom>> {
        if(it!=null) {
            val outletClassList = ArrayList<String>()
            if (lsAdapter.size() > 0) { lsAdapter.clear()
            }
            for(i in it.indices) {
                outletClassList.add(it[i].name)
                lsAdapter.add(it[i].id, it[i].name)
            }
            val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, outletClassList)
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            outlettypeedit!!.adapter = aa
            outlettypeedit!!.setSelection(lsAdapter.getIndexById(customers.outlettypeid.toInt()))
        }
    }

    companion object{
        val TAG = "EditCustomerActivity"
    }
}
