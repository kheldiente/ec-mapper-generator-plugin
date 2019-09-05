package exception

class GeneratedFileException: MappingClassGeneratorException() {

    override val message: String?
        get() = "Invalid parameters for generated file"

}