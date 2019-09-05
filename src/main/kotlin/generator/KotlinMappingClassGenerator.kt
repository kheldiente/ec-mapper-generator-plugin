package generator

import extractor.data.ClassTree
import generator.config.GeneratedFileConfig
import generator.config.MappingClassConfig
import generator.file.BaseClassFile
import generator.file.MappingClassFile

class KotlinMappingClassGenerator: MappingClassGenerator {

    companion object {

        private const val DEFAULT_BASE_CLASS_NAME = "ModelMapper"

    }

    override fun execute(config: MappingClassConfig, classTrees: List<ClassTree>) {
        if (config.hasBaseClass) {
            val baseClassFile = BaseClassFile()
            var baseClassName = config.baseClassName

            if (baseClassName.isEmpty()) {
                baseClassName = DEFAULT_BASE_CLASS_NAME
            }

            val fileConfig = GeneratedFileConfig(
                className = baseClassName,
                destinationPath = config.path
            )
            // baseClassFile.execute(fileConfig)
        }

        if (classTrees.isNotEmpty()) {
            val mappingClassFile = MappingClassFile()
            mappingClassFile.classesToMap = classTrees

            val fileConfig = GeneratedFileConfig(
                className = config.className,
                destinationPath = config.path
            )
            mappingClassFile.execute(fileConfig)
        }
    }

}