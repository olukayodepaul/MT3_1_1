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
    var lng: String = "",
    @SerializedName("rostertime")
    @Expose
    var rostertime: String = "",
    @SerializedName("sequence_id")
    @Expose
    var sequence_id: String = "",
    @SerializedName("defaulttoken")
    @Expose
    var defaulttoken: String = ""


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
    var packprice: String = "",
    @SerializedName("totalqtysold")
    @Expose
    var totalqtysold: Double = 0.0,
    @SerializedName("totalamountsold")
    @Expose
    var totalamountsold: Double = 0.0,
    @SerializedName("totalcommission")
    @Expose
    var totalcommission: Double = 0.0,
    @SerializedName("balanceamount")
    @Expose
    var balanceamount: Double = 0.0
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

//convert api to room
fun SalesEntriesApi.toSalesEntriesEntity(): SalesEntriesRoom {
    return SalesEntriesRoom(
        id, product_id, soq, product_name, qty, price, seperator
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
        lng,
        rostertime,
        sequence_id,
        defaulttoken
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
        packprice,
        totalqtysold,
        totalamountsold,
        totalcommission,
        balanceamount
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
    @SerializedName("visitsequence")
    @Expose
    var visitsequence: String = "",
    @SerializedName("saleslist")
    @Expose
    var saleslist: List<SalesEntrieHolderApi>? = null
)

//convert room to api
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
        order_price,
        contorder,
        contprincing,
        continventory,
        mtcom,
        mtamt
    )
}

data class SumSales(
    var sorder: Double = 0.0,
    var spricing: Int = 0,
    var sinventory: Double = 0.0,
    var stotalsum: Double = 0.0
)

data class salesEntryResponses (
    var status: Int = 0,
    var msg: String = "",
    var curlat: Double = 0.0,
    var curlng : Double = 0.0
)



//this id the post receive from server
data class postRecieveFromServer (
    @SerializedName("status")
    @Expose
    var status: Int = 0,
    @SerializedName("msg")
    @Expose
    var msg: String = "",
    @SerializedName("outletname")
    @Expose
    var outletname: String = "",
    @SerializedName("urno")
    @Expose
    var urno: Int = 0,
    @SerializedName("times")
    @Expose
    var times: String = "",
    @SerializedName("outletstatus")
    @Expose
    var outletstatus: String = "",
    @SerializedName("updateproductlist")
    @Expose
    var updateproductlist: List<UpdateProdcts>
)

data class UpdateProdcts (
    @SerializedName("totalqtysold")
    @Expose
    var totalqtysold: Double = 0.0,
    @SerializedName("totalamountsold")
    @Expose
    var totalamountsold: Double = 0.0,
    @SerializedName("totalcommission")
    @Expose
    var totalcommission: Double = 0.0,
    @SerializedName("balanceamount")
    @Expose
    var balanceamount: Double = 0.0,
    @SerializedName("product_code")
    @Expose
    var product_code: String = ""
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
    var product_id: String = "",
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

data class SalesEntrieHolderApi(
    @SerializedName("id")
    @Expose
    var id: Int = 0,
    @SerializedName("product_id")
    @Expose
    var product_id: String = "",
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
    var continventory: String = "",
    @SerializedName("mtcom")
    @Expose
    var mtcom: Double = 0.0,
    @SerializedName("mtamt")
    @Expose
    var mtamt: Double = 0.0
)

//
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
        continventory,
        mtcom,
        mtamt
    )
}

data class repSalesHistoryApi (
    @SerializedName("urno")
    @Expose
    var urno: Int = 0,
    @SerializedName("outletname")
    @Expose
    var outletname: String = "",
    @SerializedName("times")
    @Expose
    var times: String = "",
    @SerializedName("outletstatus")
    @Expose
    var outletstatus: String = ""
)

fun repSalesHistoryApi.toCustomersEntity(): repSalesHistoryRoom {
    return repSalesHistoryRoom(
       urno, outletname, times, outletstatus
    )
}

data class OutletSalesHistory (
    @SerializedName("status")
    @Expose
    var status: Int = 0,
    @SerializedName("shisto")
    @Expose
    var shisto: List<OutletSalesHistoryDetails>
)

data class OutletSalesHistoryDetails (
    @SerializedName("product_name")
    @Expose
    var product_name: String = "",
    @SerializedName("pricing")
    @Expose
    var pricing: String = "",
    @SerializedName("inventory")
    @Expose
    var inventory: String = "",
    @SerializedName("orders")
    @Expose
    var orders: String = "",
    @SerializedName("amount")
    @Expose
    var amount: String = "",
    @SerializedName("com")
    @Expose
    var com: String = ""
)

data class totalSumProductEntry(
    var qty: Double = 0.0,
    var tqsols: Double = 0.0,
    var tasole: Double = 0.0,
    var tcco: Double = 0.0
)

data class salesCommisssion (

    @SerializedName("status")
    @Expose
    var status: Int = 0,
    @SerializedName("flueamount")
    @Expose
    var flueamount: String = "",
    @SerializedName("fluedate")
    @Expose
    var fluedate: String = "",
    @SerializedName("ovmamount")
    @Expose
    var ovmamount: String = "",
    @SerializedName("ovmdate")
    @Expose
    var ovmdate: String = "",
    @SerializedName("salescomamount")
    @Expose
    var salescomamount: String = "",
    @SerializedName("salescomdate")
    @Expose
    var salescomdate: String = "",
    @SerializedName("mtcomamount")
    @Expose
    var mtcomamount: String = "",
    @SerializedName("mtcomdate")
    @Expose
    var mtcomdate: String = ""

)






