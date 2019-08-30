package ui

import com.intellij.openapi.project.Project
import javax.inject.Inject

class GenerateMappingClassPresenter @Inject constructor(private val view: GenerateMappingClassDialog,
                                                        private val project: Project) {
}