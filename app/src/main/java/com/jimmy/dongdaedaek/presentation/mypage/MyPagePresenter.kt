package com.jimmy.dongdaedaek.presentation.mypage

import android.app.PendingIntent.getActivity
import android.util.Log
import com.google.common.escape.Escaper
import com.jimmy.dongdaedaek.domain.usecase.GetCurrentUserEmail
import com.jimmy.dongdaedaek.domain.usecase.LogoutUseCase
import com.jimmy.dongdaedaek.domain.usecase.RequestLoginUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyPagePresenter(
    val view: MyPageContract.View,
    val requestLoginUseCase: RequestLoginUseCase,
    val getCurrentUserEmail: GetCurrentUserEmail,
    val logoutUseCase: LogoutUseCase
) : MyPageContract.Presenter {
    override val scope: CoroutineScope = MainScope()
    override var loggedIn: Boolean = false
    override fun onViewCreated() {

    }

    override fun onDestroyView() {
    }

    override fun onStart() {
        checkUserEmail()
    }

    fun checkUserEmail() {
        scope.launch {
            try {
                view.showProgressBar()
                Log.d("logging in...", "get current user email..")
                val email = getCurrentUserEmail()

                Log.d("logging in...", "it was $email")
                if (email != null) {
                    view.showUserInfo(email)
                    loggedIn = true
                }
            } catch (e: Exception) {
                view.showErrorToast()
            } finally {
                view.hideProgressBar()
            }

        }
    }

    override fun logout() {
        scope.launch {
            try {
                view.showProgressBar()
                logoutUseCase()
                loggedIn = false
                view.showLoginPage()
                view.logoutSuccessToast()
            } catch (e: Exception) {
                view.showErrorToast()
            } finally {
                view.hideProgressBar()
            }
        }
    }

    override fun requestLogin(email: String) {
        scope.launch {
            try {
                view.showProgressBar()
                requestLoginUseCase(email)
                view.emailSentToast()

            } catch (e: Exception) {
                view.showErrorToast()
            } finally {
                view.hideProgressBar()
            }

        }
    }

}