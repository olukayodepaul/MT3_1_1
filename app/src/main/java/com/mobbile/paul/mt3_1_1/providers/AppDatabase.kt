package com.example.kotlin_project.providers

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kotlin_project.Models.RoomModel.Bank_n_CustomersRoom
import com.example.kotlin_project.Models.RoomModel.ModulesRoom
import com.example.kotlin_project.Models.RoomModel.ProductTypeRoom
import com.example.kotlin_project.Models.RoomModel.ProductsRoom


@Database(entities = [ModulesRoom::class, Bank_n_CustomersRoom::class, ProductsRoom::class, ProductTypeRoom::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val appdao: AppDao

    companion object {
        val DATABASE_NAME = "mobiletrader_application"
    }

}