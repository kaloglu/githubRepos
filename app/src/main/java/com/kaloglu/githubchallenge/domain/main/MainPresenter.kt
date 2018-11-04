package com.kaloglu.githubchallenge.domain.main

import com.kaloglu.githubchallenge.domain.interfaces.main.MainContract
import com.kaloglu.githubchallenge.mobileui.base.mvp.BaseAbstractPresenter
import javax.inject.Inject

class MainPresenter @Inject constructor()
    : BaseAbstractPresenter<MainContract.View>(), MainContract.Presenter