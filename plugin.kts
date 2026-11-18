import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.editor.EditorKind
import com.intellij.openapi.editor.colors.EditorFontType
import com.intellij.openapi.editor.event.EditorFactoryEvent
import com.intellij.openapi.editor.event.EditorFactoryListener
import com.intellij.openapi.editor.ex.EditorEx
import com.intellij.openapi.util.Disposer
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import javax.swing.BorderFactory
import javax.swing.border.Border
import liveplugin.*

// Width of the code area to center, in characters.
val contentColumns = 120
// Upper limit for the padding in pixels (0 = no limit).
val maxMargin = 0

val originalBorders = mutableMapOf<Editor, Border?>()
val resizeListeners = mutableMapOf<Editor, ComponentAdapter>()

fun updateMargin(editor: Editor) {
    val component = editor.component
    val font = editor.colorsScheme.getFont(EditorFontType.PLAIN)
    val charWidth = component.getFontMetrics(font).charWidth('m')
    val gutterWidth = (editor as? EditorEx)?.gutterComponentEx?.width ?: 0
    val contentWidth = gutterWidth + contentColumns * charWidth

    var margin = (component.width - contentWidth) / 2
    if (margin < 0) margin = 0
    if (maxMargin > 0 && margin > maxMargin) margin = maxMargin

    val currentLeft = component.border?.getBorderInsets(component)?.left
    if (currentLeft == margin && resizeListeners.containsKey(editor)) return
    component.border = BorderFactory.createEmptyBorder(0, margin, 0, 0)
    component.revalidate()
}

fun attach(editor: Editor) {
    if (editor.editorKind != EditorKind.MAIN_EDITOR) return
    if (resizeListeners.containsKey(editor)) return
    originalBorders[editor] = editor.component.border
    val listener = object : ComponentAdapter() {
        override fun componentResized(e: ComponentEvent) = updateMargin(editor)
    }
    editor.component.addComponentListener(listener)
    resizeListeners[editor] = listener
    updateMargin(editor)
}

fun detach(editor: Editor) {
    val listener = resizeListeners.remove(editor) ?: return
    editor.component.removeComponentListener(listener)
    editor.component.border = originalBorders.remove(editor)
    editor.component.revalidate()
}

// Already-open editors
ApplicationManager.getApplication().invokeLater {
    EditorFactory.getInstance().allEditors.forEach { attach(it) }
}

// Editors opened from now on
EditorFactory.getInstance().addEditorFactoryListener(object : EditorFactoryListener {
    override fun editorCreated(event: EditorFactoryEvent) {
        ApplicationManager.getApplication().invokeLater { attach(event.editor) }
    }
    override fun editorReleased(event: EditorFactoryEvent) {
        resizeListeners.remove(event.editor)
        originalBorders.remove(event.editor)
    }
}, pluginDisposable)

// Restore everything when the plugin is stopped or re-run
Disposer.register(pluginDisposable) {
    EditorFactory.getInstance().allEditors.forEach { detach(it) }
}

show("center-editor: centering $contentColumns columns")