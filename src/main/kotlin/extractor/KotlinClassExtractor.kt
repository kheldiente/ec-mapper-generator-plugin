package extractor

import com.intellij.openapi.vfs.VirtualFile
import extractor.data.ClassTree

class KotlinClassExtractor: ClassExtractor() {

    override fun execute(virtualFile: VirtualFile): ClassTree {
        return ClassTree()
    }

}