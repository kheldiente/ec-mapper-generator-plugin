package generator.file

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import java.io.File

class MappingClassFile: GeneratedFile {

    override fun execute(className: String, path: String) {
        val mapFromFunction = FunSpec.builder("mapFrom")
            .addModifiers(KModifier.ABSTRACT, KModifier.PUBLIC)
            .build()

        val mapToFunction = FunSpec.builder("mapTo")
            .addModifiers(KModifier.ABSTRACT, KModifier.PUBLIC)
            .build()

        val file = FileSpec.builder("", className)
            .addType(TypeSpec.classBuilder(className).build())
            .build()
        file.writeTo(File(path))
    }

}