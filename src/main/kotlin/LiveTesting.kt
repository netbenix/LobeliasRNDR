import org.openrndr.*
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.*
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.math.Vector3
import org.openrndr.math.Vector4

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

        val obj = TestPyramidObject(Vector3(0.0, 0.0, 0.0))
        val obj2 = TestPyramidObject(Vector3(5.0, 0.0, 0.0))
        val obj3 = TestPyramidObject(Vector3(0.0, 0.0, 5.0))
        val obj4 = TestPyramidObject(Vector3(-5.0, 0.0, 0.0))

        obj.position = Vector3(obj.position.x, obj.position.y, obj.position.z)
        obj2.position = Vector3(obj2.position.x, obj2.position.y, obj2.position.z)
        obj3.position = Vector3(obj3.position.x, obj3.position.y, obj3.position.z)
        obj4.position = Vector3(obj4.position.x, obj4.position.y, obj4.position.z)

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
            drawer.translate(Vector3(0.0, 0.0, -10.0)) //CAMERA

            drawer.shadeStyle = obj.shader
            drawer.depthWrite = true
            drawer.depthTestPass = DepthTestPass.LESS_OR_EQUAL
            drawer.translate(obj.position.x, obj.position.y, obj.position.z)
            drawer.rotate(Vector3.UNIT_X, obj.rotation.x)
            drawer.rotate(Vector3.UNIT_Y, obj.rotation.y)
            drawer.vertexBuffer(obj.indexBuff, listOf(obj.vertBuff), DrawPrimitive.TRIANGLES)

            //TODO: Implement Global positioning

            drawer.shadeStyle = obj2.shader
            drawer.translate(obj2.position.x, obj2.position.y, obj2.position.z)
            drawer.vertexBuffer(obj2.indexBuff, listOf(obj2.vertBuff), DrawPrimitive.TRIANGLES)

            drawer.shadeStyle = obj3.shader
            drawer.translate(obj3.position.x, obj3.position.y, obj3.position.z)
            drawer.vertexBuffer(obj3.indexBuff, listOf(obj3.vertBuff), DrawPrimitive.TRIANGLES)

            drawer.shadeStyle = obj4.shader
            drawer.translate(obj4.position.x, obj4.position.y, obj4.position.z)
            drawer.vertexBuffer(obj4.indexBuff, listOf(obj4.vertBuff), DrawPrimitive.TRIANGLES)
        }
    }
}