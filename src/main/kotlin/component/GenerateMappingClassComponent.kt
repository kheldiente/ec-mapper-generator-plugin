package component

import com.intellij.openapi.components.AbstractProjectComponent
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializerUtil
import java.io.Serializable

@State(name="MappingGeneratorConfig", storages = [Storage(value = "mappingGeneratorConfig.xml")])
class GenerateMappingClassComponent(project: Project? = null):
        AbstractProjectComponent(project),
        Serializable,
        PersistentStateComponent<GenerateMappingClassComponent> {

    var baseClassPath: String = "" // e.g com.englishcentral.android.app.BaseMappingClass

    override fun getState(): GenerateMappingClassComponent? = this

    override fun loadState(state: GenerateMappingClassComponent) = XmlSerializerUtil.copyBean(state, this)

    companion object {

        fun getInstance(project: Project): GenerateMappingClassComponent = project.getComponent(GenerateMappingClassComponent::class.java)

    }

}

