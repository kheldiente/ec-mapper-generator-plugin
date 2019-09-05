package generator.config

data class MappingClassConfig(
        val hasBaseClass: Boolean,
        val baseClassName: String = "",
        val className: String = "",
        val path: String = ""
    )