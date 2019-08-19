package com.example.kotlin_project.providers

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mobbile.paul.mt3_1_1.models.Bank_n_CustomersRoom
import com.mobbile.paul.mt3_1_1.models.ModulesRoom
import com.mobbile.paul.mt3_1_1.models.ProductTypeRoom
import com.mobbile.paul.mt3_1_1.models.ProductsRoom


@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveEmployees(modules: List<ModulesRoom>,
                      customers: List<Bank_n_CustomersRoom>,
                      products : List<ProductsRoom>,
                      producttype: List<ProductTypeRoom>)

    @Query("SELECT * FROM ModulesRoom")
    fun fetchModules() : List<ModulesRoom>

    @Query("SELECT * FROM customers")
    fun fetchCustomers() : List<Bank_n_CustomersRoom>

    @Query("SELECT * FROM products where separator=:sep ")
    fun fetchBasket(sep: Int) : List<ProductsRoom>

}