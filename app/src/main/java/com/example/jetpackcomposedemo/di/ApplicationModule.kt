package com.example.jetpackcomposedemo.di

import android.app.Application
import android.content.Context
import com.example.jetpackcomposedemo.app.spend.data.repository.SpendCategoryRepositoryImpl
import com.example.jetpackcomposedemo.app.spend.data.source.local.CategoryLocalDataSource
import com.example.jetpackcomposedemo.app.spend.domain.repository.SpendCategoryRepository
import com.example.jetpackcomposedemo.app.spend.domain.usecases.GetCategoryUseCase
import com.example.jetpackcomposedemo.app.spend.presentation.newspend.NewSpendViewModelFactory
import com.example.jetpackcomposedemo.data.dao.SpendCategoryDAO
import com.example.jetpackcomposedemo.data.db.SpendDB
import com.example.jetpackcomposedemo.di.scopes.ApplicationContext
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

}

@Module
interface RepositoryModule {
    @Binds
    fun bindSpendCategoryRepository(
        impl: SpendCategoryRepositoryImpl
    ): SpendCategoryRepository
}

@Module
class LocalSourceModule {
    @Provides
    fun spendDB(context: Context): SpendDB {
        return SpendDB.getInstance(context = context)
    }

    @Provides
    fun spendCategoryDao(db: SpendDB): SpendCategoryDAO {
        return db.spendCategoryDao()
    }

    @Provides
    fun spendLocalDataSource(dao: SpendCategoryDAO): CategoryLocalDataSource {
        return CategoryLocalDataSource(dao)
    }
}

@Module
class UseCaseModule {
    @Provides
    fun getCategoryUseCase(repository: SpendCategoryRepository): GetCategoryUseCase {
        return GetCategoryUseCase(repository)
    }
}

@Component(
    dependencies = [],
    modules = [ApplicationModule::class, UseCaseModule::class, RepositoryModule::class, LocalSourceModule::class]
)
interface ApplicationComponent {
    fun inject(application: Application)

    @Component.Builder
    interface Builder {

        @BindsInstance
        @ApplicationContext
        fun applicationContext(context: Context): Builder

        fun build(): ApplicationComponent
    }

    fun newSpendViewModelFactory(): NewSpendViewModelFactory

}
