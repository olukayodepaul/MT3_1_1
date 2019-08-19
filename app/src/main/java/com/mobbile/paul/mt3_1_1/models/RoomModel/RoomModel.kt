package com.example.kotlin_project.Models.RoomModel


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


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
    //var serialno: Int = 0
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

