package settings

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import component.GenerateMappingClassComponent
import utils.StringsBundle
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

class GenerateMappingClassSettings constructor(private val project: Project): Configurable, DocumentListener {

    private val txtBaseClassPath = JTextField()

    private var modified = false

    override fun isModified(): Boolean = modified

    override fun getDisplayName(): String = StringsBundle.message("plugin.name")

    override fun apply() {
        val config = GenerateMappingClassComponent.getInstance(project)
        config.baseClassPath = txtBaseClassPath.text
    }

    override fun changedUpdate(e: DocumentEvent?) {
        modified = true
    }

    override fun insertUpdate(e: DocumentEvent?) {
        modified = true
    }

    override fun removeUpdate(e: DocumentEvent?) {
        modified = true
    }

    override fun createComponent(): JComponent? {
        val mainPanel = JPanel()
        mainPanel.setBounds(0, 0, 452, 254)
        mainPanel.layout = null

        val lblBaseClassPath = JLabel(StringsBundle.message("base.class.path"))
        lblBaseClassPath.setBounds(30, 25, 83, 16)
        mainPanel.add(lblBaseClassPath)

        txtBaseClassPath.setBounds(125, 20, 291, 26)
        txtBaseClassPath.columns = 10
        mainPanel.add(txtBaseClassPath)

        val config = GenerateMappingClassComponent.getInstance(project)
        txtBaseClassPath.text = config.baseClassPath
        txtBaseClassPath.document.addDocumentListener(this)

        return mainPanel
    }

}