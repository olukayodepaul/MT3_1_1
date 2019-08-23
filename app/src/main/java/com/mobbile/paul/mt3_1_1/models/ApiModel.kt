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
    @SerializedName("rostertime")
    @Expose
    var rostertime: String = "",
    @SerializedName("sort")
    @Expose
    var sort: Int = 0,
    @SerializedName("outlet_waiver")
    @Expose
    var outlet_waiver: String = ""
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
    var dates :String = ""
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
    @SerializedName("product_code")
    @Expose
    var product_code: String = "",
    @SerializedName("product_name")
    @Expose
    var product_name: String = "",
    @SerializedName("qty")
    @Expose
    var qty: String = "",
    @SerializedName("soq")
    @Expose
    var soq: String = "",
    @SerializedName("priceperroll")
    @Expose
    var priceperroll: Double = 0.0,
    @SerializedName("priceperpack")
    @Expose
    var priceperpack: Double = 0.0
)

data class SalesEntrieHolderApi(
    @SerializedName("id")
    @Expose
    var id: Int = 0,
    @SerializedName("product_id")
    @Expose
    var product_id: Int = 0,
    @SerializedName("product_code")
    @Expose
    var product_code: String = "",
    @SerializedName("soq")
    @Expose
    var soq: String = "",
    @SerializedName("product_name")
    @Expose
    var product_name: String = "",
    @SerializedName("qty")
    @Expose
    var qty: Double = 0.0,
    @SerializedName("qtyperroll")
    @Expose
    var qtyperroll: Int = 0,
    @SerializedName("qtyperpack")
    @Expose
    var qtyperpack: Int = 0,
    @SerializedName("price")
    @Expose
    var price: Double = 0.0,
    @SerializedName("priceperroll")
    @Expose
    var priceperroll: Int = 0,
    @SerializedName("priceperpack")
    @Expose
    var priceperpack: Int = 0,
    @SerializedName("entrydate")
    @Expose
    var entrydate: String = ""
)

fun SalesEntriesApi.toSalesEntriesEntity(): SalesEntriesRoom {
    return SalesEntriesRoom(
        id, product_id, product_code, product_name, qty, soq, priceperroll, priceperpack
    )
}

fun SalesEntrieHolderApi.toSalesEntrieHolderEntity(): SalesEntrieHolderRoom {
    return SalesEntrieHolderRoom(
        id,
        product_id,
        product_code,
        soq,
        product_name,
        qty,
        qtyperroll,
        qtyperpack,
        price,
        priceperroll,
        priceperpack,
        entrydate
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
        rostertime,
        sort,
        outlet_waiver
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



