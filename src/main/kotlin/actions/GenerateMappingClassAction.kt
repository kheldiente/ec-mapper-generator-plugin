package actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiManager
import ui.GenerateMappingClassDialog
import utils.ClassParser

class GenerateMappingClassAction: AnAction() {

    private var directory: PsiDirectory? = null

    override fun actionPerformed(event: AnActionEvent) {
        directory = getDirectory(event) ?: return

        val project = event.project!!
        val path = ClassParser.getDirectoryPath(directory!!)
        println("path of action: $path")

        val dialog = GenerateMappingClassDialog(project, path)
        dialog.show()
    }

    companion object {

        // Got the idea from https://github.com/Mighty16/JSONToKotlinClass/blob/master/src/com/mighty16/json/ClassFromJSONAction.java
        private fun getDirectory(event: AnActionEvent): PsiDirectory? {
            var directory: PsiDirectory? = null

            val project = event.project ?: return null
            val dataContext = event.dataContext
            val module = LangDataKeys.MODULE.getData(dataContext) ?: return null
            val navigatable = LangDataKeys.NAVIGATABLE.getData(dataContext)

            if (navigatable != null) {
                if (navigatable is PsiDirectory) {
                    directory = navigatable
                }
            }

            if (directory == null) {
                val root = ModuleRootManager.getInstance(module)
                root.sourceRoots.forEach { file ->
                    directory = PsiManager.getInstance(project).findDirectory(file)!!
                }
            }
            return directory
        }

    }

}