package generator

import extractor.data.ClassTree
import generator.config.MappingClassConfig

interface MappingClassGenerator {
    fun execute(config: MappingClassConfig, classTrees: List<ClassTree>)
}