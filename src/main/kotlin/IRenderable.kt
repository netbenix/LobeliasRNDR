import org.openrndr.Program
import org.openrndr.draw.Drawer

interface IRenderable {

    fun Program.render(drawer: Drawer)
}