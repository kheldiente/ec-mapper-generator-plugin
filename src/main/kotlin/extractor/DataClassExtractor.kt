package extractor

import com.intellij.openapi.vfs.VirtualFile
import extractor.data.ClassAttribute
import extractor.data.ClassTree
import kastree.ast.Node
import kastree.ast.Visitor
import utils.ClassParser

class DataClassExtractor: ClassExtractor() {

    companion object {

        private val TAG = DataClassExtractor::class.java.simpleName

    }

    override fun execute(virtualFile: VirtualFile): ClassTree {
        val classTree = ClassTree(name = virtualFile.name)
        val code = String(virtualFile.contentsToByteArray())
        val nodeFile = ClassParser.parse(code)
        classTree.attributes = getAttributes(nodeFile)
        return classTree
    }

    private fun getAttributes(nodeFile: Node.File): List<ClassAttribute> {
        val attributes = arrayListOf<ClassAttribute>()
        Visitor.visit(nodeFile) { v, _ ->
            if (v is Node.Decl.Structured.PrimaryConstructor) {
                val params = v.params
                if (params.isNotEmpty()) {
                    params.forEach { param ->
                        val paramName = param.name
                        val paramRef = param.type!!.ref
                        var paramType = ""
                        if (paramRef is Node.TypeRef.Simple) {
                            paramType = paramRef.pieces[0].name
                        }
                        attributes.add(ClassAttribute(name = paramName, type = paramType))
                    }
                }
            }
        }
        return attributes
    }

}