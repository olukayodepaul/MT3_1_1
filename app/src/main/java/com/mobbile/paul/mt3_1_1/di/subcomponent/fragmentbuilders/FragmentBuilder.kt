package com.mobbile.paul.mt3_1_1.di.subcomponent.fragmentbuilders


import com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_fragment.SalesFragment
import com.mobbile.paul.mt3_1_1.ui.sales.sales_history.SalesHistoryFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentBuilder {

    @ContributesAndroidInjector
    abstract fun  contributeSalesFragment(): SalesFragment

    @ContributesAndroidInjector
    abstract fun  contributeSalesHistoryFragment(): SalesHistoryFragment

}
