package utils

import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory
import kastree.ast.Node
import kastree.ast.Visitor
import kastree.ast.psi.Converter
import kastree.ast.psi.Parser

object ClassParser {

    enum class Type {
        DATA_CLASS,
        BASIC_CLASS
    }

    fun getType(virtualFile: VirtualFile): Type {
        var type = ClassParser.Type.BASIC_CLASS
        val code = String(virtualFile.contentsToByteArray())
        val nodeFile = parse(code)
        val isDataClass = isDataClass(nodeFile)

        if (isDataClass) {
            type = ClassParser.Type.DATA_CLASS
        }
        return type
    }

    fun parse(code: String): Node.File {
        val sanitizedCode = code.trimIndent()
        val extrasMap = Converter.WithExtras()
        return Parser(extrasMap).parseFile(sanitizedCode)
    }

    private fun isDataClass(nodeFile: Node.File): Boolean {
        var isDataClass = false
        Visitor.visit(nodeFile) { v, _ ->
            if (v is Node.Decl.Structured) {
                if (v.mods.isNotEmpty()) {
                    val modifier = v.mods[0] as Node.Modifier.Lit
                    isDataClass = modifier.keyword == Node.Modifier.Keyword.DATA
                }
            }
        }
        return isDataClass
    }

    /**
     * Expected input: C:\Users\Michael Diente\Documents\TestApp\app\src\main\java\com\example\testapp\data\StudentVM.kt
     * Expected output: C:\Users\Michael Diente\Documents\TestApp\app\src\main\java\com\example\testapp\data
     */
    fun getDirectoryPath(directory: PsiDirectory): String {
        val removeRegex = Regex("\\w+.kt")
        val originalPath = directory.files[0].virtualFile.path
        return removeRegex.replace(originalPath, "")
    }

}