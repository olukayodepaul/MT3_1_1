package com.example.kotlin_project.providers

import androidx.room.*
import com.mobbile.paul.mt3_1_1.models.*


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

    @Query("SELECT * FROM salesentries")
    fun fetchDailySales(): List<SalesEntriesRoom>


    @Query("SELECT * FROM salesentriesholders")
    fun fetchAllEntryPerDay(): List<SalesEntrieHolderRoom>

    @Query("UPDATE salesentriesholders SET  qtyperroll=:qtyperroll, qtyperpack=:qtyperpack, priceperroll=:priceperroll, priceperpack=:priceperpack where id=:id and product_code=:product_code")
    fun updateDailySales(
        id: Int,
        product_code: String,
        qtyperroll: Int,
        qtyperpack: Int,
        priceperroll: Double,
        priceperpack: Double
    )

}