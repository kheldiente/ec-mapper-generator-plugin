package generator.file

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import extractor.data.ClassAttribute
import java.io.File

class MappingClassFile: GeneratedFile {

    var classesToMap: Array<ArrayList<String>> = emptyArray()

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

    companion object {

        /**
         * Requirements:
         *  - No of classes: 2
         *  - Class 1 and 2 attribute evaluation uses same index
         *    E.g. Class1 attribute index 1 <-> Class2 attribute index 1
         *  - Attributes should be of the same type.
         *    TODO: Map any data/value type to String
         *  - Attributes with no counter part is NOT evaluated.
         *    E.g. Class1 attribute index 1 is not null <-> Class2 attribute index 1 is null or empty,
         *         And vice versa.
         */
        fun canBeMapped(classesToMap: List<List<ClassAttribute>>): Boolean {
            if (classesToMap.size != 2) {
                return false
            }

            var result = true
            val firstClassAttributes = classesToMap[0]
            val secondClassAttributes = classesToMap[1]

            firstClassAttributes.forEachIndexed { index, firstClassAttr ->
                val secondClassAttr = secondClassAttributes.getOrNull(index) ?: return@forEachIndexed
                val isSameType = firstClassAttr.type.equals(secondClassAttr.type, ignoreCase = true)

                println("attr1: $firstClassAttr, attr2: $secondClassAttr, isSameType: $isSameType")
                if (!isSameType) {
                    result = false
                }
            }
            return result
        }

    }

}