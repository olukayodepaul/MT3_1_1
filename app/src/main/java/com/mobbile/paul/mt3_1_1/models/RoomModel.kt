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
    var rostertime: String = "",
    var sort: Int = 0,
    var outlet_waiver: String = ""
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
    var packprice: String = ""
)

@Entity
data class ProductTypeRoom (
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    var name: String = "",
    var sep: Int = 0
)

@Entity(tableName = "salesentries")
data class SalesEntriesRoom (
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    var product_id: Int = 0,
    var product_code: String = "",
    var product_name: String = "",
    var qty: String = "",
    var soq: String = "",
    var priceperroll: Double = 0.0,
    var priceperpack: Double = 0.0
)

@Entity(tableName = "salesentriesholders")
data class SalesEntrieHolderRoom(
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    var product_id: Int = 0,
    var product_code: String = "",
    var soq: String = "",
    var product_name: String = "",
    var qty: Double = 0.0,
    var qtyperroll: Int = 0,
    var qtyperpack: Int = 0,
    var price: Double = 0.0,
    var priceperroll: Int = 0,
    var priceperpack: Int = 0,
    var entrydate: String = ""
)

