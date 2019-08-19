package com.example.kotlin_project.providers

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mobbile.paul.mt3_1_1.models.Bank_n_CustomersRoom
import com.mobbile.paul.mt3_1_1.models.ModulesRoom
import com.mobbile.paul.mt3_1_1.models.ProductTypeRoom
import com.mobbile.paul.mt3_1_1.models.ProductsRoom


@Database(entities = [ModulesRoom::class, Bank_n_CustomersRoom::class, ProductsRoom::class, ProductTypeRoom::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val appdao: AppDao

    companion object {
        val DATABASE_NAME = "mobiletrader_application"
    }

}