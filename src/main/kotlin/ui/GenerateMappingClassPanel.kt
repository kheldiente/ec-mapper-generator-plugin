package ui

import utils.StringsBundle
import java.awt.Dimension
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField

class GenerateMappingClassPanel: JPanel() {

    companion object {
        private val TAG = GenerateMappingClassPanel::class.java.simpleName
    }

    private val txtMappingClassName = JTextField()

    init {
        setup()
    }

    private fun setup() {
        layout = null

        val lblMappingClassName = JLabel(StringsBundle.message("mapping.class.name"))
        lblMappingClassName.setBounds(25, 44, 77, 16)
        add(lblMappingClassName)

        txtMappingClassName.setBounds(114, 28, 183, 36)
        add(txtMappingClassName)

        println("Rendered $TAG")
    }

    override fun getPreferredSize(): Dimension = Dimension(300, 110)

}