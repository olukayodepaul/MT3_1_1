package com.mobbile.paul.mt3_1_1.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class EmployeesApi(
    @SerializedName("status")
    @Expose
    var status: Int = 0,
    @SerializedName("id")
    @Expose
    var id: Int = 0,
    @SerializedName("name")
    @Expose
    var name: String = "",
    @SerializedName("msg")
    @Expose
    var msg: String = "",
    @SerializedName("customer_code")
    @Expose
    var customer_code: String = "",
    @SerializedName("modules")
    @Expose
    var modules: List<ModulesApi>,
    @SerializedName("customers")
    @Expose
    var customers: List<Bank_n_CustomersApi>,
    @SerializedName("product")
    @Expose
    var product: List<ProductsApi>,
    @SerializedName("spinners")
    @Expose
    var spinners: List<ProductTypeApi>
)

data class ModulesApi(
    @SerializedName("id")
    @Expose
    var id: Int = 0,
    @SerializedName("nav")
    @Expose
    var nav: String = "",
    @SerializedName("name")
    @Expose
    var name: String = "",
    @SerializedName("imageurl")
    @Expose
    var imageurl: String = "",
    @SerializedName("lat")
    @Expose
    var lat: Double = 0.0,
    @SerializedName("lng")
    @Expose
    var lng: Double = 0.0,
    @SerializedName("sort")
    @Expose
    var sort: Int = 0,
    @SerializedName("outlet_waiver")
    @Expose
    var outlet_waiver: Int = 0
)

data class Bank_n_CustomersApi(
    @SerializedName("auto")
    @Expose
    var auto: Int = 0,
    @SerializedName("id")
    @Expose
    var id: Int = 0,
    @SerializedName("notice")
    @Expose
    var notice: String = "",
    @SerializedName("urno")
    @Expose
    var urno: String = "",
    @SerializedName("customerno")
    @Expose
    var customerno: String = "",
    @SerializedName("outletname")
    @Expose
    var outletname: String = "",
    @SerializedName("token")
    @Expose
    var token: String = "",
    @SerializedName("visit_sequence")
    @Expose
    var visit_sequence: String = "",
    @SerializedName("sort")
    @Expose
    var sort: Int = 0,
    @SerializedName("outlet_waiver")
    @Expose
    var outlet_waiver: String = "",
    @SerializedName("lat")
    @Expose
    var lat: String = "",
    @SerializedName("lng")
    @Expose
    var lng: String = ""
)

data class ProductsApi(
    @SerializedName("auto")
    @Expose
    var auto: Int = 0,
    @SerializedName("id")
    @Expose
    var id: Int = 0,
    @SerializedName("separator")
    @Expose
    var separator: String = "",
    @SerializedName("separatorname")
    @Expose
    var separatorname: String = "",
    @SerializedName("productcode")
    @Expose
    var productcode: String = "",
    @SerializedName("productname")
    @Expose
    var productname: String = "",
    @SerializedName("qty")
    @Expose
    var qty: String = "",
    @SerializedName("soq")
    @Expose
    var soq: String = "",
    @SerializedName("rollprice")
    @Expose
    var rollprice: String = "",
    @SerializedName("packprice")
    @Expose
    var packprice: String = ""
)

data class ProductTypeApi(
    @SerializedName("id")
    @Expose
    var id: Int = 0,
    @SerializedName("name")
    @Expose
    var name: String = "",
    @SerializedName("sep")
    @Expose
    var sep: Int = 0
)

data class SaveEntries(
    var name: String = "",
    var id: Int = 0,
    var dates: String = "",
    var customerno: String = ""
)

data class GenSales(
    @SerializedName("status")
    @Expose
    var status: Int = 0,
    @SerializedName("msg")
    @Expose
    var msg: String = "",
    @SerializedName("dates")
    @Expose
    var dates: String = "",
    @SerializedName("sentry")
    @Expose
    var sentry: List<SalesEntriesApi>,
    @SerializedName("sentryh")
    @Expose
    var sentryh: List<SalesEntrieHolderApi>
)

data class SalesEntriesApi(
    @SerializedName("id")
    @Expose
    var id: Int = 0,
    @SerializedName("product_id")
    @Expose
    var product_id: Int = 0,
    @SerializedName("soq")
    @Expose
    var soq: String = "",
    @SerializedName("product_name")
    @Expose
    var product_name: String = "",
    @SerializedName("qty")
    @Expose
    var qty: String = "",
    @SerializedName("price")
    @Expose
    var price: String = "",
    @SerializedName("seperator")
    @Expose
    var seperator: String = ""
)

fun SalesEntriesApi.toSalesEntriesEntity(): SalesEntriesRoom {
    return SalesEntriesRoom(
        id, product_id, soq, product_name, qty, price, seperator
    )
}

data class SalesEntrieHolderApi(
    @SerializedName("id")
    @Expose
    var id: Int = 0,
    @SerializedName("product_id")
    @Expose
    var product_id: Int = 0,
    @SerializedName("soq")
    @Expose
    var soq: String = "",
    @SerializedName("product_name")
    @Expose
    var product_name: String = "",
    @SerializedName("qty")
    @Expose
    var qty: String = "",
    @SerializedName("price")
    @Expose
    var price: String = "",
    @SerializedName("seperator")
    @Expose
    var seperator: String = "",
    @SerializedName("seperatorname")
    @Expose
    var seperatorname: String = "",
    @SerializedName("orders")
    @Expose
    var orders: Double = 0.0,
    @SerializedName("inventory")
    @Expose
    var inventory: Double = 0.0,
    @SerializedName("pricing")
    @Expose
    var pricing: Int = 0,
    @SerializedName("entry_time")
    @Expose
    var entry_time: String = "",
    @SerializedName("order_price")
    @Expose
    var order_price: Double = 0.0,
    @SerializedName("contorder")
    @Expose
    var contorder: String = "",
    @SerializedName("contprincing")
    @Expose
    var contprincing: String = "",
    @SerializedName("continventory")
    @Expose
    var continventory: String = ""
)

fun SalesEntrieHolderApi.toSalesEntrieHolderEntity(): SalesEntrieHolderRoom {
    return SalesEntrieHolderRoom(
        id,
        product_id,
        soq,
        product_name,
        qty,
        price,
        seperator,
        seperatorname,
        orders,
        inventory,
        pricing,
        entry_time,
        order_price,
        contorder,
        contprincing,
        continventory
    )
}

fun ModulesApi.toModulesEntity(): ModulesRoom {
    return ModulesRoom(
        id,
        nav,
        name,
        imageurl
    )
}

fun Bank_n_CustomersApi.toCustomersEntity(): Bank_n_CustomersRoom {
    return Bank_n_CustomersRoom(
        auto,
        id,
        notice,
        urno,
        customerno,
        outletname,
        token,
        visit_sequence,
        sort,
        outlet_waiver,
        lat,
        lng
    )
}

fun ProductsApi.toProductEntity(): ProductsRoom {
    return ProductsRoom(
        auto,
        id,
        separator,
        separatorname,
        productcode,
        productname,
        qty,
        soq,
        rollprice,
        packprice
    )
}

fun ProductTypeApi.toProductTypeRoomEntity(): ProductTypeRoom {
    return ProductTypeRoom(
        id,
        name,
        sep
    )
}

data class postToServer(

    @SerializedName("employee_id")
    @Expose
    var employee_id: Int = 0,
    @SerializedName("urno")
    @Expose
    var urno: String = "",
    @SerializedName("token")
    @Expose
    var token: String = "",
    @SerializedName("distance")
    @Expose
    var distance: String = "",
    @SerializedName("arrivaltime")
    @Expose
    var arrivaltime: String = "",
    @SerializedName("departuretime")
    @Expose
    var departuretime: String = "",
    @SerializedName("dates")
    @Expose
    var dates: String = "",
    @SerializedName("arrivallat")
    @Expose
    var arrivallat: String = "",
    @SerializedName("arrivallng")
    @Expose
    var arrivallng: String = "",
    @SerializedName("departurelat")
    @Expose
    var departurelat: String = "",
    @SerializedName("departurelng")
    @Expose
    var departurelng: String = "",
    @SerializedName("saleslist")
    @Expose
    var saleslist: List<SalesEntrieHolderApi>? = null
)


fun SalesEntrieHolderRoom.toSalesHolderEntity(): SalesEntrieHolderApi {
    return SalesEntrieHolderApi(
        id,
        product_id,
        soq,
        product_name,
        qty,
        price,
        seperator,
        seperatorname,
        orders,
        inventory,
        pricing,
        entry_time,
        order_price
    )
}

data class postRecieveFromServer (
    @SerializedName("status")
    @Expose
    var employee_id: Int = 0,
    @SerializedName("saleshistory")
    @Expose
    var saleshistory: List<salesHistory>
)

data class salesHistory (
    @SerializedName("status")
    @Expose
    var employee_id: Int = 0
)

data class SumSales(
    var sorder: Double = 0.0,
    var spricing: Int = 0,
    var sinventory: Double = 0.0,
    var stotalsum: Double = 0.0
)


