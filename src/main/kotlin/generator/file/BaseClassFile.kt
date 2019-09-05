package generator.file

import com.squareup.kotlinpoet.*
import generator.config.GeneratedFileConfig
import utils.ClassParser
import java.io.File

class BaseClassFile: GeneratedFile {

    override fun execute(config: GeneratedFileConfig) {
        val mapFromFunction = FunSpec.builder("mapFrom")
            .addModifiers(KModifier.ABSTRACT, KModifier.PUBLIC)
            .addParameter("from", TypeVariableName("E"))
            .returns(TypeVariableName("T"))

        val mapToFunction = FunSpec.builder("mapTo")
            .addModifiers(KModifier.ABSTRACT, KModifier.PUBLIC)
            .addParameter("to", TypeVariableName("T"))
            .returns(TypeVariableName("E"))

        val classFile = FileSpec.builder("", config.className)
            .addType(TypeSpec.classBuilder(config.className)
                .addModifiers(KModifier.ABSTRACT)
                .addTypeVariable(TypeVariableName("E"))
                .addTypeVariable(TypeVariableName("T"))
                .addFunction(mapFromFunction.build())
                .addFunction(mapToFunction.build())
                .build())
            .build()

        classFile.writeTo(File(config.destinationPath))
    }

}