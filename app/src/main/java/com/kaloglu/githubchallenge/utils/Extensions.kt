package com.kaloglu.githubchallenge.utils

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer

fun <T> MutableLiveData<T>.observe(lifeCycleOwner: LifecycleOwner, function: (T?) -> Unit) =
        observe(lifeCycleOwner, Observer<T>(function::invoke))
