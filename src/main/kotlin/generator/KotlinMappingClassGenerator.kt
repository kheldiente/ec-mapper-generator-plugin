package generator

import generator.file.BaseClassFile

class KotlinMappingClassGenerator: MappingClassGenerator {

    companion object {

        private const val DEFAULT_BASE_CLASS_NAME = "ModelMapper"

    }

    override fun execute(config: MappingClassConfig) {
        if (config.hasBaseClass) {
            val baseClassFile = BaseClassFile()
            var baseClassName = config.baseClassName

            if (baseClassName.isEmpty()) {
                baseClassName = DEFAULT_BASE_CLASS_NAME
            }

            baseClassFile.execute(baseClassName, config.path)
        }
    }

}