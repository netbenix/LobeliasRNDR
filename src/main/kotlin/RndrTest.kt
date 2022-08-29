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

        var rotationY: Double = 0.0
        var rotationX: Double = 0.0
        var positionX: Double = 0.0
        var positionY: Double = 0.0
        var positionZ: Double = -10.0
        val scrollSensitivty: Double = 2.0
        val cube = vertexBuffer(vertexFormat {
            position(3)
            color(4)
        },5)
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

        //INT16 is 2 Bytes, so total needed size is 6x2=12
        val bb = ByteBuffer.allocateDirect(obj.indexCount *2) //in bytes


        obj.insertVert(0, Vector3(-1.0,0.0,1.0), Vector4(1.0,0.0,0.0,1.0))
        obj.insertVert(1, Vector3(1.0,0.0,1.0), Vector4(0.0,0.0,1.0,1.0))
        obj.insertVert(2, Vector3(1.0,0.0,-1.0), Vector4(0.0,1.0,0.0,1.0))
        obj.insertVert(3, Vector3(-1.0,0.0,-1.0), Vector4(1.0,0.0,1.0,1.0))
        obj.insertVert(4, Vector3(0.0, 2.0, 0.0), Vector4(0.5, 0.5, 0.5, 1.0))

        bb.order(ByteOrder.nativeOrder()) //something to do with endianness
        //kotlin equivalent of Int16 is Short
        bb.putShort(0.toShort())
        bb.putShort(1.toShort()) //Base 1/2
        bb.putShort(3.toShort())

        bb.putShort(2.toShort())
        bb.putShort(1.toShort()) //Base 2/2
        bb.putShort(3.toShort())

        bb.putShort(3.toShort())
        bb.putShort(4.toShort()) //Back panel
        bb.putShort(2.toShort())

        bb.putShort(2.toShort())
        bb.putShort(4.toShort()) //Right panel
        bb.putShort(1.toShort())

        bb.putShort(1.toShort())
        bb.putShort(4.toShort()) //Front Panel
        bb.putShort(0.toShort())

        bb.putShort(0.toShort())
        bb.putShort(4.toShort()) //Left Panel
        bb.putShort(3.toShort())

        bb.rewind() //return the position of ByteBuffer back to 0 before writing to index buffer
        obj.fillIndexBuff(bb)


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

            drawer.perspective(60.0, width * 1.0 / height, 0.01, 2000.0)
            drawer.shadeStyle = obj.shader

            drawer.depthWrite = true
            drawer.strokeWeight = 10.0;
            drawer.depthTestPass = DepthTestPass.LESS_OR_EQUAL
            drawer.translate(positionX, positionY, positionZ)
            drawer.rotate(Vector3.UNIT_X, rotationX)
            drawer.rotate(Vector3.UNIT_Y, rotationY)
            drawer.vertexBuffer(obj.indexBuff, listOf(obj.vertBuff), DrawPrimitive.TRIANGLES)
        }
    }
}