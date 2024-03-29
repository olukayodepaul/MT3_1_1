package com.mobbile.paul.mt3_1_1.models


import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class ModulesRoom(
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    var nav: String = "",
    var name: String = "",
    var imageurl: String = ""
)

@Entity(tableName = "customers")
data class Bank_n_CustomersRoom(
    @PrimaryKey(autoGenerate = false)
    var auto: Int = 0,
    var id: Int = 0,
    var notice: String = "",
    var urno: String = "",
    var customerno: String = "",
    var outletname: String = "",
    var token: String = "",
    var visit_sequence: String = "",
    var sort: Int = 0,
    var outlet_waiver: String = "",
    var lat: String = "",
    var lng: String = "",
    var rostertime: String = "",
    var sequence_id: String = "",
    var defaulttoken: String = ""
)

@Entity(tableName = "products")
data class ProductsRoom(
    @PrimaryKey(autoGenerate = false)
    var auto: Int = 0,
    var id: Int = 0,
    var separator: String = "",
    var separatorname: String = "",
    var productcode: String = "",
    var productname: String = "",
    var qty: String = "",
    var soq: String = "",
    var rollprice: String = "",
    var packprice: String = "",
    var totalqtysold: Double = 0.0,
    var totalamountsold: Double = 0.0,
    var totalcommission: Double = 0.0,
    var balanceamount: Double = 0.0
)

@Entity
data class ProductTypeRoom (
    @PrimaryKey(autoGenerate = false)
    var auto : Int = 0 ,
    var id: Int = 0,
    var name: String = "",
    var sep: Int = 0
)

@Entity(tableName = "salesentries")
data class SalesEntriesRoom (
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    var product_id: String = "",
    var soq: String = "",
    var product_name: String = "",
    var qty: String = "",
    var price: String = "",
    var seperator: String = "",
    var blimit: Double = 0.0
)

@Entity(tableName = "salesentriesholders")
data class SalesEntrieHolderRoom(
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    var product_id: String = "",
    var soq: String = "",
    var product_name: String = "",
    var qty: String = "",
    var price: String = "",
    var seperator: String = "",
    var seperatorname: String = "",
    var orders: Double = 0.0,
    var inventory: Double = 0.0,
    var pricing: Int = 0,
    var entry_time: String = "",
    var order_price: Double = 0.0,
    var contorder: String = "",
    var contprincing: String = "",
    var continventory: String = "",
    var mtcom: Double = 0.0,
    var mtamt: Double = 0.0
)

@Entity(tableName = "saleshistory")
data class repSalesHistoryRoom(
    @PrimaryKey(autoGenerate = false)
    var urno: Int = 0,
    var outletname: String = "",
    var times: String = "",
    var outletstatus: String = ""
)






