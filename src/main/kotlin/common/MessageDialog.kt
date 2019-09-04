package common

import utils.StringsBundle
import java.awt.Dimension
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel

class MessageDialog constructor(private val message: String = StringsBundle.message("error.message")): BaseDialogWrapper(true) {

    init {
        init()
        showCancelButton(false)
    }

    override fun createCenterPanel(): JComponent? {
        val panel = JPanel()

        val lblErrorMessage = JLabel(message)
        lblErrorMessage.setBounds(25, 33, 77, 16)
        panel.add(lblErrorMessage)

        return panel
    }

    override fun getPreferredSize(): Dimension = Dimension(100, 200)

}