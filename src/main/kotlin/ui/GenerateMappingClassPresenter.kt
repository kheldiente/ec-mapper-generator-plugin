package ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import extractor.DataClassExtractor
import extractor.KotlinClassExtractor
import extractor.data.ClassTree
import utils.ClassParser
import javax.inject.Inject

class GenerateMappingClassPresenter @Inject constructor(private val view: GenerateMappingClassDialog,
                                                        private val project: Project) {

    private var firstClassTree: ClassTree? = null
    private var secondClassTree: ClassTree? = null

    fun setFirstClass(classTree: ClassTree) {
        this.firstClassTree = classTree
    }

    fun setSecondClass(classTree: ClassTree) {
        this.secondClassTree = classTree
    }

    fun getClassTree(virtualFile: VirtualFile): ClassTree {
        val extractor = when (ClassParser.getType(virtualFile)) {
            ClassParser.Type.DATA_CLASS -> DataClassExtractor()
            else -> KotlinClassExtractor()
        }
        return extractor.execute(virtualFile)
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