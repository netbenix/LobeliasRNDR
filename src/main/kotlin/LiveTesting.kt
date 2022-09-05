import Objects.CubeObject
import Objects.PlaneObject
import Objects.PyramidObject
import Objects.TestPyramidObject
import org.openrndr.*
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.*
import org.openrndr.extra.camera.*
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.math.Vector3
import org.openrndr.math.Vector4
import kotlin.math.sin

/**
 *  This is a template for a live program.
 *
 *  It uses oliveProgram {} instead of program {}. All code inside the
 *  oliveProgram {} can be changed while the program is running.
 */

fun main() = application {
    configure {
        width = 1024
        height = 768
        title = "OPENRNDR LIVE"
        vsync = true
        hideWindowDecorations = false
        hideCursor = true

    }

    oliveProgram {
        val rt = renderTarget(160*2, 120*2, multisample = BufferMultisample.Disabled) {
            colorBuffer()
            depthBuffer()
        }.apply {
            colorBuffer(0).filter(MinifyingFilter.LINEAR, MagnifyingFilter.NEAREST)
            depthBuffer(0, 0, multisample = BufferMultisample.Disabled)
        }

        val obj = TestPyramidObject(Vector3(0.0, 0.0, 0.0), Vector3(0.5))
        val obj2 = TestPyramidObject(Vector3(0.0, 0.0, 6.0))
        val obj3 = TestPyramidObject(Vector3(6.0, 0.0, 6.0))
        val obj4 = TestPyramidObject(Vector3(6.0, 0.0, 0.0))
        val obj5 = TestPyramidObject(Vector3(12.0, 0.0, 0.0), Vector3(1.6))
        val obj6 = TestPyramidObject(Vector3(12.0, 0.0, 6.0), Vector3(1.9))
        val obj7 = TestPyramidObject(Vector3(0.0, 0.0, -6.0))
        val obj8 = PyramidObject(Vector3(6.0, 0.0, -6.0), Vector4(Math.random(), Math.random(), Math.random(), 1.0), Vector3(1.0, 3.0, 1.0))
        val obj9 = CubeObject(Vector3(12.0, 3.0, -6.0), Vector3(2.0, 3.0, 1.0))
        val floor = PlaneObject(Vector3(5.0, -0.01, 0.0), Vector4(0.5, 0.5,0.5,1.0), Vector3(20.0, 1.0, 20.0))

        val camera = OrbitalCamera(
            Vector3.UNIT_Z, Vector3.ZERO, 90.0, 0.1, 50.0
        )
        val controls = OrbitalControls(camera, keySpeed = 1.0)

        extend(controls)

        val renderables = listOf(floor, obj, obj2, obj3, obj4, obj5, obj6, obj7, obj8, obj9)
        var lastTick = 0.0

        extend {

            val now = seconds
            val delta = now - lastTick
            renderables.forEach { it.tick(delta) }
            lastTick = now
            renderObjects(rt, program, camera, renderables)

            //Apply movement + rotation
            obj.move(Vector3(sin(seconds*10)* 10.0, 0.0, 0.0), 1.0)
            obj.spin(720.0)

            obj2.spin(360.0)

            obj3.hover(0.0, 5.0, 2.0, seconds)

            obj4.hover(0.5, 10.0, 1.0, seconds)
            obj4.spin(180.0)

            obj7.spin(90.0)

            obj8.hover(0.0, 3.0, 1.0, seconds)

            obj9.spin(90.0)

            obj6.spin(-90.0)

            drawer.image(rt.colorBuffer(0), 0.0, 0.0, program.width.toDouble(), program.height.toDouble())
            rt.clearColor(0, ColorRGBa.TRANSPARENT)
            rt.clearDepth(0.0, 0)
        }
    }
}