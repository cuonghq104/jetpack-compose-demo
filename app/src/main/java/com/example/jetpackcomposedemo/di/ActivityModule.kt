package com.example.jetpackcomposedemo.di

import com.example.jetpackcomposedemo.BaseActivity
import com.example.jetpackcomposedemo.di.scopes.ActivityContext
import com.example.jetpackcomposedemo.di.scopes.ActivityScope
import dagger.BindsInstance
import dagger.Component
import dagger.Module

@Module
interface ActivityModule {

}

@ActivityScope
@Component(
    dependencies = [],
    modules = [ActivityModule::class]
)
interface ActivityComponent {
    fun inject(activity: BaseActivity)

    @Component.Builder
    interface Builder {

        @BindsInstance
        @ActivityContext
        fun activity(activity: BaseActivity): Builder

        fun build(): ActivityComponent
    }
}
