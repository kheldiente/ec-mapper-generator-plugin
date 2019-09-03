package extractor

import com.intellij.openapi.vfs.VirtualFile
import extractor.data.ClassAttribute
import extractor.data.ClassTree
import kastree.ast.Node
import kastree.ast.psi.Converter
import kastree.ast.psi.Parser

abstract class ClassExtractor {

    abstract fun execute(virtualFile: VirtualFile): ClassTree

    internal fun parse(code: String): Node.File {
        val sanitizedCode = code.trimIndent()
        val extrasMap = Converter.WithExtras()
        return Parser(extrasMap).parseFile(sanitizedCode)
    }

}