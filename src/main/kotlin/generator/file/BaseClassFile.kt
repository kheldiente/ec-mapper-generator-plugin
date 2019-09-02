package generator.file

import com.squareup.kotlinpoet.*
import java.io.File

class BaseClassFile: GeneratedFile {

    override fun execute(className: String, path: String) {

        val mapFromFunction = FunSpec.builder("mapFrom")
            .addModifiers(KModifier.ABSTRACT, KModifier.PUBLIC)
            .addParameter("from", TypeVariableName("E"))
            .returns(TypeVariableName("T"))

        val mapToFunction = FunSpec.builder("mapTo")
            .addModifiers(KModifier.ABSTRACT, KModifier.PUBLIC)
            .addParameter("to", TypeVariableName("T"))
            .returns(TypeVariableName("E"))

        val classFile = FileSpec.builder("", className)
            .addType(TypeSpec.classBuilder(className)
                .addModifiers(KModifier.ABSTRACT)
                .addTypeVariable(TypeVariableName("E"))
                .addTypeVariable(TypeVariableName("T"))
                .addFunction(mapFromFunction.build())
                .addFunction(mapToFunction.build())
                .build())
            .build()

        classFile.writeTo(File(path))
    }

}