import org.openrndr.MouseButton
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.*
import org.openrndr.extra.color.presets.PURPLE
import org.openrndr.extra.meshgenerators.boxMesh
import org.openrndr.math.Vector3
import kotlin.math.cos
import kotlin.math.sin

fun main() = application {
    configure {
        width = 800
        height = 600
        title = "Cube Mover"
        vsync = true
    }

    program {
        var rotationY: Double = 0.0
        var rotationX: Double = 0.0
        var positionX: Double = 0.0
        var positionY: Double = 0.0
        var positionZ: Double = -100.0
        val scrollSensitivty: Double = 12.0
        val cube = boxMesh(20.0, 20.0, 20.0)
        val font = loadFont("data/fonts/default.otf", 16.0)

        mouse.dragged.listen { mse ->

            if (mse.button == MouseButton.CENTER){
                rotationX += mse.dragDisplacement.y
                rotationY += mse.dragDisplacement.x
            }
        }

        mouse.scrolled.listen{
            positionZ += it.rotation.y * scrollSensitivty
        }


        extend {
            drawer.fontMap = font
            drawer.fill = ColorRGBa.WHITE

            drawer.text("RotX: $rotationX", 0.0, 10.0)
            drawer.text("RotY: $rotationY", 0.0, 25.0)

            drawer.text("PosX: $positionX", 0.0, 50.0)
            drawer.text("PosY: $positionY", 0.0, 65.0)
            drawer.text("PosZ: $positionZ", 0.0, 80.0)

            drawer.perspective(60.0, width * 1.0 / height, 0.01, 1000.0)
            drawer.fill = ColorRGBa.PURPLE
            drawer.shadeStyle = shadeStyle {
                fragmentTransform = """
                        vec3 lightDir = normalize(vec3(0.3, 1.0, 0.5));
                        float l = dot(va_normal, lightDir) * 0.4 + 0.5;
                        x_fill.rgb *= l; 
                    """.trimIndent()
            }

            drawer.depthWrite = true
            drawer.strokeWeight = 10.0;
            drawer.depthTestPass = DepthTestPass.LESS_OR_EQUAL
            drawer.translate(positionX, positionY, positionZ)
            drawer.rotate(Vector3.UNIT_X, rotationX)
            drawer.rotate(Vector3.UNIT_Y, rotationY)
            drawer.vertexBuffer(cube, DrawPrimitive.TRIANGLES)
        }
    }
}
