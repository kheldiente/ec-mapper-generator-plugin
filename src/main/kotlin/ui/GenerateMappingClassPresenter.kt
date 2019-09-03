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

    fun getClassTree(virtualFile: VirtualFile): ClassTree {
        val extractor = when (ClassParser.getType(virtualFile)) {
            ClassParser.Type.DATA_CLASS -> DataClassExtractor()
            else -> KotlinClassExtractor()
        }
        return extractor.execute(virtualFile)
    }

}