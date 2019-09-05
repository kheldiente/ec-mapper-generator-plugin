package exception

class InvalidClassTreeException: MappingClassGeneratorException() {

    override val message: String?
        get() = "Class tree provided is invalid"

}