package com.example.desafiobtg.usecases

import com.example.desafiobtg.di.qualifiers.utils.BasePresenter
import com.example.desafiobtg.di.qualifiers.utils.BaseView

interface MainContract {

    interface View: BaseView<Presenter>
    interface Presenter: BasePresenter<View>

}