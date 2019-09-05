package exception

class ClassParserException: MappingClassGeneratorException() {

    override val message: String?
        get() = "Invalid format! error = $localizedMessage"

}