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
        val classTree = ClassTree(name = virtualFile.name, path = virtualFile.path)
        val code = String(virtualFile.contentsToByteArray())
        val nodeFile = ClassParser.parse(code)
        classTree.attributes = getAttributes(nodeFile)
        return classTree
    }

    private fun getAttributes(nodeFile: Node.File): List<ClassAttribute> {
        val attributes = arrayListOf<ClassAttribute>()
        Visitor.visit(nodeFile) { v, _ ->
            if (v is Node.Decl.Structured.PrimaryConstructor) {
                attributes.addAll(getAttrFromConstructor(v))
            } else if (v is Node.Decl.Property) {
                attributes.add(getAttrFromProperty(v))
            }
        }
        return attributes
    }

    private fun getAttrFromConstructor(v: Node.Decl.Structured.PrimaryConstructor): List<ClassAttribute> {
        val params = v.params
        val attributes = arrayListOf<ClassAttribute>()
        if (params.isNotEmpty()) {
            params.forEach { param ->
                val name = param.name
                val readOnly = param.readOnly!!
                val ref = param.type!!.ref
                var type = ""
                if (ref is Node.TypeRef.Simple) {
                    type = ref.pieces[0].name
                }
                attributes.add(ClassAttribute(
                    name = name,
                    type = type,
                    readOnly = readOnly,
                    isConstructorParameter = true))
            }
        }
        return attributes
    }

    private fun getAttrFromProperty(v: Node.Decl.Property): ClassAttribute {
        val attribute = ClassAttribute()
        attribute.readOnly =  v.readOnly
        val variables = v.vars
        variables.forEach { variable ->
            attribute.name = variable?.name!!
            val ref = variable.type!!.ref
            if (ref is Node.TypeRef.Simple) {
                attribute.type = ref.pieces[0].name
            }
        }
        attribute.isConstructorParameter = false
        return attribute
    }

}