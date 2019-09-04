package ui.table

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

    override fun getRowCount(): Int = max(firstClassTree?.attributes!!.size, secondClassTree?.attributes!!.size)

    override fun getColumnCount(): Int = DEFAULT_COLUMN_COUNT

    override fun getColumnName(column: Int): String {
        if (columnNames.isEmpty()) {
            return ""
        }
        return columnNames[column]
    }

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any {
        val attribute = if (columnIndex == 0) {
            firstClassTree?.attributes!!.getOrNull(rowIndex) ?: return ""
        } else {
            secondClassTree?.attributes!!.getOrNull(rowIndex) ?: return ""
        }
        return "${attribute.name}: ${attribute.type}"
    }

}