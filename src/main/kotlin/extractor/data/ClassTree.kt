package extractor.data

data class ClassTree(var name: String = "", var attributes: List<ClassAttribute> = emptyList()) {

    override fun toString(): String {
        return "name: $name, attributes: $attributes"
    }

}
