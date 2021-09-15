package com.jimmy.dongdaedaek.di

import android.app.Activity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.jimmy.dongdaedaek.BuildConfig
import com.jimmy.dongdaedaek.data.api.*
import com.jimmy.dongdaedaek.data.api.Url.TMAP_API_URL
import com.jimmy.dongdaedaek.data.preference.PreferenceManager
import com.jimmy.dongdaedaek.data.preference.SharedPreferenceManager
import com.jimmy.dongdaedaek.data.repository.*
import com.jimmy.dongdaedaek.domain.model.Store
import com.jimmy.dongdaedaek.domain.usecase.*
import com.jimmy.dongdaedaek.presentation.addStore.AddStoreContract
import com.jimmy.dongdaedaek.presentation.addStore.AddStoreFragment
import com.jimmy.dongdaedaek.presentation.addStore.AddStorePresenter
import com.jimmy.dongdaedaek.presentation.explore.ExploreContract
import com.jimmy.dongdaedaek.presentation.explore.ExploreFragment
import com.jimmy.dongdaedaek.presentation.explore.ExplorePresenter
import com.jimmy.dongdaedaek.presentation.map.MapPageContract
import com.jimmy.dongdaedaek.presentation.map.MapPageFragment
import com.jimmy.dongdaedaek.presentation.map.MapPagePresenter
import com.jimmy.dongdaedaek.presentation.mypage.MyPageContract
import com.jimmy.dongdaedaek.presentation.mypage.MyPageFragment
import com.jimmy.dongdaedaek.presentation.mypage.MyPagePresenter
import com.jimmy.dongdaedaek.presentation.selectLocation.SelectLocationContract
import com.jimmy.dongdaedaek.presentation.selectLocation.SelectLocationFragment
import com.jimmy.dongdaedaek.presentation.selectLocation.SelectLocationPresenter
import com.jimmy.dongdaedaek.presentation.storeinformation.StoreInformationContract
import com.jimmy.dongdaedaek.presentation.storeinformation.StoreInformationFragment
import com.jimmy.dongdaedaek.presentation.storeinformation.StoreInformationPresenter
import com.naver.maps.map.NaverMapSdk
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


val appModule = module {
    single { Dispatchers.IO }
    single { Firebase.firestore }
    single { Firebase.storage }
    single { Firebase.auth }
    single { NaverMapSdk.getInstance(androidContext()) }

}
val dataModule = module {
    single<StoreApi> { StoreApiImpl(get()) }
    single<ReviewApi> { ReviewApiImpl(get()) }

    single { androidContext().getSharedPreferences("preference", Activity.MODE_PRIVATE) }
    single<PreferenceManager> { SharedPreferenceManager(get()) }
    single<UserRepository> { UserRepositoryImpl(get(), get()) }

    single<StoreRepository> { StoreRepositoryImpl(get(), get()) }
    single<ReviewRepository> { ReviewRepositoryImpl(get(), get()) }
    single { TmapRepository(get(), get()) }
    single { CategoryRepository(get(), get()) }

    // Api
    single {
        OkHttpClient()
            .newBuilder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                }
            )
            .build()
    }
    single<TmapApi> {
        Retrofit.Builder().baseUrl(Url.TMAP_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create()
    }

}

val domainModule = module {
    factory { GetStoresUseCase(get()) }
    factory { GetReviewUseCase(get()) }
    factory { UploadReviewUseCase(get()) }
    factory { RequestLoginUseCase(get(), get(), get()) }
    factory { CheckLinkAndLoginUseCase(get(), get(), get()) }
    factory { GetCurrentUserEmail(get(), get()) }
    factory { LogoutUseCase(get(), get()) }
    factory { FindLocationUseCase(get()) }
    factory { RegisterStoreUseCase(get(),get()) }
}
val presenterModule = module {
    scope<ExploreFragment> {
        scoped<ExploreContract.Presenter> { ExplorePresenter(getSource(), get(), get()) }
    }
    scope<StoreInformationFragment> {
        scoped<StoreInformationContract.Presenter> { (store: Store) ->
            StoreInformationPresenter(store, getSource(), get(), get())
        }
    }
    scope<MyPageFragment> {
        scoped<MyPageContract.Presenter> { MyPagePresenter(getSource(), get(), get(), get()) }
    }
    scope<MapPageFragment> {
        scoped<MapPageContract.Presenter> { MapPagePresenter(getSource()) }
    }

    scope<AddStoreFragment> {
        scoped<AddStoreContract.Presenter> { AddStorePresenter(getSource(), get(),get()) }
    }

    scope<SelectLocationFragment> {
        scoped<SelectLocationContract.Presenter> { SelectLocationPresenter(getSource(), get()) }
    }

}