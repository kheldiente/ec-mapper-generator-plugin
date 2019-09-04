package ui.combobox

import extractor.data.ClassAttribute
import javax.swing.JComboBox

class ClassAttributeComboBox(private val attributes: List<ClassAttribute> = emptyList()): JComboBox<String>() {

    init {
        attributes.forEach { attr ->
            addItem("${attr.name}: ${attr.type}")
        }
    }

}