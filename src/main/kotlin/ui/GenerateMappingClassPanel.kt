package ui

import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.table.JBTable
import extractor.data.ClassAttribute
import extractor.data.ClassTree
import utils.StringsBundle
import ui.table.ClassTreeTableModel
import ui.combobox.ClassAttributeComboBox
import java.awt.Dimension
import java.awt.event.ItemEvent
import javax.swing.*

class GenerateMappingClassPanel: JPanel() {

    companion object {

        private val TAG = GenerateMappingClassPanel::class.java.simpleName

    }

    interface EventListener {
        fun onSelectFirstClass()
        fun onSelectSecondClass()
    }

    private val txtBaseClassName = JTextField()
    private val txtClassName = JTextField()
    private val cbCreateBaseClass = JBCheckBox()
    private val lblFirstClass = JLabel()
    private val lblSecondClass = JLabel()
    private val tblClassAttributes = JBTable()

    var firstClassTree = ClassTree()
        set (value) {
            field = value

            refreshTable()
            setupCellEditor()
        }
    var secondClassTree = ClassTree()
        set (value) {
            field = value

            refreshTable()
            setupCellEditor()
        }
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

    private var tblModelClass = ClassTreeTableModel(firstClassTree, secondClassTree)
    private val toggleCbBaseClass: (event: ItemEvent?) -> Unit = { event ->
        if (event?.stateChange == ItemEvent.SELECTED) {
            txtBaseClassName.isEnabled = true
            txtBaseClassName.enableInputMethods(true)
        } else if (event?.stateChange == ItemEvent.DESELECTED) {
            txtBaseClassName.isEnabled = false
            txtBaseClassName.enableInputMethods(false)
        }
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
        cbCreateBaseClass.addItemListener { event -> toggleCbBaseClass(event) }
        cbCreateBaseClass.isSelected = true
        add(cbCreateBaseClass)

        val lblClassName = JLabel(StringsBundle.message("mapping.class.name"))
        lblClassName.setBounds(25, 77, 77, 16)
        add(lblClassName)

        txtClassName.setBounds(114, 77, 183, 50)
        add(txtClassName)

        val btnSelectFirstClass = JButton(StringsBundle.message("select.first.class"))
        btnSelectFirstClass.setBounds(25, 130, 77, 50)
        btnSelectFirstClass.addActionListener { listener?.onSelectFirstClass() }
        add(btnSelectFirstClass)

        lblFirstClass.setBounds(110, 140, 150, 16)
        add(lblFirstClass)

        val btnSelectSecondClass = JButton(StringsBundle.message("select.second.class"))
        btnSelectSecondClass.setBounds(180, 130, 77, 50)
        btnSelectSecondClass.addActionListener { listener?.onSelectSecondClass() }
        add(btnSelectSecondClass)

        lblSecondClass.setBounds(280, 140, 150, 16)
        add(lblSecondClass)

        tblClassAttributes.setBounds(25, 210, 300, 500)
        tblClassAttributes.model = tblModelClass
        add(tblClassAttributes)
    }

    override fun getPreferredSize(): Dimension = Dimension(500, 500)

    fun getTableData(): List<List<ClassAttribute>> = tblModelClass.getTableData()

    private fun refreshTable() {
        lblFirstClass.text = firstClassTree.name
        lblSecondClass.text = secondClassTree.name

        tblModelClass = ClassTreeTableModel(firstClassTree, secondClassTree)
        tblClassAttributes.model = tblModelClass
    }

    private fun setupCellEditor() {
        val columnFirst = tblClassAttributes.columnModel.getColumn(0)
        val columnSecond = tblClassAttributes.columnModel.getColumn(1)

        columnFirst.cellEditor = DefaultCellEditor(ClassAttributeComboBox(firstClassTree.attributes))
        columnSecond.cellEditor = DefaultCellEditor(ClassAttributeComboBox(secondClassTree.attributes))
    }

}