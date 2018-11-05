package com.kaloglu.githubchallenge.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object KeyboardUtil {

    /**
     * Utility method for showing keyboard.
     *
     * @param view which has keyboard focus
     */
    fun showKeyboard(view: View) {
        getInputMethodManager(view.context)
                .showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    /**
     * Utility method for hiding keyboard.
     *
     * @param view which has keyboard focus
     */
    fun hideKeyboard(view: View) {
        getInputMethodManager(view.context)
                .hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * Utility method for check keyboard state.
     *
     * @param context which is current context
     */
    fun isKeyboardShown(context: Context): Boolean {
        return getInputMethodManager(context).isAcceptingText
    }

    /**
     * Return a system-level [InputMethodManager] service
     * for accessing input methods.
     *
     * @param context the context in which system services are obtained from.
     * @return An [InputMethodManager]
     */
    private fun getInputMethodManager(context: Context): InputMethodManager {
        return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }
}
