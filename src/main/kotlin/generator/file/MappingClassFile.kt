package generator.file

import com.squareup.kotlinpoet.*
import exception.GeneratedFileException
import extractor.data.ClassAttribute
import extractor.data.ClassTree
import generator.config.GeneratedFileConfig
import utils.ClassParser
import java.io.File
import kotlin.math.min

class MappingClassFile: GeneratedFile {

    var classesToMap: List<ClassTree> = emptyList()

    override fun execute(config: GeneratedFileConfig) {
        try {
            if (classesToMap.size != EXPECTED_NO_OF_CLASSES) {
                throw GeneratedFileException()
            }

            val firstClass = classesToMap[0]
            val secondClass = classesToMap[1]
            val packageName = ClassParser.getPackageNameFromDirectoryPath(ClassParser.getPackageName(config.destinationPath))
            println("$TAG => packageName: $packageName")

            val classFile = FileSpec.builder("", config.className)
                .addType(TypeSpec.classBuilder(config.className)
                    .addFunction(buildMapFunc("mapFrom", "from", firstClass, secondClass).build())
                    .addFunction(buildMapFunc("mapTo", "to", secondClass, firstClass).build())
                    .build())
                .build()
            classFile.writeTo(File(config.destinationPath))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun buildMapFunc(name: String,
                             paramName: String,
                             classTreeParameter: ClassTree,
                             classTreeReturned: ClassTree): FunSpec.Builder {

        val paramPackageName = ClassParser.getPackageNameFromDirectoryPath(ClassParser.getPackageName(classTreeParameter.path))
        val returnedPackageName = ClassParser.getPackageNameFromDirectoryPath(ClassParser.getPackageName(classTreeReturned.path))
        val classParameter = ClassName(paramPackageName, classTreeParameter.getFormattedName())
        val classReturned = ClassName(returnedPackageName, classTreeReturned.getFormattedName())
        println("$name => paramPackageName: $paramPackageName, returnedPackagedName: $returnedPackageName")

        val function = FunSpec.builder(name)
            .addModifiers(KModifier.PUBLIC)
            .addParameter(paramName, classParameter)
            .returns(classReturned)

        val objectName = "temp"
        var constructorCode = ""
        var memberAssignCode = ""

        val maxIndex = min(classTreeParameter.attributes.lastIndex, classTreeReturned.attributes.lastIndex)
        for (index in 0..maxIndex) {
            val paramAttr = classTreeParameter.attributes[index]
            val returnAttr = classTreeReturned.attributes[index]

            val isAssigningToConstructor = returnAttr.isConstructorParameter
            val attrParamName = paramAttr.name
            val attrReturnName = returnAttr.name

            println("$name => attrParamName: $attrParamName, attrReturnName: $attrReturnName")
            if (isAssigningToConstructor) {
                constructorCode += if (index < maxIndex) {
                    "$attrReturnName = $paramName.$attrParamName,"
                } else {
                    "$attrReturnName = $paramName.$attrParamName"
                }
            } else {
                memberAssignCode += "\n$objectName.$attrReturnName = $paramName.$attrParamName"
            }
        }

        var body = ""
        if (memberAssignCode.isNotEmpty()) {
            body += if (constructorCode.isNotEmpty()) {
                "val $objectName = ${classTreeReturned.getFormattedName()}($constructorCode)"
            } else {
                "val $objectName = ${classTreeReturned.getFormattedName()}()"
            }
            body += memberAssignCode
            body += "\nreturn $objectName"
        } else {
            body += "return ${classTreeReturned.getFormattedName()}($constructorCode)"
        }

        function.addStatement(body)
        println("$name => successfully built $name()")

        return function
    }

    companion object {

        private val TAG = MappingClassFile::class.java.simpleName
        private const val EXPECTED_NO_OF_CLASSES = 2

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