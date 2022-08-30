import org.openrndr.*
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.*
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.internal.Driver
import org.openrndr.math.Vector3

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
        val font = loadFont("data/fonts/default.otf", 16.0)
        var camPos = Vector3(0.0, 5.0, -10.0)
        var camRot = Vector3(30.0, -180.0, 0.0)

        val scrollSensitivty: Double = 2.0
        val movementSpeed: Double = 0.1

        val obj = TestPyramidObject(Vector3(0.0, 0.0, 0.0))
        val obj2 = TestPyramidObject(Vector3(0.0, 0.0, 6.0))
        val obj3 = TestPyramidObject(Vector3(6.0, 0.0, 6.0))
        val obj4 = TestPyramidObject(Vector3(6.0, 0.0, 0.0))
        val floor = PlaneObject(Vector3(0.0))

        obj.position = Vector3(obj.position.x, obj.position.y, obj.position.z)
        obj2.position = Vector3(obj2.position.x, obj2.position.y, obj2.position.z)
        obj3.position = Vector3(obj3.position.x, obj3.position.y, obj3.position.z)
        obj4.position = Vector3(obj4.position.x, obj4.position.y, obj4.position.z)


        mouse.dragged.listen { mse ->
            if (mse.button == MouseButton.CENTER){
                camRot = Vector3(camRot.x + mse.dragDisplacement.y,
                    camRot.y + mse.dragDisplacement.x,
                    camRot.z)
            }
        }

        mouse.scrolled.listen{
            camPos = Vector3(camPos.x, camPos.y,
                camPos.z + (it.rotation.y * scrollSensitivty))
        }


        keyboard.keyRepeat.listen {kbd ->
            if(kbd.name == "a"){
                camPos = Vector3(
                    camPos.x-(1*movementSpeed),
                    camPos.y,
                    camPos.z
                )
            }

            if(kbd.name == "d"){
                camPos = Vector3(
                    camPos.x+(1*movementSpeed),
                    camPos.y,
                    camPos.z
                )
            }
        }

        extend {
            drawer.fontMap = font
            drawer.fill = ColorRGBa.WHITE

            drawer.text("RotX: ${camRot.x}", 0.0, 10.0)
            drawer.text("RotY: ${camRot.y}", 0.0, 25.0)

            drawer.text("PosX: ${camPos.x}", 0.0, 50.0)
            drawer.text("PosY: ${camPos.y}", 0.0, 65.0)
            drawer.text("PosZ: ${camPos.z}", 0.0, 80.0)

            drawer.perspective(60.0, width * 1.0 / height, 0.01, 2000.0)
            drawer.translate(camPos, TransformTarget.VIEW) //CAMERA
            drawer.rotate(Vector3.UNIT_X, camRot.x)
            drawer.rotate(Vector3.UNIT_Y, camRot.y)
            drawer.rotate(Vector3.UNIT_Z, camRot.z)


            drawer.translate(-camPos)

            drawer.shadeStyle = floor.shader
            drawer.

            drawer.shadeStyle = obj.shader
            drawer.depthWrite = true
            drawer.depthTestPass = DepthTestPass.LESS_OR_EQUAL

            drawer.translate(obj.position)
            drawer.vertexBuffer(obj.indexBuff, listOf(obj.vertBuff), DrawPrimitive.TRIANGLES)
            drawer.translate(-obj.position)

            //TODO: Implement Global positioning

            drawer.shadeStyle = obj2.shader

            drawer.translate(obj2.position)
            drawer.vertexBuffer(obj2.indexBuff, listOf(obj2.vertBuff), DrawPrimitive.TRIANGLES)
            drawer.translate(-obj2.position)

            drawer.shadeStyle = obj3.shader

            drawer.translate(obj3.position)
            drawer.vertexBuffer(obj3.indexBuff, listOf(obj3.vertBuff), DrawPrimitive.TRIANGLES)
            drawer.translate(-obj3.position)

            drawer.shadeStyle = obj4.shader

            drawer.translate(obj4.position)
            drawer.vertexBuffer(obj4.indexBuff, listOf(obj4.vertBuff), DrawPrimitive.TRIANGLES)
            drawer.translate(-obj4.position)
        }
    }
}