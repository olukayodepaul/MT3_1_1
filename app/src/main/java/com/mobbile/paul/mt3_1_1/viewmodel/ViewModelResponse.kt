package com.mobbile.paul.mt3_1_1.viewmodel

data class ReloadCustomers(
    var status: Int = 0,
    var msg: String? = null
)

data class Attendant(
    var status: Int = 0,
    var msg: String? = null,
    var taskid : Int  = 0
)

data class EditCustomer(
    var status: Int = 0,
    var msg: String? = null
)

data class CloseOutlets(
    var status: Int = 0,
    var msg: String = ""
)