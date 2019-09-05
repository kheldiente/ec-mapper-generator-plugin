package ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import extractor.DataClassExtractor
import extractor.KotlinClassExtractor
import extractor.data.ClassAttribute
import extractor.data.ClassTree
import generator.KotlinMappingClassGenerator
import generator.config.MappingClassConfig
import generator.file.MappingClassFile
import utils.ClassParser
import javax.inject.Inject

class GenerateMappingClassPresenter @Inject constructor(private val view: GenerateMappingClassDialog,
                                                        private val project: Project) {

    private val generator = KotlinMappingClassGenerator()

    private var firstClassTree: ClassTree? = null
    private var secondClassTree: ClassTree? = null

    fun setFirstClass(classTree: ClassTree) {
        this.firstClassTree = classTree
    }

    fun setSecondClass(classTree: ClassTree) {
        this.secondClassTree = classTree
    }

    fun getFirstClass(): ClassTree? = firstClassTree

    fun getSecondClass(): ClassTree? = secondClassTree

    fun getClassTree(virtualFile: VirtualFile): ClassTree {
        val extractor = when (ClassParser.getType(virtualFile)) {
            ClassParser.Type.DATA_CLASS -> DataClassExtractor()
            else -> KotlinClassExtractor()
        }
        return extractor.execute(virtualFile)
    }

    fun generateMappingClass(config: MappingClassConfig, attrToMap: List<ClassTree>) {
        generator.execute(config, attrToMap)
    }

    fun canBeMapped(attributeToMap: List<List<ClassAttribute>>): Boolean {
        return MappingClassFile.canBeMapped(attributeToMap)
    }

    fun allowedToGenerateMappers(): Boolean {
        return firstClassTree != null
                && secondClassTree != null
    }

    fun hasBeenLoaded(classTree: ClassTree): Boolean {
        var loaded = false
        if (firstClassTree != null) {
            loaded = firstClassTree?.name!!.equals(classTree.name, ignoreCase = true)
            if (loaded) {
                return true
            }
        }
        if (secondClassTree != null) {
            loaded = secondClassTree?.name!!.equals(classTree.name, ignoreCase = true)
            if (loaded) {
                return true
            }
        }
        return loaded
    }

}