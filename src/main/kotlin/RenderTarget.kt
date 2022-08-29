import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.arrayTexture
import org.openrndr.draw.isolatedWithTarget
import org.openrndr.draw.renderTarget

fun main() = application {
    program {
        val at = arrayTexture(512, 512, 100)
        // -- create a render target
        val rt = renderTarget(512, 512) {
            // -- attach the 0th layer of the array texture
            arrayTexture(at, 0)
            depthBuffer()
        }

        extend {
            drawer.isolatedWithTarget(rt) {
                drawer.ortho(rt)
                drawer.clear(ColorRGBa.PINK)
            }
            drawer.image(at, 0)
        }
    }
}