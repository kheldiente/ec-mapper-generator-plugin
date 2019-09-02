package actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiManager
import com.intellij.psi.impl.file.PsiDirectoryFactory
import ui.GenerateMappingClassDialog

class GenerateMappingClassAction: AnAction() {

    private var directory: PsiDirectory? = null

    override fun actionPerformed(event: AnActionEvent) {
        // val dialog = GenerateMappingClassDialog(e.project!!)
        // dialog.show()
        directory = getDirectory(event) ?: return
        print("package name of action: ${getPackageName(directory!!)}")
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

        private fun getPackageName(directory: PsiDirectory): String {
            val directoryFactory = PsiDirectoryFactory.getInstance(directory.project)
            return directoryFactory.getQualifiedName(directory, true)
        }

    }

}