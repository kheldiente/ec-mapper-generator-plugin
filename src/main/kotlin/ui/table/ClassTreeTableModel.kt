package ui.table

import extractor.data.ClassAttribute
import extractor.data.ClassTree
import javax.swing.table.AbstractTableModel
import kotlin.math.max

class ClassTreeTableModel(private val firstClassTree: ClassTree? = null,
                          private val secondClassTree: ClassTree? = null,
                          private val columnNames: List<String> = emptyList()): AbstractTableModel() {

    companion object {

        private const val DEFAULT_ROW_COUNT = 10
        private const val DEFAULT_COLUMN_COUNT = 2

    }

    private var data: Array<ArrayList<String>>

    init {
        val firstClassTreeAttr = ArrayList(firstClassTree?.attributes!!.map { attr -> attr.toString() })
        val secondClassTreeAttr = ArrayList(secondClassTree?.attributes!!.map { attr -> attr.toString() })
        data =  arrayOf(firstClassTreeAttr, secondClassTreeAttr)
    }

    override fun getRowCount(): Int = max(firstClassTree?.attributes!!.size, secondClassTree?.attributes!!.size)

    override fun getColumnCount(): Int = DEFAULT_COLUMN_COUNT

    override fun getColumnName(column: Int): String {
        if (columnNames.isEmpty()) {
            return ""
        }
        return columnNames[column]
    }

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any {
        return data[columnIndex].getOrNull(rowIndex) ?: return ""
    }

    override fun isCellEditable(rowIndex: Int, columnIndex: Int): Boolean = true

    override fun setValueAt(aValue: Any?, rowIndex: Int, columnIndex: Int) {
        super.setValueAt(aValue, rowIndex, columnIndex)

        data[columnIndex][rowIndex] = aValue.toString()
        fireTableCellUpdated(rowIndex, columnIndex)
        println("setValueAt: $aValue")
    }

    fun getTableData(): List<List<ClassAttribute>> {
        return data.mapIndexed { index, attrWithTypes ->
            val classAttributeRef = if (index == 0) firstClassTree?.attributes else secondClassTree?.attributes
            attrWithTypes.map { key ->
                classAttributeRef?.firstOrNull { attr -> attr.id.equals(key, ignoreCase = true) } ?: ClassAttribute()
            }.filterNot { attr -> attr.name.isEmpty() || attr.type.isEmpty() }
        }
    }

}