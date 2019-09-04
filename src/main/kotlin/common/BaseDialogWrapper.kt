package common

import com.intellij.openapi.ui.DialogWrapper

abstract class BaseDialogWrapper constructor(canBeParent: Boolean = true): DialogWrapper(canBeParent) {

    protected fun showCancelButton(show: Boolean) {
        getButton(cancelAction)?.isVisible = show
    }

}