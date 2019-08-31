package com.example.kotlin_project.providers

import androidx.room.*
import com.mobbile.paul.mt3_1_1.models.*
import io.reactivex.Single


@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveEmployees(
        modules: List<ModulesRoom>,
        customers: List<Bank_n_CustomersRoom>,
        products: List<ProductsRoom>,
        producttype: List<ProductTypeRoom>
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveSales(
        salesen: List<SalesEntriesRoom>,
        salesh: List<SalesEntrieHolderRoom>
    )

    @Query("DELETE FROM ModulesRoom")
    fun deleteModulesRoom()

    @Query("DELETE FROM customers")
    fun deleteBank_n_CustomersRoom()

    @Query("DELETE FROM products")
    fun deleteProductsRoom()

    @Query("DELETE FROM ProductTypeRoom")
    fun deleteProductTypeRoom()

    @Query("DELETE FROM salesentries")
    fun deleteSalesEntriesRoom()

    @Query("DELETE FROM salesentriesholders")
    fun deleteSalesEntrieHolderRoom()

    @Query("SELECT * FROM ModulesRoom")
    fun fetchModules(): List<ModulesRoom>

    @Query("SELECT * FROM customers")
    fun fetchCustomers(): List<Bank_n_CustomersRoom>

    @Query("SELECT * FROM products where separator=:sep ")
    fun fetchBasket(sep: Int): List<ProductsRoom>

    @Query("SELECT * FROM salesentries order by seperator ASC")
    fun fetchDailySales(): List<SalesEntriesRoom>

    @Query("UPDATE salesentriesholders SET orders=:orders, inventory=:inventory, pricing=:pricing, entry_time=:entry_time, order_price=:salesprice, contorder=:contOrder, contprincing=:contPrincing, continventory=:contInventory where id=:id and product_id=:product_id")
    fun updateDailySales(orders: Double, inventory: Double, pricing: Int, entry_time: String, id: Int, product_id: Int, salesprice: Double, contOrder: String, contPrincing: String, contInventory: String)

    @Query("SELECT * FROM salesentriesholders order by seperator asc")
    fun fetchAllEntryPerDay(): List<SalesEntrieHolderRoom>

    @Query("SELECT count(id) FROM salesentriesholders WHERE contOrder= '' OR contPrincing = '' OR contInventory = ''")
    fun validateSalesEntry() : Int

    @Query("SELECT SUM(orders) AS sorder, SUM(inventory) AS sinventory, SUM(pricing) AS spricing, SUM(order_price) AS stotalsum  FROM salesentriesholders")
    fun sumAllSalesEntry(): SumSales

    @Query("SELECT * FROM salesentriesholders")
    fun pullAllSalesEntry() : List<SalesEntrieHolderRoom>
}


