package ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import generator.KotlinMappingClassGenerator
import generator.MappingClassConfig
import javax.swing.JComponent

class GenerateMappingClassDialog constructor(private val absolutePath: String): DialogWrapper(true) {

    private val panel = GenerateMappingClassPanel()

    init {
        init()
    }

    override fun createCenterPanel(): JComponent? = panel

    override fun doOKAction() {
        super.doOKAction()

        val generator = KotlinMappingClassGenerator()
        val config = MappingClassConfig(
            hasBaseClass = true,
            className = panel.getClassName(),
            path = absolutePath
        )

        generator.execute(config)
    }

}