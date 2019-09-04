package extractor.data

data class ClassAttribute(var name: String = "",
                          var type: String = "",
                          var readOnly: Boolean = false,
                          var isConstructorParameter: Boolean = false) {

    var id = ""
        get() = toString()


    override fun toString(): String {
        if (name.isEmpty() || type.isEmpty()) {
            return ""
        }
        return "$name: $type"
    }

    companion object {

        fun parseString(value: String): ClassAttribute {
            if (value.isEmpty()) {
                return ClassAttribute(name = "", type = "")
            }

            val trimmed = value.split(": ")
            return ClassAttribute(name = trimmed[0], type = trimmed[1])
        }

    }

}