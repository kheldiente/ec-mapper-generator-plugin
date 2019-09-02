package generator.file

interface GeneratedFile {
    fun execute(className: String, path: String)
}