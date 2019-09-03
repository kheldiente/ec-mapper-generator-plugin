package ui

import com.intellij.ui.components.JBCheckBox
import extractor.data.ClassAttribute
import utils.StringsBundle
import java.awt.Dimension
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.ItemEvent
import java.awt.event.ItemListener
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField

class GenerateMappingClassPanel: JPanel(), ItemListener, ActionListener {

    companion object {
        private val TAG = GenerateMappingClassPanel::class.java.simpleName
    }

    interface EventListener {
        fun onSelectFirstClass()
    }

    private val txtBaseClassName = JTextField()
    private val txtClassName = JTextField()
    private val cbCreateBaseClass = JBCheckBox()
    private val lblFirstClass = JLabel()

    var firstClassAttributes = emptyList<ClassAttribute>()
    var secondClassAttributes = emptyList<ClassAttribute>()
    var listener: EventListener? = null
    var baseClassName: String = ""
        set(value) {
            field = value
            txtBaseClassName.text = value
        }
        get() {
            return txtBaseClassName.text
        }
    var className: String = ""
        set(value) {
            field = value
            txtClassName.text = className
        }
        get() {
            return txtClassName.text
        }
    var firstClass: String = ""
        set(value) {
            field = value
            lblFirstClass.text = value
        }
        get() {
            return lblFirstClass.text
        }

    init {
        setup()
    }

    private fun setup() {
        layout = null

        val lblBaseClassName = JLabel(StringsBundle.message("mapping.class.base.name"))
        lblBaseClassName.setBounds(25, 33, 77, 16)
        add(lblBaseClassName)

        txtBaseClassName.setBounds(114, 33, 183, 50)
        add(txtBaseClassName)

        cbCreateBaseClass.setBounds(325, 33, 50, 50)
        cbCreateBaseClass.addItemListener(this)
        cbCreateBaseClass.isSelected = true
        add(cbCreateBaseClass)

        val lblClassName = JLabel(StringsBundle.message("mapping.class.name"))
        lblClassName.setBounds(25, 77, 77, 16)
        add(lblClassName)

        txtClassName.setBounds(114, 77, 183, 50)
        add(txtClassName)

        val btnSelectFirstClass = JButton(StringsBundle.message("select.first.class"))
        btnSelectFirstClass.setBounds(25, 121, 77, 50)
        btnSelectFirstClass.addActionListener(this)
        add(btnSelectFirstClass)

        lblFirstClass.setBounds(110, 140, 150, 16)
        add(lblFirstClass)

        println("Rendered $TAG")
    }

    override fun getPreferredSize(): Dimension = Dimension(500, 500)

    override fun itemStateChanged(event: ItemEvent?) {
        if (event?.stateChange == ItemEvent.SELECTED) {
            txtBaseClassName.isEnabled = true
            txtBaseClassName.enableInputMethods(true)
        } else if (event?.stateChange == ItemEvent.DESELECTED) {
            txtBaseClassName.isEnabled = false
            txtBaseClassName.enableInputMethods(false)
        }
    }

    override fun actionPerformed(e: ActionEvent?) {
        listener?.onSelectFirstClass()
    }

}