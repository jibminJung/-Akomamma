package com.jimmy.dongdaedaek.presentation

interface BaseView<presenterT:BasePresenter> {
    val presenter:presenterT
}