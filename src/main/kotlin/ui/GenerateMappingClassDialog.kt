package ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import javax.swing.JComponent

class GenerateMappingClassDialog constructor(private val project: Project): DialogWrapper(true) {

    private val panel = GenerateMappingClassPanel()

    init {
        init()
    }

    override fun createCenterPanel(): JComponent? = panel

    override fun doOKAction() {
        super.doOKAction()
    }

}