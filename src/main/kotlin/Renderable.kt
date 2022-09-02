import org.openrndr.Program
import org.openrndr.draw.Drawer

interface Renderable {

    fun Program.render(drawer: Drawer)
}