package com.jimmy.dongdaedaek.presentation.mypage

import com.jimmy.dongdaedaek.presentation.BasePresenter
import com.jimmy.dongdaedaek.presentation.BaseView

interface MyPageContract {
    interface View:BaseView<Presenter> {
        fun showProgressBar()
        fun hideProgressBar()
        fun showErrorToast()
        fun showUserInfo(email:String)
        fun showLoginPage()
        fun emailSentToast()
        fun logoutSuccessToast()
    }
    interface Presenter:BasePresenter{
        var loggedIn:Boolean
        fun requestLogin(email:String)
        fun onStart()
        fun logout()
    }
}