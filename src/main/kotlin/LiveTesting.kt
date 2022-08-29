import org.openrndr.*
import org.openrndr.color.ColorRGBa
import org.openrndr.color.mix
import org.openrndr.draw.*
import org.openrndr.extensions.Screenshots
import org.openrndr.extra.color.presets.PURPLE
import org.openrndr.extra.fx.distort.Tiles
import org.openrndr.extra.meshgenerators.boxMesh
import org.openrndr.extra.noise.Random
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.extra.shadestyles.linearGradient
import org.openrndr.internal.Driver
import org.openrndr.math.Matrix44
import org.openrndr.math.Vector2
import org.openrndr.math.Vector3
import org.openrndr.math.Vector4
import org.openrndr.shape.contour
import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 *  This is a template for a live program.
 *
 *  It uses oliveProgram {} instead of program {}. All code inside the
 *  oliveProgram {} can be changed while the program is running.
 */

fun main() = application {
    configure {
        width = 800
        height = 600
        title = "OPENRNDR LIVE"
        vsync = true

    }
    oliveProgram {

        val scrollSensitivty: Double = 2.0
        val font = loadFont("data/fonts/default.otf", 16.0)

        var obj = RenderObject(5, 18)
        obj.shader = shadeStyle {
            vertexTransform = """
                    va_color = a_color;
                """.trimIndent()
            fragmentTransform = """
                    x_fill = va_color;
                """
        }

        obj.position = Vector3(obj.position.x, obj.position.y, obj.position.z -10.0)

        obj.insertVert(0, Vector3(-1.0,0.0,1.0), Vector4(1.0,0.0,0.0,1.0))
        obj.insertVert(1, Vector3(1.0,0.0,1.0), Vector4(0.0,0.0,1.0,1.0))
        obj.insertVert(2, Vector3(1.0,0.0,-1.0), Vector4(0.0,1.0,0.0,1.0))
        obj.insertVert(3, Vector3(-1.0,0.0,-1.0), Vector4(1.0,0.0,1.0,1.0))
        obj.insertVert(4, Vector3(0.0, 2.0, 0.0), Vector4(0.5, 0.5, 0.5, 1.0))

        obj.insertIndexes(
            shortArrayOf(
                0, 1, 3,
                2, 1, 3,
                3, 4, 2,
                2, 4, 1,
                1, 4, 0,
                0, 4, 3
            )
        )

        mouse.dragged.listen { mse ->

            if (mse.button == MouseButton.CENTER){
                obj.rotation = Vector3(obj.rotation.x + mse.dragDisplacement.y,
                    obj.rotation.y + mse.dragDisplacement.x,
                    obj.rotation.z)
            }
        }

        mouse.scrolled.listen{
            obj.position = Vector3(obj.position.x, obj.position.y,
                obj.position.z + (it.rotation.y * scrollSensitivty))
        }

        extend {

            drawer.fontMap = font
            drawer.fill = ColorRGBa.WHITE

            drawer.text("RotX: ${obj.rotation.x}", 0.0, 10.0)
            drawer.text("RotY: ${obj.rotation.y}", 0.0, 25.0)

            drawer.text("PosX: ${obj.rotation.x}", 0.0, 50.0)
            drawer.text("PosY: ${obj.rotation.y}", 0.0, 65.0)
            drawer.text("PosZ: ${obj.rotation.z}", 0.0, 80.0)

            drawer.perspective(60.0, width * 1.0 / height, 0.01, 2000.0)
            drawer.shadeStyle = obj.shader

            drawer.depthWrite = true
            drawer.strokeWeight = 10.0;
            drawer.depthTestPass = DepthTestPass.LESS_OR_EQUAL
            drawer.translate(obj.position.x, obj.position.y, obj.position.z)
            drawer.rotate(Vector3.UNIT_X, obj.rotation.x)
            drawer.rotate(Vector3.UNIT_Y, obj.rotation.y)
            drawer.vertexBuffer(obj.indexBuff, listOf(obj.vertBuff), DrawPrimitive.TRIANGLES)
        }
    }
}