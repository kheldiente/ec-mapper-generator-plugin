package generator.file

import generator.config.GeneratedFileConfig

interface GeneratedFile {
    fun execute(config: GeneratedFileConfig)
}