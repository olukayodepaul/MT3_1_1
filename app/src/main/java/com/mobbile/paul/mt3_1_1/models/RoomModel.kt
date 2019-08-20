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

